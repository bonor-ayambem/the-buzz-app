import "./App.css";
import {
  Route,
  Switch,
  Redirect,
  NavLink,
  useLocation,
} from "react-router-dom";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import ProfilePage from "./components/ProfilePage";
import { useTransition, animated } from "react-spring";
import animation from "./components/small_comps/animation";
import React from "react";
import logo from "./buzzlogo.png";

function App(props) {
  const location = useLocation();

  // a cool animation between route
  const transitions = useTransition(location, animation(location));

  return (
    <div className="App">
      <img className="logo" src={logo} alt="Logo" />
      <h1 className="app__title"></h1>


      <div className="app__Links">
        <NavLink to="/home">Home</NavLink>
        <NavLink to="/profile">Profile</NavLink>
      </div>

      {transitions((props, item) => (
        <animated.div style={props} className="app__container">
          <Switch location={item}>
            <Route path="/home">
              <HomePage />
            </Route>
            <Route path="/profile">
              <ProfilePage />
            </Route>
            <Redirect from="/" to="/home" />
          </Switch>
        </animated.div>
      ))}
    </div>
  );
}

export default App;
