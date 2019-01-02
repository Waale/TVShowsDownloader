package tvshowsdownloader.services;

import thepiratebayapi.beans.TPBaySearchResults;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
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
            episodes.addAll(getEpisodesAsString(library.get(name)));
            episodes.addAll(getEpisodesAsString(downloads.get(name)));

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
        List<String> library = getEpisodesAsString(libraryService.getShows());
        List<String> downloads = getEpisodesAsString(downloaderService.getShows());

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

        List<String> library = getEpisodesAsString(libraryService.getShows());
        List<String> downloads = getEpisodesAsString(downloaderService.getShows());

        List<Episode> episodes = getUnownedEpisodes(show, library, downloads);
        show.setEpisodes(episodes);

        return show;
    }

    private List<String> getEpisodesAsString(List<Show> shows) {
        if (shows == null) {
            return new ArrayList<>();
        } else {
            return shows.stream()
                .flatMap(s -> s.getEpisodes().stream()
                        .map(e -> s.getName() + " " + e.getNumberAsString()))
                .collect(Collectors.toList());
        }
    }

    private List<String> getEpisodesAsString(Show show) {
        if (show == null || show.getEpisodes() == null) {
            return new ArrayList<>();
        } else {
            return show.getEpisodes().stream()
                    .map(e -> show.getName() + " " + e.getNumberAsString())
                    .collect(Collectors.toList());
        }
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

    public void downloadAllEpisodes() throws Exception {
        downloadEpisodes(getAllEpisodes());
    }

    public void downloadShowEpisodes(String name) throws Exception {
        downloadEpisodes(getShowEpisodes(name));
    }

    public void downloadEpisodes(List<Show> shows) throws Exception {
        for (Show show : shows) {
            downloadEpisodes(show);
        }
    }

    public void downloadEpisodes(Show show) throws Exception {
        String destinationPath = propertiesService.getProperty("destination.path");

        if (tpBayAPI == null) {
            tpBayAPI = new TPBayAPI(propertiesService.getProperty("tpbay.url"));
        }

        for (Episode episode : show.getEpisodes()) {
            String name = show.getName() + " " + episode.getNumberAsString();
            TPBaySearchResults tpBaySearchResults = tpBayAPI.searchTorrents(name, null);
            if (!tpBaySearchResults.isEmpty()) {
                TPBayTorrent torrent = tpBayAPI.getTorrent(tpBaySearchResults.get(0).getUrl());
                downloaderService.addTorrent(torrent.getMagnet(), destinationPath + "/" + show.getName(), name);
            } else {
                throw new Exception("No torrent found for search : " + name);
            }
        }
    }
}
