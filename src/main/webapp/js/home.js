/**
 * Created by Romain on 28/12/2018.
 */

import {html, render} from 'https://unpkg.com/lit-html?module'
import * as templates from './templates.js'

function loadShows() {
    $.ajax({
        url: "api/get-shows",
        type: "GET",
        async: true,
        success: function (res) {
            $("#tvshows").removeClass("loading");
            render(templates.showsTemplate(res.shows), document.getElementById("tvshows"));
            res.shows.forEach(function(show) {
                $("#show-" + show.id + " .fa-download").click(function() {
                    downloadShowEpisodes(show);
                });
                $("#show-" + show.id + " .fa-search").click(function() {
                    loadShowEpisodes(show);
                });
            })
        }
    });
}

function downloadShowEpisodes(show) {
    disableClicks();

    $.ajax({
        url: "api/download-show-episodes",
        data: JSON.stringify({"id": show.id}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function (res) {
            notifySuccess(show.name, "Download started");
            enableClicks();
        },
        error: function (res) {
            notifyError(show.name, res.responseJSON.actionErrors.join(", "));
            enableClicks();
        }
    });
}

function loadShowEpisodes(show) {
    disableClicks();
    $("#tvshow-details").addClass("loading");
    render("", document.getElementById("tvshow-details"));

    $.ajax({
        url: "api/get-show-episodes",
		data: JSON.stringify({"id": show.id}),
        dataType: "json",
        contentType: "application/json",
        type: "POST",
        async: true,
        success: function (res) {
            enableClicks();
            $("#tvshow-details").removeClass("loading");
        	render(templates.episodesTemplate(res.show), document.getElementById("tvshow-details"));
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
            notifyError(show.name + " " + episode.numberAsString + " : " + episode.name, res.responseJSON.join(", "));
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
    $("#tvshows").addClass("loading");
	loadShows();
});