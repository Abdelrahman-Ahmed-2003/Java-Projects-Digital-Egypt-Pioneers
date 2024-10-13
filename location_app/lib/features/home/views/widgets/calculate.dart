import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:location_app/core/dealingHive.dart';
import 'package:location_app/core/localHive.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';
import 'package:location_app/features/home/funcitons/calc.dart';
import 'package:quickalert/quickalert.dart';

class Calculate extends StatelessWidget {
  TextEditingController gasCostController;
  Calculate({super.key, required this.gasCostController});
  String error = '';
  @override
  Widget build(BuildContext context) {
    return Center(
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: const Color.fromARGB(255, 4, 33, 73),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
          ),
        ),
        onPressed: () async {
          var cubit = Provider.of<DataCubit>(context, listen: false);
          if (cubit.destenations.length <= 1) {
            error = 'Please add at least 2 destinations';
          } else if (cubit.carKind == '') {
            error = 'Kind of car is required';
          } else if (cubit.ccKind == 0) {
            error = 'Kind of cc is required';
          } else if (gasCostController.text.isEmpty ||
              double.parse(gasCostController.text) == 0.0) {
            error = "Gas cost is can't be = 0.0";
          } else {
            error = '';
            cubit.totalDistance = calculateRouteDistance(cubit.destenations);
            
            double gasCost = cubit.setDest() / 100;
            gasCost += (cubit.ccKind / 100.0) * 0.1;
            gasCost *= cubit.price1LGas;
            gasCost *= cubit.totalDistance;
            cubit.gasCost = gasCost;

            StoreLocation store = StoreLocation(
                carKind: cubit.carKind,
                ccKind: cubit.ccKind,
                route: cubit.convertRoute(),
                gasCost: cubit.gasCost,
                totalDistance: cubit.totalDistance);
            DealingWithHive.saveData(store);
            cubit.setShow(true);
          }
          if (error != '') {
            QuickAlert.show(
                headerBackgroundColor: const Color.fromARGB(255, 2, 26, 46),
                backgroundColor: const Color.fromARGB(255, 4, 33, 73),
                context: context,
                type: QuickAlertType.error,
                title: error,
                barrierDismissible: true,
                showConfirmBtn: false);
            await Future.delayed(const Duration(seconds: 5));
            if (Navigator.canPop(context)) {
              Navigator.of(context).pop();
            }
          }
        },
        child: const Text('Calc', style: TextStyle(color: Colors.white)),
      ),
    );
  }
}
