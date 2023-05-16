
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
 
class newWeather extends StatefulWidget {
  //create a new state for the weather API
  @override
  newWeatherState createState() => newWeatherState();
}
 
//use that new state
class newWeatherState extends State<newWeather> {
//A function to call getLocation() get it from the Geolocator class
  @override
  void getLocation() async {
    Location location = Location();
    await location.getLocation();
  }
 
//having trouble implimenting this, mostly because
  void getData() async {}
 
  @override
  Widget build(BuildContext context) {
    getLocation();
    getData();
    return Scaffold();
  }
}
 
class Location {
  late double long;
  late double lat;
 
  Future<void> getLocation() async {
    try {
      Position position = await Geolocator.getCurrentPosition(
          desiredAccuracy: LocationAccuracy.low);
      lat = position.latitude;
      long = position.longitude;
    } catch (e) {
      //I'm not sure what to put here
    }
  }
}
