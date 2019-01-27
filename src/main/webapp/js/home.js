/**
 * Created by Romain on 28/12/2018.
 */
import {html, render} from 'https://unpkg.com/lit-html?module'

const showsTemplate = (tvshows) => html`
    ${tvshows.map((tvshow) => html`
        <div class="card tvshow">
            <div class="card-header" id="show-${tvshow.id}" class="btn btn-link collapsed">
                <table>
                    <tr>
                        <td class="banner">
                            <img src="${tvshow.banner}" title="${tvshow.name} (${tvshow.id})"/>
                            <span class="enabled"><i class="fas fa-download color1" title="Download"></i></span>
                            <span class="enabled"><i class="fas fa-search color1" title="Search"></i></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div><br/>
    `)}
`;

const loadingEpisodesTemplate = html`
    <div class="card-body">
        <div>
            <img src="../assets/loading.gif"/>
        </div>
    </div>
`;

const episodesTemplate = (show) => html`
	<div class="card-body">
	    <div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                
                    ${show.episodes.map((episode) => html`
                        <tr id="${show.id}${episode.numberAsString}">
                            <td>${episode.numberAsString}</td>
                            <td>${episode.name}</td>
                            <td class="enabled"><i class="fas fa-download color2" title="Download"></i></td>
                            <td class="enabled"><i class="fas fa-search color2" title="Search on ThePirateBay"></i></td>
                        </tr>
                    `)}
                </tbody>
            </table>
        </div>
	</div>
`;

function loadShows() {
    $.ajax({
        url: "api/get-shows",
        type: "GET",
        async: true,
        success: function (res) {
            $("#tvshows-container").removeClass("loading");
            render(showsTemplate(res.shows), document.getElementById("tvshows"));
            res.shows.forEach(function(show) {
                $("#show-" + show.id).click(function() {
                    loadShowEpisodes(show);
                });
            })
        }
    });
}

function loadShowEpisodes(show) {
    disableClicks();
    render(loadingEpisodesTemplate, document.getElementById("tvshow-details"));

    $.ajax({
        url: "api/get-show-episodes",
		data: JSON.stringify({"id": show.id}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function (res) {
            enableClicks();
        	render(episodesTemplate(res.show), document.getElementById("tvshow-details"));
        	res.show.episodes.forEach(function(episode) {
        	   $("#"+ show.id + episode.numberAsString + " .enabled .fa-download").click(function() {
        	       downloadEpisode(show, episode);
               });
            });
        }
    });
}

function downloadEpisode(show, episode) {
    disableClicks();

    $.ajax({
        url: "api/download-episodes",
        data: JSON.stringify({"shows": [{"name": show.name, "episodes": [episode]}]}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function () {
            notifySuccess(show.name + " " + episode.numberAsString + " : " + episode.name, "Download started");
            enableClicks();
        },
        error: function (res) {
            notifyError(show.name + " " + episode.numberAsString + " : " + episode.name, res.responseJSON.actionErrors[0]);
            enableClicks();
        }
    })
}

function enableClicks() {
    $(".disabled").addClass("enabled");
    $(".enabled").removeClass("disabled");
    $(".enabled").css("pointer-events", "auto");
}

function disableClicks() {
    $(".enabled").addClass("disabled");
    $(".disabled").removeClass("enabled");
    $(".disabled").css("pointer-events", "none");
}

$(document).ready(function() {
    $("#tvshows-container").addClass("loading");
	loadShows();
});