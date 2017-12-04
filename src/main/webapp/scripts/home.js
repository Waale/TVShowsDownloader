// SHOW DETAILS PANEL
$(".tvsm-card .btn").click(function() {
	$("#show-details-panel").show();
	$("#watchlist").removeClass("col-sm-12");
    $("#watchlist").addClass("col-sm-7");
    displayShowDetails(this);
});

function displayShowDetails(btn) {
    var card = $(btn).closest(".tvsm-card");
    var id = card.attr("id");
    var showDetailsPanel = $("#show-details-panel");
    $(".show-details").hide();

    var showDetails = $("#" + id + "-details");
    if (showDetails.length) {
        showDetails.remove();
    }

    disableButtons();
    $("#show-details-loading").show();
    var name = card.attr("name");
    var episode = card.attr("episode");
    var remainingEpisodes = card.attr("remaining-episodes");
    var link = card.attr("link");
    var tvstRemember = card.attr("tvst-remember");

    var params = {"id": id, "name": name, "episode": episode, "remainingEpisodes": remainingEpisodes, "link": link, "tvstRemember": tvstRemember};

    $.get("show", params, function(data) {
        $("#show-details-loading").hide();
        showDetailsPanel.append(data);
        enableButtons();
    });
}

function disableButtons() {
    var buttons = $(".tvsm-card .btn");
    buttons.addClass("disabled");
}

function enableButtons() {
    var buttons = $(".tvsm-card .btn");
    buttons.removeClass("disabled");
}