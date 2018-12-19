package tvshowsdownloader.services;

import thepiratebayapi.services.TPBayAPI;
import tvshowsdownloader.beans.Episode;
import tvshowsdownloader.beans.Show;
import tvtimeapi.beans.TVTimeEpisode;
import tvtimeapi.beans.TVTimeSeason;
import tvtimeapi.beans.TVTimeShow;
import tvtimeapi.services.TVTimeAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<Show> getEpisodesToDownload() throws Exception {
        List<Show> watchlist = getTvTimeShows();
        // List<Show> watchlist = null;

        List<String> library = libraryService.getShows().stream()
                .flatMap(s -> s.getEpisodes().stream()
                        .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                .collect(Collectors.toList());

        List<String> downloads = downloaderService.getShows().stream()
                .flatMap(s -> s.getEpisodes().stream()
                        .map(e -> new String(s.getName() + " " + e.getNumberAsString())))
                .collect(Collectors.toList());

        List<Show> episodesToDownload = watchlist.stream()
                . map(s -> s.getEpisodes().stream()
                        .filter(e -> !library.contains(s.getName() + " " + e.getNumberAsString())
                                && !downloads.contains(s.getName() + " " + e.getNumberAsString())))
                .collect(Collectors.toList());


        return episodesToDownload;
    }

    public void downloadEpisodes(List<Show> episodes) {
        // TODO
    }

    private List<Show> getTvTimeShows() throws Exception {
        List<Show> shows = new ArrayList<>();

        for (TVTimeShow tvTimeShow : tvTimeAPI.getDetailedWatchlist()) {
            Show show = new Show();
            show.setName(tvTimeShow.getName());
            show.setBanner(tvTimeShow.getBanner());

            List<Episode> episodes = new ArrayList<>();
            for (Map.Entry<Integer, TVTimeSeason> seasonEntry : tvTimeShow.getSeasons().entrySet()) {
                for (Map.Entry<Integer, TVTimeEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
                    episodes.add(new Episode(seasonEntry.getKey(), episodeEntry.getKey(), episodeEntry.getValue().getTitle()));
                }
            }

            shows.add(show);
        }

        return shows;
    }
}
