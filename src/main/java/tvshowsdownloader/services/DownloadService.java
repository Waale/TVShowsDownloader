package tvshowsdownloader.services;

import thepiratebayapi.beans.TPBaySearchResults;
import thepiratebayapi.beans.TPBayTorrent;
import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvshowsdownloader.exceptions.ParameterException;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.services.TVTimeAPI;

import java.util.List;
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
        tpBayAPI = new TPBayAPI(propertiesService.getProperty("tpbay.url"));
        downloaderService = new DownloaderService(propertiesService);
        libraryService = new LibraryService(propertiesService);
    }

    public List<Show> getAllEpisodes() throws Exception {
        List<Show> watchlist = TVTimeService.parseWatchlist(tvTimeAPI.getDetailedWatchlist());

        List<String> library = libraryService.getShows().stream()
                .flatMap(s -> s.getEpisodes().stream()
                        .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                .collect(Collectors.toList());

        List<String> downloads = downloaderService.getShows().stream()
                .flatMap(s -> s.getEpisodes().stream()
                        .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                .collect(Collectors.toList());

        for (Show show : watchlist) {
            List<Episode> episodes = show.getEpisodes().stream()
                    .filter(e -> !library.contains(new String(show.getName() + " " + e.getNumberAsString()))
                            && !downloads.contains(!library.contains(new String(show.getName() + " " + e.getNumberAsString())))
                    ).collect(Collectors.toList());
            show.setEpisodes(episodes);
        }

        return watchlist.stream().filter(s -> s.getEpisodes().size() > 0).collect(Collectors.toList());
    }

    public Show getShowEpisodes(String name) throws Exception {
        if (name == null) {
            throw new ParameterException("name");
        }

        List<TVTimeShow> tvTimeShows = tvTimeAPI.getWatchlist().stream()
                .filter(s -> s.getName().equals(name))
                .collect(Collectors.toList());

        if (!tvTimeShows.isEmpty()) {
            Show show = TVTimeService.parseShow(tvTimeAPI.getUnwatchedShow(tvTimeShows.get(0).getLink()));

            List<String> library = libraryService.getShows().stream()
                    .flatMap(s -> s.getEpisodes().stream()
                            .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                    .collect(Collectors.toList());

            List<String> downloads = downloaderService.getShows().stream()
                    .flatMap(s -> s.getEpisodes().stream()
                            .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                    .collect(Collectors.toList());

            List<Episode> episodes = show.getEpisodes().stream()
                    .filter(e -> !library.contains(new String(show.getName() + " " + e.getNumberAsString()))
                            && !downloads.contains(!library.contains(new String(show.getName() + " " + e.getNumberAsString())))
                    ).collect(Collectors.toList());
            show.setEpisodes(episodes);

            return show;
        } else {
            return null;
        }
    }

    public void downloadAllEpisodes() throws Exception {
        downloadShows(getAllEpisodes());
    }

    public void downloadShows(List<Show> shows) throws Exception {
        for (Show show : shows) {
            downloadShow(show);
        }
    }

    public void downloadShow(String name) throws Exception {
        downloadShow(getShowEpisodes(name));
    }

    private void downloadShow(Show show) throws Exception {
        String destinationPath = propertiesService.getProperty("destination.path");

        for (Episode episode : show.getEpisodes()) {
            String name = show.getName() + " " + episode.getNumberAsString();
            TPBaySearchResults tpBaySearchResults = tpBayAPI.searchTorrents(name, null);
            if (!tpBaySearchResults.isEmpty()) {
                TPBayTorrent torrent = tpBayAPI.getTorrent(tpBaySearchResults.get(0).getUrl());
                downloaderService.addTorrent(torrent.getMagnet(), destinationPath + "/" + show.getName(), name);
            }
        }
    }
}
