import './App.css';
import { Route, Routes, Link } from 'react-router';
import Home from './pages/Home';
import WriteReview from './pages/WriteReview';
import Header from './components/header/Header';
import SignUp from './pages/SignUp';
import ReviewList from './pages/ReviewList';
import Login from './pages/Login';

function App() {

  const test = [];

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
