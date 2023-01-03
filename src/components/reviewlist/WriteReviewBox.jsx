import React from "react";

function WriteReviewBox() {
  return (
    <div className="write-review-box">
      <div className="write-movie-info">
        <img src="https://cdn.shopify.com/s/files/1/0057/3728/3618/products/803bf08ef84b9a1f42a64819fcf3fe70_078f43dc-ab20-4266-9ed0-b32d5e181725_480x.progressive.jpg?v=1573592630" />
        <div className="movie-info">
          <h4 className="movie-title">Yes Man (2008)</h4>
          <p className="ratings">Rating : 7 / 10</p>
          <p>Your Rating : </p>
          <hr/>
        </div>
      </div>
      <form>
        <h4>Your Review</h4>
        <input className="review-headline" type="text" placeholder="Write a headline for your review"></input>
        <textarea className="review-context" placeholder="Write your review here"></textarea>
        <input className="login-button" type="submit" value="Sign In"></input>
      </form>
    </div>
  )
}

export default WriteReviewBox;