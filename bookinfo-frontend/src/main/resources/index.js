window.gotoDetails = function (id) {
    window.location.href = "/book/" + id;
};

const renderStars = () => new Array(6).join(`<i class="fas fa-star"></i>`);
const renderReviewScore = (item) => `
<div class="reviews">
  <div class="stars-root" title="${(item.avgReviewScore).toFixed(1)} / 5">
    <div class="stars-bg">
       ${renderStars()}    
    </div>
    <div class="stars-fg" style="width: ${(item.avgReviewScore / 5 * 100).toFixed(0)}%">
       ${renderStars()}    
    </div>
  </div>
</div>
`;

const renderItem = (item) => `
<div class="box">
        <article class="media">
            <div class="media-left">
                <figure class="image is-64x64">
                    <img src="${item.image}" alt="Image">
                </figure>
            </div>
            <div class="media-content">
                <div class="content">
                    <p class="item-title">
                        <strong>${item.title}</strong>
                        <small><i>by</i> ${item.author}</small>
                        <br>
                        <div class="item-description">
                        ${item.description}
                        </div>
                       
                    </p>
                </div>
                <nav class="level">
                    <div class="level-left">
                        <a class="button is-small is-primary" onclick="gotoDetails(${item.id})">View Details</a>
                    </div>
                    ${typeof item.reviewCount === "number" ? 
                        `<div class="level-right">
                         ${renderReviewScore(item)}
                        <span>
                          ${item.reviewCount} Reviews
                        </span>                        
                    </div>` : ""}
                </nav>
            </div>
        </article>
    </div>`;


$(document).ready(function () {
    $('#content-container').html(
        window.contents.map(renderItem).join("\n")
    );
});
