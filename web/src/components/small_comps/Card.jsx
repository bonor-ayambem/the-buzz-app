import React, { useState } from "react";
import upvote from "../../asset/upvote.svg";
import downvote from "../../asset/downvote.svg";
import {
  delete_message,
  upvote_message,
  downvote_message,
  post_comment,
  delete_comment,
  modify_Message,
} from "../../backendService/backendServer";


function Card({ message }) {
  const [like, set_like] = useState(false);

  const on_upvote = () => {
    //upvote_message(message_id, title, message);
  };
  const on_downvote = () => {
    //downvote_message(message_id, title, message);
  };
  const on_submit = () => {
    modify_Message(message_id, title, content);
  }

  const [toggle, set_toggle] = useState(false);

  const [title, set_Title] = useState("");
  const [content, set_content] = useState("");
  const message_id = "p1";

  return (
    <div className="card">
      <button 
        className="card__mEdit"
        onClick={() => {
          set_toggle(true);
        }}
      >
        Edit
      </button>
      <div className="card_mEdit_containter">
        {!toggle && (
          <>
            <div className="card__mTitle">{message.title}</div>
            <div className="card__mAuthor">Posted by {message.mAuthor}</div>
            <div className="card__message">{message.message}</div>
          </>
        )}
        {toggle && (
          <>
            <input
              className = "card_mEdit_title"
              defaultValue={message.title}
              onChange={(e) => {
                set_Title(e.currentTarget.value);
              }}
            />
            <textarea
              className = "card_mEdit_message"
              defaultValue={message.message}
              onChange={(e) => {
                set_content(e.currentTarget.value);
              }}
          />
          <button className="card_mEdit_submit" onClick={() => on_submit()}>Submit</button>
          <button className="card_mEdit_cancel" onClick={() => set_toggle(false)}>Cancel</button>
          </>
        )}
      </div>

      <div className="card__vote">
        <img src={upvote} />
        <img src={downvote} />
      </div>
      <input placeholder="Add Comment"></input>
      <button className = "card__submit">Submit</button>
      <span className = "card__cAuthor">{message.cAuthor}</span>
      <span className = "card__comment">: {message.comment}</span>
      <span className = "card__cEdit">Edit</span>
    </div>
  );
}

export default Card;
