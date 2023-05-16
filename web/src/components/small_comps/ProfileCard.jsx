import React, { useEffect, useState } from "react";
import upvote from "../../asset/upvote.svg";
import downvote from "../../asset/downvote.svg";
import { modify_User } from "../../backendService/backendServer";
​
function ProfileCard({ user }) {
  const [editMode, setEditMode] = useState(false);
​
  const [username, setUsername] = useState(null);
  const [name, setName] = useState(null);
  const [email, setEmail] = useState(null);
  const [bio, setBio] = useState(null);
​
  useEffect(() => {
    setName(user.mFirstName);
    setEmail(user.mEmail);
    setUsername(user.mUser);
    setBio(user.mBio);
  }, [user]);
​
  const profileChangeSubmit = async () => {
    modify_User(username, name, email, bio);
  };
​
  console.log(user);
  return (
    <div className="pcard">
      {editMode ? (
        <>
          <h2>Username</h2>
          <input
            value={username}
            onChange={(e) => setUsername(e.currentTarget.value)}
          />
          <h2>Name</h2>
          <input
            value={name}
            onChange={(e) => setName(e.currentTarget.value)}
          />
          <h2>Email</h2>
          <input
            value={email}
            onChange={(e) => setEmail(e.currentTarget.value)}
          />
          <h2>Bio</h2>
          <input value={bio} onChange={(e) => setBio(e.currentTarget.value)} />
​
          <button
            style={{
              display: "block",
              backgroundColor: "#d8a881",
              padding: "5px 8px",
              marginTop: "20px",
            }}
            onClick={() => {
              setEditMode(false);
              profileChangeSubmit();
            }}
          >
            Submit
          </button>
        </>
      ) : (
        <>
          <h2>Username</h2>
          <span> {username} </span>
          <h2>Name</h2>
          <span> {name} </span>
          <h2>Email</h2>
          <span> {email}</span>
          <h2>Bio</h2>
          <span> {bio} </span>{" "}
        </>
      )}
​
      <span className="pcard__edit" onClick={() => setEditMode(true)}>
        Edit
      </span>
    </div>
  );
}
​
export default ProfileCard;