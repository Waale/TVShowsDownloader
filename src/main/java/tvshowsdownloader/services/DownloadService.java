package tvshowsdownloader.services;

import thepiratebayapi.beans.TPBaySearchResults;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.exceptions.DownloadException;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.services.TVTimeAPI;

import java.util.*;
import java.util.stream.Collectors;

public class DownloadService {
    PropertiesService propertiesService = new PropertiesService();

    TVTimeAPI tvTimeAPI;

    TPBayAPI tpBayAPI;

    DownloaderService downloaderService;

    LibraryService libraryService;

    public DownloadService() throws Exception {
        super();

        tvTimeAPI = new TVTimeAPI(propertiesService.getProperty("tvtime.url"),
                propertiesService.getProperty("tvtime.username"), propertiesService.getProperty("tvtime.password"));
        downloaderService = new DownloaderService(propertiesService);
        libraryService = new LibraryService(propertiesService);
    }

    public List<Show> getShows() throws Exception {
        List<Show> shows = new ArrayList<>();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("limit", "10000");

        Map<String, Show> watchlist = TVTimeService.parseShows(tvTimeAPI.getWatchlist(parameters)).stream().collect(Collectors.toMap(s -> s.getName(), s -> s, (s1, s2) -> s1));
        Map<String, Show> library = libraryService.getShows().stream().collect(Collectors.toMap(s -> s.getName(), s -> s, (s1, s2) -> s1));
        Map<String, Show> downloads = downloaderService.getShows().stream().collect(Collectors.toMap(s -> s.getName(), s -> s, (s1, s2) -> s1));

        for (Map.Entry<String, Show> showEntry : watchlist.entrySet()) {
            String name = showEntry.getKey();
            Show show = showEntry.getValue();

            Set<String> episodes = new HashSet<>();
            episodes.addAll(getUnwatchedEpisodesAsString(library.get(name)));
            episodes.addAll(getUnwatchedEpisodesAsString(downloads.get(name)));

            if ((show.getAiredEpisodes() - show.getSeenEpisodes()) > episodes.size()) {
                show.setDownloadedEpisodes(episodes.size());
                shows.add(show);
            }
        }

        return shows;
    }

    public List<Show> getAllEpisodes() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("limit", "10000");

        List<Show> watchlist = TVTimeService.parseShows(tvTimeAPI.getDetailedWatchlist(parameters));
        List<String> library = getUnwatchedEpisodesAsString(libraryService.getShows());
        List<String> downloads = getUnwatchedEpisodesAsString(downloaderService.getShows());

        watchlist.stream().forEach(s -> s.setEpisodes(getUnownedEpisodes(s, library, downloads)));

        return watchlist.stream().filter(s -> s.getEpisodes().size() > 0).collect(Collectors.toList());
    }

    public Show getShowEpisodes(Integer id) throws Exception {
        return getShowEpisodes(tvTimeAPI.getShow(id, true));
    }

    public Show getShowEpisodes(String name) throws Exception {
        return getShowEpisodes(tvTimeAPI.getShow(name, true));
    }

    private Show getShowEpisodes(TVTimeShow tvTimeShow) throws Exception {
        Show show = TVTimeService.parseShow(tvTimeShow);

        List<String> library = getUnwatchedEpisodesAsString(libraryService.getShows());
        List<String> downloads = getUnwatchedEpisodesAsString(downloaderService.getShows());

        List<Episode> episodes = getUnownedEpisodes(show, library, downloads);
        show.setEpisodes(episodes);

        return show;
    }

    private List<Episode> getUnownedEpisodes(Show show, List<String> library, List<String> downloads) {
        if (show == null || show.getEpisodes() == null) {
            return new ArrayList<>();
        } else {
            return show.getEpisodes().stream()
                    .filter(e -> {
                            String name = show.getName() + " " + e.getNumberAsString();
                            return !library.contains(name) && !downloads.contains(name);
                    })
                    .collect(Collectors.toList());
        }
    }

    private List<String> getUnwatchedEpisodesAsString(List<Show> shows) {
        if (shows == null) {
            return new ArrayList<>();
        } else {
            return shows.stream()
                    .flatMap(s -> s.getEpisodes().stream()
                            .filter(e -> !e.getWatched())
                            .map(e -> s.getName() + " " + e.getNumberAsString()))
                    .collect(Collectors.toList());
        }
    }

    private List<String> getUnwatchedEpisodesAsString(Show show) {
        if (show == null || show.getEpisodes() == null) {
            return new ArrayList<>();
        } else {
            return show.getEpisodes().stream()
                    .filter(e -> !e.getWatched())
                    .map(e -> show.getName() + " " + e.getNumberAsString())
                    .collect(Collectors.toList());
        }
    }

    public void downloadAllEpisodes() throws Exception {
        downloadEpisodes(getAllEpisodes());
    }

    public void downloadShowEpisodes(Integer id) throws Exception {
        downloadEpisodes(getShowEpisodes(id));
    }

    public void downloadEpisodes(List<Show> shows) throws Exception {
        List<String> messages = new ArrayList<>();

        for (Show show : shows) {
            try {
                downloadEpisodes(show);
            } catch (DownloadException e) {
                messages.addAll(e.getMessages());
            } catch (Exception e) {
                messages.add(e.getMessage());
            }
        }

        if (!messages.isEmpty()) {
            throw new DownloadException(messages);
        }
    }

    public void downloadEpisodes(Show show) throws Exception {
        List<String> messages = new ArrayList<>();

        for (Episode episode : show.getEpisodes()) {
            try {
                downloadEpisode(show, episode);
            } catch (Exception e) {
                messages.add(e.getMessage());
            }
        }

        if (!messages.isEmpty()) {
            throw new DownloadException(messages);
        }
    }

    private void downloadEpisode(Show show, Episode episode) throws Exception {
        if (tpBayAPI == null) {
            tpBayAPI = new TPBayAPI(propertiesService.getProperty("tpbay.url"));
        }

        String name = show.getName() + " " + episode.getNumberAsString();

        TPBaySearchResults tpBaySearchResults = tpBayAPI.searchTorrents(name, null);
        if (!tpBaySearchResults.isEmpty()) {
            TPBayTorrent torrent = tpBayAPI.getTorrent(tpBaySearchResults.get(0).getUrl());
            String destination = propertiesService.getProperty("destination.path") + "/" + show.getName();
            downloaderService.addTorrent(torrent.getMagnet(), destination, name);
        } else {
            throw new Exception("No torrent found for search : " + name);
        }
    }
}
