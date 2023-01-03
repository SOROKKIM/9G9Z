import React from "react";
import Header from "../components/header/Header";
import '../components/header/header.css'
import Reviews from "../components/reviewlist/Reviews";

function ReviewList() {
  return (
    <div>
      <Header />
      <section>
        <Reviews />
      </section>
    </div>
  )
}

export default ReviewList;