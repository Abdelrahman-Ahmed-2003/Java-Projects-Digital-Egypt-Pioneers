import 'package:geolocator/geolocator.dart';

class CurrentLocation {
  String errorMessage = '';
  Future<Position> determinePosition() async {
    bool serviceEnabled;
    LocationPermission permission;

    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      print('Location services are disabled.');
      errorMessage = 'Location services are disabled';
      return Future.error('Location services are disabled.');
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        print('Location permissions are denied');
        errorMessage = 'Location permissions are denied';
        return Future.error('Location permissions are denied');
      }
    }

    if (permission == LocationPermission.deniedForever) {
      errorMessage =
          'Location permissions are permanently denied, we cannot request permissions.';
      print(
          'Location permissions are permanently denied, we cannot request permissions.');
      return Future.error(
          'Location permissions are permanently denied, we cannot request permissions.');
    }
    print('Location permissions are granteddddddddddddddddddddddddddd');

    return await Geolocator.getCurrentPosition();
  }

// Future<String> getAddressFromLatLng(Position position) async {
//     try {
//       List<Placemark> placemarks = await placemarkFromCoordinates(
//           position.latitude, position.longitude);

//       if (placemarks.isNotEmpty) {
//         Placemark place = placemarks[0];
//         return "${place.street}, ${place.locality}, ${place.postalCode}, ${place.country}";
//       }
//       return "No address available";
//     } catch (e) {
//       print(e);
//       return "Error occurred while getting address";
//     }
//   }

}
