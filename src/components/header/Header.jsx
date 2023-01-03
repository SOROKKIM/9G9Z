import React from "react";
import { Link } from "react-router-dom";

function Header() {

  // function openSubmenu(event) {
  //   event.preventDefault();
  //   var x = document.getElementById("profile-submenu");
  //   if (x.style.display === "none") {
  //     x.style.display = "block";
  //   } else {
  //     x.style.display = "none";
  //   }
  // }

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
        <Link to="/" className="nav-button">ABOUT US</Link>
        <Link to="/reviews" className="nav-button">REVIEW LIST</Link>
        <Link to="/rankings" className="nav-button">RANKINGS</Link>
        <a href="" id="profile-button" className="nav-button"><i class="fa-regular fa-user"></i>
          <div id="profile-submenu">
            <Link to="/signin">Login</Link>
            <Link to="/signup">Sign Up</Link>
            <Link>Blah Blah</Link>
          </div>
        </a>
      </div>
    </div>

  )
}

export default Header;