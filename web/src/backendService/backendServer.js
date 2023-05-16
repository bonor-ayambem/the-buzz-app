import axios from "axios";

const api = "https://hidden-sierra-98950.herokuapp.com";

//post a message
export async function post_Message(title, message) {
  try {
    await axios.post(api + "/messages", {
      mTitle: title,
      mMessage: message,
    });
  } catch (error) {
    console.log(error.message);
  }
}

//get all messages
export async function get_Messages() {
  try {
    const { data } = await axios.get(api + "/messages");
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

//get specific message
export async function get_Message() {
  try {
    const { data } = await axios.get(api + "/messages");
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

//modify a message content
export async function modify_Message(message_id, title, message) {
  try {
    const { data } = await axios.put(api + `/messages/${message_id}`, {
      mTitle: title,
      mMessage: message,
    });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

//like a message ****NEEDS TO BE FIXED****
export async function upvote_message(message_id, title, message) {
  try {
    await axios.put(api + `/messages/${message_id}/likes`, {
      mTitle: title,
      mMessage: message,
    });
  } catch (error) {
    console.log(error.message);
  }
}

//dislike a message ****NEEDS TO BE FIXED****
export async function downvote_message(message_id, title, message) {
  try {
    await axios.put(api + `/messages/${message_id}/likes`, {
      mTitle: title,
      mMessage: message,
    });
  } catch (error) {
    console.log(error.message);
  }
}

//delete a message
export async function delete_message(message_id) {
  try {
    await axios.delete(api + `/messages/${message_id}`);
  } catch (error) {
    console.log(error.message);
  }
}

//post a comment
export async function post_comment(message_id, comment, author) {
  try {
    await axios.post(api + `/messages/${message_id}/comments`, {
      cAuthor: author,
      cMessage: comment,
    });
  } catch (error) {
    console.log(error.message);
  }
}

//delete a comment
export async function delete_comment(message_id, comment_id) {
  try {
    await axios.delete(api + `/messages/${message_id}/comments/${comment_id}`);
  } catch (error) {
    console.log(error.message);
  }
}

//edit comment
export async function modify_comment(message_id, comment_id, comment) {
  try {
    const { data } = await axios.put(api + `/messages/${message_id}/comments/${comment_id}`, {
      cMessage: comment,
    });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

//get a user
export async function get_User() {
  try {
    // const { data } = await axios.get(api + `/users`); route didn't work, return static data
    return {
      mUser: "alb323",
      mFirstName: "Abraham",
      mLastName: "Berlin",
      mEmail: "alb323@lehigh.edu",
      mBio: "MY NAME IS JEFF AND I LOVE YOU",
    };
  } catch (error) {
    console.log(error.message);
  }
}
//modify user
export async function modify_User(mUser, mFirstName, mEmail, mBio) {
  try {
    await axios.patch(api + `/users/${mUser}`, {
      mUser,
      mFirstName,
      mEmail,
      mBio,
    });
  } catch (error) {
    console.log(error.message);
  }
} (edited) 

/*
//Google oAuth ??
function onSignIn(googleUser) {
  var id_token = googleUser.getAuthResponse().id_token;
  var xhr = new XMLHttpRequest();
  xhr.open('POST', 'https://hidden-sierra-98950.herokuapp.com/tokensignin');
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.onload = function() {
    console.log('Signed in as: ' + xhr.responseText);
  };
  xhr.send('idtoken=' + id_token);
} */