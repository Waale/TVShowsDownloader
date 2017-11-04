<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TVShowMonitoring</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<link rel="stylesheet" href="styles/home.css"/>
</head>
<body>
	<div class="row">
		<div id="watchlist" class="col-sm-12">
			<div class="row tvsm-row">
				<c:forEach items="${simplifiedWatchlist}" var="show" varStatus="status">
					<div id="show-${show.id}" class="card col-sm-2 tvsm-card">
						<img class="card-img-top" src="${show.poster}" alt="TV Show Poser">
						<div class="card-body">
						  <p class="card-title ellipsis">${show.name}</p>
						  <p class="card-text"></p>
						  <a href="#" class="btn btn-primary ellipsis">Show episodes</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div id="show-details-panel" class="col-sm-4">
			Test
		</div>
	</div>
</body>
</html>