import React from "react";
import { Link } from "react-router-dom";


function ReviewBox({ key, mTitle, mRating, mPosterUrl, rTitle, username, date }) {
  return (
    <div className="review-box">
      <div className="movie-poster">
        <img src={mPosterUrl} />
      </div>
      <div>
        <div className="review-movie-info">
          <h1><strong>{mTitle}</strong></h1>
          <p>Ratings: {mRating} / 10</p>
        </div>
        <div className="review-info">
          <div className="review-title"><Link to="/selectedreview/" key={key} mTitle={mTitle} mRating={mRating} mPosterUrl={mPosterUrl} rTitle={rTitle} username={username} date={date}><strong>{rTitle}</strong></Link></div>
          <div className="review-writer-info">
            <a><i className="fa-solid fa-user"></i> {username}</a>
            <a><i className="fa-regular fa-calendar"></i> {date}</a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ReviewBox;