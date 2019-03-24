import {html, render} from 'https://unpkg.com/lit-html?module'

export const showsTemplate = (tvshows) => html`
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

export const episodesTemplate = (show) => html`
    <table class="episode table table-striped">
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
                </tr>
            `)}
        </tbody>
    </table>
`;