import 'package:flutter/material.dart';
import 'message_stateless.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Startup Name Generator',
      home: Message(),
    );
  }
}
