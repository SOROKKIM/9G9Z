import React from "react";
import Scroller from "../components/home/Scroller";
import Header from "../components/header/Header";
import '../components/header/header.css'

function Home() {
  return (
    <div>
      <Header />
      <div className="Home">
        <Scroller />
        <Scroller />
        <Scroller />
      </div>
    </div>
  )
}

export default Home;