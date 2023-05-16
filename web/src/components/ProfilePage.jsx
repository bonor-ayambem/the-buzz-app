import React, { Component } from "react";
import ProfileCard from "./small_comps/ProfileCard";
import { get_User } from "../backendService/backendServer";

//attempt at a user login page
class ProfilePage extends Component {
  state = {
    user: null,
  };

  componentDidMount() {
    this.getUser();
  }

  getUser = async () => {
    this.setState({ user: await get_User() });
  }
  
  render() {
      return (
        <div className="mainContent">
          <h3>Profile</h3>
          <div className="mainContent_subContainer">
            {this.state.user && <ProfileCard user={this.state.user} />}
          </div>
        </div>
      );
  }
}

export default ProfilePage;