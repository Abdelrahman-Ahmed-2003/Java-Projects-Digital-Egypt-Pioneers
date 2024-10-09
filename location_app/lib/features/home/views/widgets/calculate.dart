import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:location_app/core/area.dart';
import 'package:location_app/core/dealingHive.dart';
import 'package:location_app/core/localHive.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';
import 'package:location_app/features/home/funcitons/calc.dart';
import 'package:quickalert/quickalert.dart';

class Calculate extends StatelessWidget {
  Calculate({super.key});
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
        onPressed: () async{
          var cubit = Provider.of<DataCubit>(context, listen: false);
          if (cubit.destenations.length <= 1) {
            error = 'Please add at least 2 destinations';
          } else if (cubit.carKind == '') {
            error = 'Kind of car is required';
          } else if (cubit.ccKind == 0) {
            error = 'Kind of cc is required';
          } else {
            error = '';
            List<Area> best = findBestRoute(cubit.destenations);
            cubit.totalDistance = calculateRouteDistance(best);
            cubit.bestRoute = cubit.destenations[0].name;
            for (int i = 1; i < best.length; i++) {
              cubit.bestRoute += '-> ${best[i].name}';
            }
            double gasCost = cubit.setDest() / 100;
            gasCost += (cubit.ccKind / 100.0) * 0.1;
            gasCost *= 9.25;
            gasCost *= cubit.totalDistance;
            cubit.gasCost = gasCost;

            StoreLocation store = StoreLocation(
                carKind: cubit.carKind,
                ccKind: cubit.ccKind,
                bestRoute: cubit.bestRoute,
                gasCost: cubit.gasCost,
                totalDistance: cubit.totalDistance);
            DealingWithHive.saveData(store);
            cubit.setShow(true);
          }
          if(error != ''){
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
