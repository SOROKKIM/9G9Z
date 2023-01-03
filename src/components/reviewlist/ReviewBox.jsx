import React from "react";


function ReviewBox() {
  return (
    <div className="review-box">
      <div className="movie-poster">
        <img src="https://cdn.shopify.com/s/files/1/0057/3728/3618/products/b88f1213c01b76e6eb509b28deaf97c4_cf8616aa-04be-4ef0-800d-27d71437a89c_480x.progressive.jpg?v=1573595127" />
      </div>
      <div>
        <div className="review-movie-info">
          <h1><strong>Titanic (1997)</strong></h1>
          <p>Ratings: 9.5 / 10</p>
        </div>
        <div className="review-info">
          <div className="review-title"><a><strong>One of the best movies I've ever seen...</strong></a></div>
          <div className="review-writer-info">
            <a><i class="fa-solid fa-user"></i> hwane94</a>
            <a><i class="fa-regular fa-calendar"></i> 2023.01.03</a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ReviewBox;