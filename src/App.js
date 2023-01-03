import './App.css';
import { Route, Routes } from 'react-router';
import HomePage from './pages/HomePage';
import WriteReviewPage from './pages/WriteReviewPage';
import ReviewListPage from './pages/ReviewListPage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import SelectedReviewPage from './pages/SelectedReviewPage';

function App() {

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

  const userInfo = [
    {
      username: "hwane94",
      password: "1234"
    }
  ]


  return (
    <div>
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/writereview' element={<WriteReviewPage />} />
        <Route path='/reviews' element={<ReviewListPage />}></Route>
        <Route path='/signin' element={<LoginPage />}></Route>
        <Route path='/signup' element={<SignUpPage />}></Route>
        <Route path='/selectedreview' element={<SelectedReviewPage />}></Route>
      </Routes>
    </div>
  );
}

export default App;
