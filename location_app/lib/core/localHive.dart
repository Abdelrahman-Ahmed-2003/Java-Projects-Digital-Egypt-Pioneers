import 'package:hive/hive.dart';

part 'localHive.g.dart';

@HiveType(typeId: 0)
class StoreLocation extends HiveObject {
  @HiveField(0)
  String carKind = '';
  @HiveField(1)
  int ccKind = 0;
  @HiveField(2)
  String route = '';
  @HiveField(3)
  double gasCost = 0.0;
  @HiveField(4)
  double totalDistance = 0.0;

  StoreLocation(
      {
      required this.carKind,
      required this.ccKind,
      required this.route,
      required this.gasCost,
      required this.totalDistance,
      });
  StoreLocation.empty(){}
}
