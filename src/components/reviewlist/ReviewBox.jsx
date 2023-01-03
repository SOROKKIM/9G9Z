import React from "react";


function ReviewBox() {
  return (
      <div className="review-box">
        <div className="ratings"></div>
        <div className="review-info">
          <div className="review-movie-info">Movie Info</div>
          <div className="review-title">Review Title</div>
          <div className="review-content">Review Description</div>
        </div>
      </div>
  )
}

export default ReviewBox;