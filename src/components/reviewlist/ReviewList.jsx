import React from "react";
import ReviewBox from "./ReviewBox";
import './reviewlist.css';



function ReviewList() {
  return (
    <section>
      <div className="review-background">
        <div className="review-box-container">
          <ReviewBox />
          <ReviewBox />
          <ReviewBox />
          <ReviewBox />
          <ReviewBox />
        </div>
      </div>
    </section>
  )
}


export default ReviewList;