import React from "react";
import "./login.css";

function LoginBox() {
  return (
    <div className="login-box">
      <h1>Sign In</h1>
      <form>
        <label className="login-label">Username</label>
        <input className="login-input" type="text"></input>
        <label className="login-label">Password</label>
        <input className="login-input" type="password"></input>
        <input className="login-button" type="submit" value="Sign In"></input>
      </form>
    </div>
  )
}


export default LoginBox;