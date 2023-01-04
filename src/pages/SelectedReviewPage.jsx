import React from "react";
import Header from "../components/header/Header";
import SelectedReview from "../components/reviewlist/SelectedReview";

function SelectedReviewPage({ key, mTitle, mRating, mPosterUrl, rTitle, username, date }) {
  return (
    <div>
      <Header />
      <SelectedReview key={key} mTitle={mTitle} mRating={mRating} mPosterUrl={mPosterUrl} rTitle={rTitle} username={username} date={date} />
    </div>
  )
}

export default SelectedReviewPage;