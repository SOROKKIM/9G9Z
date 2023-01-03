import './App.css';
import { Route, Routes } from 'react-router';
import Home from './pages/Home';
import WriteReview from './pages/WriteReview';
import SignUp from './pages/SignUp';
import ReviewList from './pages/ReviewList';
import Login from './pages/Login';

function App() {

  const testMovieInfo = [
    {
      title: "Teenage Mutant Ninja Turtles",
      yearOfMovie: 2022,
      movieDescription: "Shit movie",
      actors: {
        Diego : "John Smith",
        Turtle2 : "Joe Mama",
        Turtle3 : "Joshua Krap",
        BadGuy1 : "Chimpanzee"
      },
      posterImageSrc : "www.google.com",
    }
  ];
  
  const userInfo = [
    {
      username : "hwane94",
      password : "1234"
    }
  ]

  return (
    <div>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/writereview' element={<WriteReview />} />
        <Route path='/reviews' element={<ReviewList />}></Route>
        <Route path='/signin' element={<Login />}></Route>
        <Route path='/signup' element={<SignUp />}></Route>
      </Routes>
    </div>
  );
}

export default App;
