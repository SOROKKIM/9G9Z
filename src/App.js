import './App.css';
import { Route, Routes } from 'react-router';
import { useState } from 'react';
import HomePage from './pages/HomePage';
import WriteReviewPage from './pages/WriteReviewPage';
import ReviewListPage from './pages/ReviewListPage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import SelectedReviewPage from './pages/SelectedReviewPage';
import { v4 as uuidv4 } from 'uuid';

function App() {

  const [ info, setInfo ] = useState(reviewInfo);


  return (
    <div>
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/writereview' element={<WriteReviewPage />} />
        <Route path='/reviews' element={<ReviewListPage review={info} />}></Route>
        <Route path='/signin' element={<LoginPage />}></Route>
        <Route path='/signup' element={<SignUpPage />}></Route>
        <Route path='/selectedreview' element={<SelectedReviewPage />}></Route>
      </Routes>
    </div>
  );
}

const reviewInfo = [
  {
    id: uuidv4(),
    username: "joepap987",
    dateOfReview: "2020.08.12",
    movieTitle: "Wakanda Forever",
    moviePosterUrl: "https://cdn.shopify.com/s/files/1/0057/3728/3618/products/black-panther-wakanda-forever_bbma1guv_480x.progressive.jpg?v=1666882065",
    movieRating: 1,
    reviewTitle: "ShitSHitPCMOVIE",
    reviewContent: "SHitmovieOfthe year hope you go bankrupt"
  },
  {
    id: uuidv4(),
    username: "hwane94",
    dateOfReview: "2022.01.03",
    movieTitle: "Titanic",
    moviePosterUrl: "https://cdn.shopify.com/s/files/1/0057/3728/3618/products/b88f1213c01b76e6eb509b28deaf97c4_cf8616aa-04be-4ef0-800d-27d71437a89c_480x.progressive.jpg?v=1573595127",
    movieRating: 9,
    reviewTitle: "One of the best movies I've ever seen..",
    reviewContent: "Awesome movie. Watched it like 40 times. Jack come back~"
  },
  {
    id: uuidv4(),
    username: "chananabanana",
    dateOfReview: "2021.12.25",
    movieTitle: "Violent Night",
    moviePosterUrl: "https://cdn.shopify.com/s/files/1/0057/3728/3618/products/violent_night_480x.progressive.jpg?v=1666812140",
    movieRating: 7,
    reviewTitle: "So Violent...",
    reviewContent: "I love you whoever you are"
  }
]


const testMovieInfo = [
  {
    title: "Teenage Mutant Ninja Turtles",
    yearOfMovie: 2022,
    movieDescription: "Shit movie",
    actors: {
      Diego: "John Smith",
      Turtle2: "Joe Mama",
      Turtle3: "Joshua Krap",
      BadGuy1: "Chimpanzee"
    },
    posterImageSrc: "www.google.com",
  }
];


export default App;