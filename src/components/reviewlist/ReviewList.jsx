import React from "react";
import ReviewBox from "./ReviewBox";
import './reviewlist.css';



function ReviewList({ review }) {

  const mappedReview = review.map(n => {
    return (
      <ReviewBox key={n.id} mTitle={n.movieTitle} mRating={n.movieRating} mPosterUrl={n.moviePosterUrl} rTitle={n.reviewTitle} username={n.username} date={n.dateOfReview}/>
    )
  })

  return (
    <section>
      <div className="review-background">
        <div className="review-box-container">
          {mappedReview}
        </div>
      </div>
    </section>
  )
}


export default ReviewList;