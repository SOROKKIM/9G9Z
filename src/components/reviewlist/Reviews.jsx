import React from "react";
import ReviewBox from "./ReviewBox";
import './reviewlist.css';



function Reviews() {
  return (
    <section>
      <div className="review-background">
        <div className="review-box-container">
          <ReviewBox />
        </div>
      </div>
    </section>
  )
}


export default Reviews;