import React, { Component } from "react";
import Card from "./small_comps/Card";
import CreateMessage from "../components/small_comps/CreateMessage";
import { get_Message } from "../backendService/backendServer";
import db from "../fakedb.json";

class Home extends Component {
  state = {
    db: db,
    toggle: false,
  };
  async componentDidMount() {
    const data = await get_Message();
    data && console.log(data);
  }
  render() {
    const { db } = this.state;
    return (
      <div className="mainContent">
        <CreateMessage />
        <h3>Showing {db.length} messages in Databse</h3>
        <div className="mainContent_subContainer">
          {db.map((message) => (
            <Card key={message.id} message={message} />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;