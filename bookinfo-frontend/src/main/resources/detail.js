window.setScore = function (element, event) {
    let rect = element.getBoundingClientRect();
    window.reviewData.score = Math.max(Math.ceil((event.clientX - rect.left) / rect.width * 5), 1);
    $(".star-selector .stars-fg").css({width: (window.reviewData.score / 5 * 100).toFixed(0) + "%"});
};

window.setName = function () {
    window.reviewData.name = $("#name-input").val();
};

window.setReviewContent = function () {
    window.reviewData.content = $("#review-input").val();
};

window.showMoreReviews = function () {
    window.reviewShowLimit += 5;
    $('#reviews-list').html(
      renderReviewsList()
    );
};

window.submitReview = function () {
    if(window.submitting){ return; }

    if (!window.reviewData.name) {
        alert("Your name cannot be empty!");
        return;
    }

    window.submitting = true;

    let xhr = new XMLHttpRequest();
    xhr.open("POST", window.location.href, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(window.reviewData));

    xhr.onreadystatechange = function () {
        if (this.readyState != 4) return;
        if (this.status === 200) {
            window.location.reload();
        } else {
            alert("Error: " + this.status);
        }
    };
};

window.reviewData = {
    score: 5,
    name: "",
    content: "",
};

const renderStars = () => new Array(6).join(`<i class="fas fa-star"></i>`);
const renderStarsSelector = () => `
<div class="star-selector" onclick="setScore(this, event)">
  <div class="stars-root">
    <div class="stars-bg">
       ${renderStars()}    
    </div>
    <div class="stars-fg" style="width: ${(window.reviewData.score / 5 * 100).toFixed(0)}%">
       ${renderStars()}    
    </div>
  </div>
</div>
`;

const renderSmallStars = (score) => `
  <div class="stars-root small-stars">
    <div class="stars-bg">
       ${renderStars()}    
    </div>
    <div class="stars-fg" style="width: ${(score / 5 * 100).toFixed(0)}%">
       ${renderStars()}    
    </div>
  </div>
`;

const renderReviewsList = ()=> window.contents.reviews.slice(0, window.reviewShowLimit)
  .map(item => `<div class="review-item">
                        <div class="line1">
                            <b>${item.name}</b> ${renderSmallStars(item.score)}  <span class="review-date">${item.date}</span>
                        </div>
                        <p>
                            ${item.content || ""}
                        </p>
                      </div>`).join("\n") + (window.reviewShowLimit < window.contents.reviews.length ? `<a class="show-more-reviews" onclick="showMoreReviews()">Show ${ window.contents.reviews.length - window.reviewShowLimit } more reviews</a>` : "" );

const renderItem = (item) => `
<div class="box">
        <article class="media">
            <div class="media-left">
                <figure class="image">
                    <img src="${item.image}" alt="Image">
                </figure>
            </div>
            <div class="media-content">
                <div class="content">
                  <h1>
                    ${item.title}    
                  </h1>
                  <div class="tagline">
                    ${item.author}    
                  </div>
                  
                  <div class="book-desc">${item.description}</div>
                </div>
            </div>
        </article>
        
        <hr/>
        <article class="media">
            <div class="media-content">
                ${window.contents.reviews && window.contents.reviews.length ? `<div class="content">
                    <h4>Reviews</h4>
                    <div id="reviews-list">
                      ${ renderReviewsList() }
                    </div>
                </div>   
                ` : "" }
                
                <div class="content">
                    <h4>Write Your Review</h4>
                    <div class="field">
                        <div class="control name-and-rating">
                            <input class="input" type="text" placeholder="Your Name" id="name-input" onchange="setName()">
                            ${renderStarsSelector()}
                        </div>
                    </div>
                    <div class="field">
                        <div class="control">
                            <textarea class="textarea" placeholder="Write Your Review Here" id="review-input" onchange="setReviewContent()"></textarea>
                        </div>
                    </div>
                    <div class="field">
                        <div class="control">
                            <a class="button is-primary" onclick="submitReview()">Submit</a>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </div>`;


$(document).ready(function () {
    $('#content-container').html(
        renderItem(window.contents)
    );

    $('.header-container').click(() => {
        window.location.href = "/";
    });
});
