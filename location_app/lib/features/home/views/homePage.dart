import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:location_app/core/sharedWidgets/display_data.dart';
import 'package:location_app/features/home/views/widgets/carInfo.dart';
import 'package:location_app/features/home/views/widgets/editText.dart';
import 'package:location_app/features/home/views/widgets/calculate.dart';
import 'package:location_app/features/home/views/widgets/logo.dart';
import 'package:location_app/features/home/views/widgets/displayDest.dart';
import '../modelV/destenationCubit.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    TextEditingController gasCostController = TextEditingController();
    var cubit = context.watch<DataCubit>();
    return Scaffold(
      backgroundColor: Colors.black,
      body: SafeArea(
        child: SingleChildScrollView(
          child: Container(
            padding: const EdgeInsets.all(15),
            width: double.infinity,
            color: Colors.black,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Center(child: Logo()),
                const SizedBox(height: 20),
                const TakeText(),
                const SizedBox(height: 20),
                const DisplayDest(),
                const SizedBox(height: 20),
                
                CarInfo(gasCostController: gasCostController,),
                Calculate(gasCostController: gasCostController,),
                BlocBuilder<DataCubit, DataState>(
                  builder: (context, state) {
                    return cubit.getSHow()
                        ? DisplayData(
                            route: cubit.convertRoute(),
                            totalDistance: cubit.totalDistance,
                            gasCost: cubit.gasCost,
                            carKind: '',
                            ccKind: 0,
                          )
                        : const SizedBox.shrink();
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}