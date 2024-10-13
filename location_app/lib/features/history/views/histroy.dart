import 'package:flutter/material.dart';
import 'package:location_app/core/dealingHive.dart';
import 'package:location_app/core/sharedWidgets/display_data.dart';

class HistoryPage extends StatelessWidget {
  const HistoryPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:const Center(child: Text('History', style: TextStyle(color: Colors.white, fontSize: 36,fontFamily: 'Cursive',fontWeight: FontWeight.bold,))),
        backgroundColor: Colors.black,
      ),
      backgroundColor: Colors.black,
      body: Container(
        padding: const EdgeInsets.all(15),
        color: Colors.black,
        child: FutureBuilder(
          future: DealingWithHive.getData(), // Ensure data is loaded
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
            } else if (snapshot.hasError) {
              return const Center(child: Text('Error loading data'));
            } else {
              var data = DealingWithHive.data.reversed.toList(); // Reverse the list
              return ListView.separated(
                itemBuilder: (context, index) {
                  return DisplayData(
                    route: data[index].route,
                    totalDistance: data[index].totalDistance,
                    gasCost: data[index].gasCost,
                    carKind: data[index].carKind,
                    ccKind: data[index].ccKind,
                  );
                },
                separatorBuilder: (context, index) => const SizedBox(
                  height: 11,
                ),
                itemCount: data.length,
              );
            }
          },
        ),
      ),
    );
  }
}