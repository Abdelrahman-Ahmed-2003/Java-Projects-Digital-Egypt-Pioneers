import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:geolocator/geolocator.dart';
import 'package:location_app/core/area.dart';
import 'package:location_app/core/locaiton/current_location.dart';
import 'package:geocoding/geocoding.dart';

class DataCubit extends Cubit<DataState> {
  DataCubit() : super(DataInitial());
  Position? position;
  List<Area> destenations = [];
  String carKind = '';
  int ccKind = 0;
  String bestRoute = '';
  double gasCost = 0.0;
  double totalDistance = 0.0;
  bool _isData = false;

  void setShow(bool isData) {
    if (_isData != isData) {
      print('set showwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww');
      _isData = isData;
      emit(DataLoading());
    }
    else {
      _isData = isData;
    }
    
  }

  bool getSHow() {
    return _isData;
  }

  Future<void> addDestenation(Area destenation) async {
    try {
      var list = await locationFromAddress(destenation.name);
      destenation.lattiude = list[0].latitude;
      destenation.longitude = list[0].longitude;
      destenations.add(destenation);
      print(
          'adddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd');
      emit(DataLoading());
    } catch (e) {
      print(
          'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerror in get locationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn');
      print(e.toString());
      emit(DataError(e.toString()));
    }
  }

  void removeDestenation(int index) {
    destenations.removeAt(index);
    emit(DataLoading());
  }

  getLocation() async {
    try {
      CurrentLocation location = CurrentLocation();
      position = await location.determinePosition();
      emit(DataLoading());
    } catch (e) {
      print(
          'error in get location.tostringgggggggggggggggggggggggggggggggggggggggggggggggg');
      print(e.toString());
      emit(DataError(e.toString()));
    }
  }

  double setDest() {
    switch (carKind) {
      case "Sedan":
        return 10.0; // 10 liters per 100 km
      case "SUV":
        return 12.0; // 12 liters per 100 km
      case "Truck":
        return 15.0; // 15 liters per 100 km
      case "Hatchback":
        return 9.0; // 9 liters per 100 km
      case "Convertible":
        return 14.0; // 14 liters per 100 km
      default:
        return 10.0; // Default to Sedan
    }
  }
}

class DataLoading extends DataState {
  DataLoading();
}

class DataState {}

class DataInitial extends DataState {
  DataInitial();
}

class DataError extends DataState {
  final String error;
  DataError(this.error);
}
