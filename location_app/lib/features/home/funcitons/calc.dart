
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:location_app/core/area.dart';

double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
  return Geolocator.distanceBetween(lat1, lon1, lat2, lon2) / 1000; // in kilometers
}

double calculateRouteDistance(List<Area> route) {
  double totalDistance = 0.0;
  for (int i = 0; i < route.length - 1; i++) {
    totalDistance += calculateDistance(
        route[i].lattiude, route[i].longitude, route[i + 1].lattiude, route[i + 1].longitude);
  }
  return totalDistance;
}

List<Area> findBestRoute(List<Area> areasList) {
  if (areasList.isEmpty) {
    return [];
  }
  Area startLocation = areasList[0];
  List<Area> locationsToPermute = List<Area>.from(areasList.sublist(1, areasList.length));
  List<Area> bestRoute = [];
  double bestDistance = double.maxFinite;

  List<List<Area>> permutations = generatePermutations(locationsToPermute);

  for (List<Area> perm in permutations) {
    List<Area> route = [];
    route.add(startLocation);
    route.addAll(perm);
    double distance = calculateRouteDistance(route);
    if (distance < bestDistance) {
      bestDistance = distance;
      bestRoute = List<Area>.from(route);
    }
  }
  return bestRoute;
}

List<List<Area>> generatePermutations(List<Area> list) {
  List<List<Area>> permutations = [];
  if (list.isEmpty) {
    permutations.add([]);
    return permutations;
  }
  Area firstElement = list.removeAt(0);
  List<List<Area>> remainingPermutations = generatePermutations(list);
  for (List<Area> perm in remainingPermutations) {
    for (int i = 0; i <= perm.length; i++) {
      List<Area> temp = List<Area>.from(perm);
      temp.insert(i, firstElement);
      permutations.add(temp);
    }
  }
  return permutations;
}


//double? baseGasUsagePer100Km;
