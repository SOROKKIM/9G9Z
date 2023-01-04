import React from "react";
import Header from "../components/header/Header";
import '../components/header/header.css'
import ReviewList from "../components/reviewlist/ReviewList";

function ReviewListPage({ review }) {
  return (
    <div>
      <Header />
      <section>
        <ReviewList review={review}/>
      </section>
    </div>
  )
}

export default ReviewListPage;