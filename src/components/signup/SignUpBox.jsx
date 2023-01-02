import React from "react";
import "./signup.css";

function SignUpBox() {
  return (
    <div className="signup-box">
      <h1>Sign Up</h1>
      <form>
        <label className="signup-label">First Name</label>
        <input className="signup-input" type="text"></input>
        <label className="signup-label">Last Name</label>
        <input className="signup-input" type="text"></input>
        <label className="signup-label">Username</label>
        <input className="signup-input" type="text"></input>
        <label className="signup-label">Email</label>
        <input className="signup-input" type="text"></input>
        <label className="signup-label">Password</label>
        <input className="signup-input" type="password"></input>
        <label className="signup-label">Confirm Password</label>
        <input className="signup-input" type="password"></input>
        <input className="signup-button" type="submit" value="Create Account"></input>
      </form>
    </div>
  )
}


export default SignUpBox;