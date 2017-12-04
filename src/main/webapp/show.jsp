<%--
  Created by IntelliJ IDEA.
  User: Romain
  Date: 04/11/2017
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div id="${show.id}-details" class="card show-details">
    <img class="card-img-top" src="${show.banner}" alt="TV Show Banner">
    <div class="card-block">
        <h2 class="card-title">${show.name}</h2>
    </div>

    <div id="${show.id}-accordion" class="show-accordion" role="tablist" aria-multiselectable="false">
        <c:forEach items="${show.seasons}" var="season" varStatus="seasonStatus">
            <div class="card">
                <div class="card-header" role="tab" id="heading-${show.id}-${season.key}">
                    <h5 class="mb-0">
                        <p class="season-title collapsed" data-toggle="collapse" data-parent="#${show.id}-accordion" href="#collapse-${show.id}-${season.key}" aria-expanded="false" aria-controls="collapse-${show.id}-${season.key}">
                            Season ${season.key}
                            <i class="fa fa-chevron-down" aria-hidden="true"></i>
                        </p>
                    </h5>
                </div>
                <div id="collapse-${show.id}-${season.key}" class="collapse" role="tabpanel" aria-labelledby="heading-${show.id}-${season.key}">
                    <div class="card-block table-responsive">
                        <table class="table table-hover table-striped">
                            <tbody>
                                <c:forEach items="${season.value.episodes}" var="episode" varStatus="episodeStatus">
                                    <c:if test="${episode.key == 1}">
                                        <tr>
                                            <td colspan="3">Full season</td>
                                            <td>
                                                <a href="https://ukpirate.click/s/?q=${showDownloadLinkPart} ${season.value.downloadLinkPart}" target="_blank">
                                                    <i class="fa fa-cloud-download" aria-hidden="true"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>${episode.key}</td>
                                        <td>${episode.value.title}</td>
                                        <td>${episode.value.date}</td>
                                        <td>
                                            <a href="https://ukpirate.click/s/?q=${showDownloadLinkPart} ${season.value.downloadLinkPart}${episode.value.downloadLinkPart}" target="_blank">
                                                <i class="fa fa-cloud-download" aria-hidden="true"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
