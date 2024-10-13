import 'package:hive/hive.dart';
import 'package:location_app/core/localHive.dart';

class DealingWithHive {
  static double gasPrice = 9.25;
  static List<StoreLocation> data = [];
  static const String _boxName1 = "calc_box";
  static final Box<StoreLocation> box1 = Hive.box<StoreLocation>(_boxName1);
  static final Box<double> box2 = Hive.box<double>("gasPrice");

  static Future<void> saveData(StoreLocation store) async {
    int existingIndex = data.indexWhere((element) =>
        element.carKind == store.carKind &&
        element.ccKind == store.ccKind &&
        element.route == store.route);

    if (existingIndex != -1) {
      // Move the existing item to the first position
      box1.deleteAt(existingIndex);
      data.removeAt(existingIndex);
    }
    // Add the new item to the first position
    data.insert(0, store);
    await box1.add(store);
  }

  static Future<List<StoreLocation>> getData() async {
    data = box1.values.toList();
    return data;
  }

  static Future<void> getGasPrice() async {
    if (box2.isEmpty) {
      await box2.add(9.25);
      gasPrice = 9.25;
    } else {
      gasPrice = box2.getAt(0)!;
    }
  }

  static Future<void> saveGasPrice(double price) async {
    if (box2.isEmpty) {
      await box2.add(price);
    } else {
      await box2.putAt(0, price);
    }
    gasPrice = price;
  }
}