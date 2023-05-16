import React, { useState } from "react";
import { post_Message } from "../../backendService/backendServer";
import plus from "../../asset/plus.svg";
import cancel from "../../asset/cancel.svg";
function CreateMessage(props) {
  const [title, set_Title] = useState("");
  const [content, set_content] = useState("");
  const [toggle, set_toggle] = useState(false);
  
  const on_submit = () => {
    post_Message(title, content);
  };

  return (
    <div className="newMessage__container">
      <div className="newMessage__container__title">
        Create a new Message
        <img
          src={toggle ? cancel : plus}
          width="18px"
          onClick={() => {
            set_toggle(!toggle);
          }}
        />
      </div>
      {toggle && (
        <form className="newMessage__createMessage">
          <input
            placeHolder="Title"
            onChange={(e) => {
              set_Title(e.currentTarget.value);
            }}
          />
          <textarea
            placeHolder="Message Content"
            onChange={(e) => {
              set_content(e.currentTarget.value);
            }}
          />
          <button onClick={() => on_submit()}>Submit</button>
        </form>
      )}
    </div>
  );
}

export default CreateMessage;
