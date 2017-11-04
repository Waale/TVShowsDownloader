<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>TVShowMonitoring</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<link rel="stylesheet" href="styles/home.css"/>
	<link rel="stylesheet" type="text/css" href="assets/fonts/fonts.min.css" />
	<link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
</head>
<body>
	<div class="row tvsm-row">
		<div id="watchlist" class="col-sm-12">
			<div class="row tvsm-row">
				<c:forEach items="${watchlist}" var="show" varStatus="status">
					<div id="show-${show.id}" class="card col-sm-2 tvsm-card" name="${show.name}" episode="${show.episode}" remaining-episodes="${show.remainingEpisodes}" link="${show.link}" tvst-remember="${tvstRemember}">
						<img class="card-img-top" src="${show.poster}" alt="TV Show Poster">
						<div class="card-body">
						  <p class="card-title ellipsis">${show.name}</p>
						  <p class="card-text"></p>
						  <a href="#" onclick="return false;" class="btn btn-primary ellipsis">Show episodes</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div id="show-details-panel" class="col-sm-5">
			<div id="show-details-loading">
				<img src="assets/loading.gif"/>
			</div>
		</div>
	</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
<script src="scripts/home.js"></script>
</html>