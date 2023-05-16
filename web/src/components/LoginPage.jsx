import React, { Component } from "react";
import {GoogleLogin} from "react-google-login"

//attempt at a user login page
class LoginPage extends Component {
  
  handleLogin = async googleData => {
    const res = await fetch("/api/v1/auth/google", {
        method: "POST",
        body: JSON.stringify({
        token: googleData.tokenId
      }),
      headers: {
        "Content-Type": "application/json"
      }
    })
    const data = await res.json()
    // store returned user somehow
  }

  render() {
      return (
        
        <GoogleLogin
        //disabled={false}
        //disabledStyle={false}
          clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID} //issue with env var
          buttonText="Log in with Google"
          onSuccess={this.handleLogin}
          onFailure={this.handleLogin}
          cookiePolicy={'single_host_origin'}
/>
      );
  }

}

export default LoginPage;