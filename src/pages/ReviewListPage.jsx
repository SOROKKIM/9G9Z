import React from "react";
import Header from "../components/header/Header";
import '../components/header/header.css'
import ReviewList from "../components/reviewlist/ReviewList";

function ReviewListPage() {
  return (
    <div>
      <Header />
      <section>
        <ReviewList />
      </section>
    </div>
  )
}

export default ReviewListPage;