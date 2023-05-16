import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'dart:convert';
import 'package:http/http.dart' as http;

String api =
    "https://raw.githubusercontent.com/abraham-berlin/filehost/main/test.json";

// ignore_for_file: file_names
class NewSignUpForm {
  final String name;
  final String username;
  final String password;

  NewSignUpForm(this.name, this.username, this.password);

  NewSignUpForm.fromJson(Map<String, dynamic> json)
      : name = json['name'],
        username = json['username'],
        password = json['password'];

  Map<String, dynamic> toJson() => {
        'name': name,
        'username': username,
        'password': password,
      };
}

Future<http.Response> postLogin(NewSignUpForm post) {
  return http.post(Uri.parse(api), body: jsonEncode(post.toJson()));
}

class SignupPage extends StatefulWidget {
  const SignupPage({Key? key}) : super(key: key);

  @override
  _SignupPageState createState() => _SignupPageState();
}

class _SignupPageState extends State<SignupPage> {
  @override
  Widget build(BuildContext context) {
    return const LoginForm();
  }
}

// Define a custom Form widget.
class LoginForm extends StatefulWidget {
  const LoginForm({Key? key}) : super(key: key);

  @override
  _LoginFormState createState() => _LoginFormState();
}

// Define a corresponding State class.
// This class holds the data related to the Form.
class _LoginFormState extends State<LoginForm> {
  // Create a text controller and use it to retrieve the current value
  // of the TextField.

  final myControllerName = TextEditingController();
  final myControllerUsername = TextEditingController();
  final myControllerPassword = TextEditingController();

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    myControllerUsername.dispose();
    myControllerName.dispose();
    myControllerPassword.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Register Page'),
      ),
      body: Column(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              decoration: const InputDecoration(hintText: 'Name'),
              controller: myControllerName,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              decoration: const InputDecoration(hintText: 'Username'),
              controller: myControllerUsername,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              decoration: const InputDecoration(hintText: 'Password'),
              controller: myControllerPassword,
            ),
          ),
          Column(
            children: <Widget>[
              ElevatedButton(
                  style: ElevatedButton.styleFrom(primary: Colors.green[200]),
                  onPressed: () {
                    NewSignUpForm post = NewSignUpForm(myControllerName.text,
                        myControllerUsername.text, myControllerPassword.text);
                    postLogin(post);
                    showDialog(
                      context: context,
                      builder: (context) {
                        return const AlertDialog(
                          // Retrieve the text the that user has entered by using the
                          // TextEditingController.
                          content: Text('Creating...'),
                        );
                      },
                    );
                  },
                  child: const Text("Submit")),
            ],
          )
        ],
      ),
    );
  }
}
