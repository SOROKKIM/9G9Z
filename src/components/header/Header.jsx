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
          <button className="search-button"><i className="fa-solid fa-magnifying-glass"></i></button>
        </form>
      </div>
      <div className="navbar">
        <Link to="/" className="nav-button">ABOUT US</Link>
        <Link to="/reviews" className="nav-button">REVIEW LIST</Link>
        <Link to="/rankings" className="nav-button">RANKINGS</Link>
        <div id="profile-button" className="nav-button">
          <a href=""><i className="fa-regular fa-user"></i></a>
          <div id="profile-submenu">
            <Link to="/signin">Login</Link>
            <Link to="/signup">Sign Up</Link>
            <Link>Blah Blah</Link>
          </div>
        </div>
      </div>
    </div>

  )
}

export default Header;