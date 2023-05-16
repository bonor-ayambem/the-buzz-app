import 'dart:convert';
import 'dart:js';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:path/path.dart';
import 'camera.dart';
import 'new_post.dart';
import 'package:camera/camera.dart';

String api =
    "https://raw.githubusercontent.com/abraham-berlin/filehost/main/test.json";

const primaryColor = Color(0xFF585858);

Future<http.Response> postPost(NewPost post) {
  return http.post(Uri.parse(api), body: jsonEncode(post.toJson()));
}

class NewMessage extends StatefulWidget {
  const NewMessage({Key? key}) : super(key: key);

  @override
  _NewMessageState createState() => _NewMessageState();
}

class _NewMessageState extends State<NewMessage> {
  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: MyCustomForm(),
    );
  }
}

// Define a custom Form widget.
class MyCustomForm extends StatefulWidget {
  const MyCustomForm({Key? key}) : super(key: key);

  @override
  _MyCustomFormState createState() => _MyCustomFormState();
}

// Define a corresponding State class.
// This class holds the data related to the Form.
class _MyCustomFormState extends State<MyCustomForm> {
  // Create a text controller and use it to retrieve the current value
  // of the TextField.
  final myControllerTitle = TextEditingController();
  final myControllerMessage = TextEditingController();

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    myControllerTitle.dispose();
    myControllerMessage.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('New Message'),
          backgroundColor: primaryColor,
        ),
        body: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                decoration: const InputDecoration(hintText: 'Title'),
                controller: myControllerTitle,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                decoration: const InputDecoration(hintText: 'Message'),
                controller: myControllerMessage,
              ),
            ),
          ],
        ),
        floatingActionButton:
            Column(mainAxisAlignment: MainAxisAlignment.end, children: [
          FloatingActionButton(
            onPressed: () {},
            tooltip: 'Add Attachment',
            child: const Icon(Icons.attach_file_outlined),
            backgroundColor: Colors.amber,
          ),
          const SizedBox(
            height: 10,
          ),
          FloatingActionButton(
            onPressed: () {
              pushToCamera();
            },
            tooltip: 'Upload Photo',
            child: const Icon(Icons.add_a_photo_outlined),
            backgroundColor: Colors.amber,
          ),
          const SizedBox(
            height: 10,
          ),
          FloatingActionButton(
            onPressed: () {
              NewPost post =
                  NewPost(myControllerTitle.text, myControllerMessage.text);
              postPost(post);
              showDialog(
                context: context,
                builder: (context) {
                  return const AlertDialog(
                    // Retrieve the text the that user has entered by using the
                    // TextEditingController.
                    content: Text('Posted!!'),
                  );
                },
              );
            },
            tooltip: 'Post',
            child: const Icon(Icons.done_outlined),
            backgroundColor: Colors.amber.shade700,
          )
        ]));
  }
}

Future<void> pushToCamera() async {
  // Ensure that plugin services are initialized so that `availableCameras()`
  // can be called before `runApp()`
  WidgetsFlutterBinding.ensureInitialized();

  // Obtain a list of the available cameras on the device.
  final cameras = await availableCameras();

  // Get a specific camera from the list of available cameras.
  final firstCamera = cameras.first;

  runApp(
    MaterialApp(
      home: TakePictureScreen(
        // Pass the appropriate camera to the TakePictureScreen widget.
        camera: firstCamera,
      ),
    ),
  );
}

/*
File _image;
_imgFromCamera() async {
  File image = await ImagePicker.pickImage(
    source: ImageSource.camera, imageQuality: 50
  );

  setState(() {
    _image = image;
  });
}

_imgFromGallery() async {
  File image = await  ImagePicker.pickImage(
      source: ImageSource.gallery, imageQuality: 50
  );

  setState(() {
    _image = image;
  });
}*/