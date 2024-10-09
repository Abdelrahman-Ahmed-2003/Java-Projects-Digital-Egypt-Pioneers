import 'package:hive/hive.dart';
import 'package:location_app/core/localHive.dart';

class DealingWithHive {
  static List<StoreLocation> data = [];
  static const String _boxName = "calc_box";
  static final Box<StoreLocation> box = Hive.box<StoreLocation>(_boxName);

  static Future<void> saveData(StoreLocation store) async {
    int existingIndex = data.indexWhere((element) =>
        element.carKind == store.carKind &&
        element.ccKind == store.ccKind &&
        element.bestRoute == store.bestRoute);

    if (existingIndex != -1) {
      // Move the existing item to the first position
      box.deleteAt(existingIndex);
      data.removeAt(existingIndex);
    }
    // Add the new item to the first position
    data.insert(0, store);
    await box.add(store);
  }

  static Future<List<StoreLocation>> getData() async {
    data = box.values.toList();
    print('data gettedddddddddddddddddddddddddddddddddddddddddddd');
    print(data.toList().toString());
    return data;
  }
}
