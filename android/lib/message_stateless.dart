import 'dart:convert';
import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'new_message_stateless.dart';
import 'post.dart';
import 'like.dart';
import 'package:http/http.dart' as http;
import "login.dart";
import "message_card.dart";

String api =
    "https://raw.githubusercontent.com/abraham-berlin/filehost/main/test.json";
//DATA MODEL

const primaryColor = Color(0xFF585858);

//GET REQ
Future<List<Post>> fetchPost(http.Client client) async {
  final response = await client.get(Uri.parse(api));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return compute(parsePost, response.body);
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to load post');
  }
}

//Helper function to converts string body into a List<Post>
List<Post> parsePost(String responseBody) {
  final parsed = jsonDecode(responseBody)['data'].cast<Map<String, dynamic>>();

  return parsed.map<Post>((json) => Post.fromJson(json)).toList();
}

// Future<Post> fetchMessage(int id) async {
//   final response =
//       await http.get(Uri.parse(api + "/messages/" + id.toString()));

//   if (response.statusCode == 200) {
//     // If the server did return a 200 OK response,
//     // then parse the JSON.
//     return Post.fromJson(jsonDecode(response.body));
//   } else {
//     // If the server did not return a 200 OK response,
//     // then throw an exception.
//     throw Exception('Failed to load album');
//   }
// }

//Put req to update Likes
Future<http.Response> likePost(Like post) {
  return http.put(Uri.parse(api), body: jsonEncode(post.toJson()));
}

//END OF DATA MODEL

class Message extends StatefulWidget {
  const Message({Key? key}) : super(key: key);

  @override
  _MessageState createState() => _MessageState();
}

class _MessageState extends State<Message> {
  //late Future<List<Post>> futurePost;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //Create top bar for app
      appBar: AppBar(
        title: Image.asset(
          '../assets/buzzlogo.png',
          height: 100,
        ),
        backgroundColor: primaryColor,
        actions: [
          //Button to go to new message screen
          IconButton(
              icon: const Icon(Icons.add_outlined), onPressed: _pushSaved),
          IconButton(
              icon: const Icon(Icons.login_outlined),
              onPressed: _pushTologinPage),
        ],
      ),
      body: FutureBuilder<List<Post>>(
        future: fetchPost(http.Client()),
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            //If GET errored
            return const Center(
              child: Text('An error has occurred!'),
            );
          } else if (snapshot.hasData) {
            //if GET got data
            return _BuildMessages(snapshot.data!);
          } else {
            return const Center(
              child: CircularProgressIndicator(), // while its GETTING
            );
          }
        },
      ),
    );
  }

  Widget _BuildMessages(List<Post> messages) {
    return ListView.builder(
        itemCount: messages.length + 5, //count though all messsages in the list
        padding: const EdgeInsets.all(16.0),
        itemBuilder: /*1*/ (context, i) {
          if (i.isOdd)
            return const Divider(); // Add small break inbetween cards
          final index = i ~/ 2; /*3*/
          return Center(
            child: _buildRow(messages[index]),
          ); //build card
        });
  }

  Widget _buildRow(Post post) {
    return MessageCard(post);
  }

  void _pushSaved() {
    Navigator.of(context).push(
      // NEW lines from here...
      MaterialPageRoute<void>(
        builder: (BuildContext context) {
          return const Scaffold(
            //GO TO new message screen
            body: NewMessage(),
          );
        },
      ), // ...to here.
    );
  }

  void _pushTologinPage() {
    Navigator.of(context).push(
      // NEW lines from here...
      MaterialPageRoute<void>(
        builder: (BuildContext context) {
          return Scaffold(
            //GO TO new message screen
            body: SignInDemo(),
          );
        },
      ), // ...to here.
    );
  }
}
