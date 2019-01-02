/**
 * Created by Romain on 28/12/2018.
 */
import {html, render} from 'https://unpkg.com/lit-html?module'

const showsTemplate = (tvshows) => html`
	<div class="accordion" id="tvshows-accordion">
		${tvshows.map((tvshow) => html`
			<div class="card tvshow">
                <div class="card-header" id="heading-${tvshow.id}" class="btn btn-link collapsed">
					<table>
						<tr>
							<td class="banner">
								<img src="${tvshow.banner}" title="${tvshow.name} (${tvshow.id})"/>
                                <i class="fas fa-download"></i>
                                <i class="fas fa-angle-down" id="collapser-${tvshow.id}" data-toggle="collapse" data-target="#collapse-${tvshow.id}" aria-expanded="false" aria-controls="collapse-${tvshow.id}"></i>
							</td>
						</tr>
					</table>
                </div>
                
                <div id="collapse-${tvshow.id}" class="collapse" aria-labelledby="heading-${tvshow.id}" data-parent="#tvshows-accordion">
                    <div class="card-body loading"></div>
                </div>
            </div><br/>
		`)}
	</div>
`;

const loadingEpisodesTemplate = html`
    <div class="card-body">
        <div>
            <img src="assets/loading.gif"/>
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
                            <td><i class="fas fa-download" title="Download"></i></td>
                            <td><i class="fas fa-search" title="Search on ThePirateBay"></i></td>
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
            $("#tvshows").removeClass("loading");
            render(showsTemplate(res.shows), document.getElementById("tvshows"));
            res.shows.forEach(function(show) {
                $("#collapse-" + show.id).on("show.bs.collapse", function() {
                    loadShowEpisodes(show);
                    $("#collapser-" + show.id).addClass("rotated");
                });
                $("#collapse-" + show.id).on("hide.bs.collapse", function() {
                    $("#collapser-" + show.id).removeClass("rotated");
                });
            })
        }
    });
}

function loadShowEpisodes(show) {
    render(loadingEpisodesTemplate, document.getElementById("collapse-" + show.id));

    $.ajax({
        url: "api/get-show-episodes",
		data: JSON.stringify({"id": show.id}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function (res) {
        	render(episodesTemplate(res.show), document.getElementById("collapse-" + show.id));
        	res.show.episodes.forEach(function(episode) {
        	   $("#"+ show.id + episode.numberAsString + " .fa-download").click(function() {
        	       downloadEpisode(show, episode);
               });
            });
        }
    });
}

function downloadEpisode(show, episode) {
    $.ajax({
        url: "api/download-episodes",
        data: JSON.stringify({"shows": [{"name": show.name, "episodes": [episode]}]}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function (res) {
            console.log(res);
        }
    })
}

$(document).ready(function() {
    $("#tvshows").addClass("loading");
	loadShows();
});