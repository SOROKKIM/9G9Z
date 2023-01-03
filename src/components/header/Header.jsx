import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <div className="header">
      <div>
        <div className="logo"><Link to="/">9G9J</Link></div>
      </div>
      <div className="container">
        <form className="search-bar">
          <input className="search-input" type="text" placeholder="Search movie..." name="q"></input>
          <button className="search-button"><i class="fa-solid fa-magnifying-glass"></i></button>
        </form>
      </div>
      <div className="navbar">
          <Link to="/reviews" className="nav-button">REVIEWS</Link>
          <Link to="/signin" className="nav-button">LOGIN</Link>
          <Link to="/signup" className="nav-button">SIGNUP</Link>
          <div className="nav-button"><i class="fa-regular fa-user"></i></div>
      </div>
    </div>

  )
}

export default Header;