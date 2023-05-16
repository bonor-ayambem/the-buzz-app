import 'dart:convert';
import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'new_message_stateless.dart';
import 'post.dart';
import 'like.dart';
import 'package:http/http.dart' as http;
import "login.dart";

class MessageCard extends StatefulWidget {
  final Post post;

  const MessageCard(this.post);

  @override
  _MessageCardState createState() => _MessageCardState();
}

String api =
    "https://raw.githubusercontent.com/abraham-berlin/filehost/main/test.json";
//DATA MODEL
//Put req to update Likes
Future<http.Response> likePost(Like post) {
  return http.put(Uri.parse(api), body: jsonEncode(post.toJson()));
}

class _MessageCardState extends State<MessageCard> {
  final mComment = TextEditingController();

  bool _toggle = false;
  void _toggleComment() {
    setState(() {
      _toggle = !_toggle;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Card(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            //Text portion
            ListTile(
              title: Text(widget.post.title),
              subtitle: Text(widget.post.message),
            ),

            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: <Widget>[
                //UPVOTE
                TextButton(
                  child: const Icon(Icons.thumb_up_outlined),
                  onPressed: () {
                    setState(() {
                      //sent put req to updote like
                      likePost(Like(widget.post.id, widget.post.title,
                          widget.post.likes + 1));
                    });
                  },
                ),
                const SizedBox(width: 8),
                //NUMBER OF LIKES
                Text(widget.post.likes.toString()),
                //DOWNVOTE
                TextButton(
                  child: const Icon(Icons.thumb_down_outlined),
                  onPressed: () {
                    setState(() {
                      //sent put req to downdote like
                      likePost(Like(widget.post.id, widget.post.title,
                          widget.post.likes - 1));
                    });
                  },
                ),
                const SizedBox(width: 8),
              ],
            ),

            if (_toggle == false)
              Padding(
                  padding: const EdgeInsets.all(20),
                  child: TextFormField(
                    controller: mComment,
                    decoration: const InputDecoration(
                        border: UnderlineInputBorder(),
                        labelText: 'Comment Here'),
                  )),
            if (_toggle == false)
              Container(
                margin: const EdgeInsets.only(left: 20, bottom: 20),
                alignment: Alignment.bottomLeft,
                child: ElevatedButton(
                    onPressed: () {
                      _toggleComment();
                      _sendComment(mComment);
                    },
                    style: ElevatedButton.styleFrom(
                      primary: Colors.green[200],
                    ),
                    child: const Text("Send")),
              ),
            if (_toggle == true)
              Container(
                  margin: const EdgeInsets.only(left: 20, bottom: 20),
                  alignment: Alignment.bottomLeft,
                  child: Row(
                    children: [
                      const Text("User X commented "),
                      Text(mComment.text)
                    ],
                  ))
          ],
        ),
      ),
    );
    ;
  }

  void _sendComment(final comment) {}
}
