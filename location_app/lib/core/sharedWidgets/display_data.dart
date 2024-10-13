import 'package:flutter/material.dart';

class DisplayData extends StatelessWidget {
  String route;
  double totalDistance;
  double gasCost;
  String carKind;
  int ccKind;
  DisplayData(
      {super.key,
      required this.route,
      required this.totalDistance,
      required this.gasCost,
      required this.carKind,
      required this.ccKind});

  @override
  Widget build(BuildContext context) {
    return Container(
        width: double.infinity,
        decoration: BoxDecoration(
          color: Colors.grey[700],
          borderRadius: BorderRadius.circular(10),
        ),
        padding: const EdgeInsets.all(10),
        child: Column(
          //mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Route: $route',
              style: const TextStyle(fontSize: 20),
            ),
            Text(
              'Total Distance: ${totalDistance.toStringAsFixed(2)} KM',
              style: const TextStyle(fontSize: 20),
            ),
            Text(
              'Gas Cost: \$ ${gasCost.toStringAsFixed(2)} L',
              style: const TextStyle(fontSize: 20),
            ),
            ccKind != 0
                ? Text(
                    'CC: $ccKind cc',
                    style: const TextStyle(fontSize: 20),
                  )
                : const SizedBox.shrink(),
            carKind != ''
                ? Text(
                    'Car Kind: $carKind',
                    style: const TextStyle(fontSize: 20),
                  )
                : const SizedBox.shrink(),
          ],
        ));
  }
}
