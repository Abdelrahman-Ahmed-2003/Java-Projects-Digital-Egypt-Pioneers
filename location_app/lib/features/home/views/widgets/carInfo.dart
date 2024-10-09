import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';

class CarInfo extends StatefulWidget {
  const CarInfo({super.key});

  @override
  State<CarInfo> createState() => _CarInfoState();
}

class _CarInfoState extends State<CarInfo> {
  final List<String> ccArray = [
    "CC",
    "1000 CC",
    "1300 CC",
    "1500 CC",
    "1600 CC",
    "2000 CC"
  ];

  final List<String> kindArray = [
    "kind",
    "Sedan",
    "SUV",
    "Truck",
    "Hatchback",
    "Convertible"
  ];

  @override
  Widget build(BuildContext context) {
    var cubit = context.read<DataCubit>();
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            DropdownButton(
              dropdownColor: Colors.black,
              value: cubit.carKind.isEmpty ? "kind" : cubit.carKind,
              items: kindArray.map((String value) {
                return DropdownMenuItem(
                  value: value,
                  child:
                      Text(value, style: const TextStyle(color: Colors.white)),
                );
              }).toList(),
              onChanged: (String? value) {
                if (value != cubit.carKind) {
                  cubit.setShow(false);
                }
                cubit.carKind = value!;
                setState(() {});
              },
            ),
            DropdownButton(
              dropdownColor: Colors.black,
              value: cubit.ccKind == 0 ? "CC" : "${cubit.ccKind} CC",
              items: ccArray.map((String value) {
                return DropdownMenuItem(
                  value: value,
                  child:
                      Text(value, style: const TextStyle(color: Colors.white)),
                );
              }).toList(),
              onChanged: (String? value) {
                final ccKindMatch = RegExp(r'(\d+)').firstMatch(value!);
                if (value != '${cubit.ccKind} CC') {
                  cubit.setShow(false);
                }
                final ccKindValue =
                    ccKindMatch != null ? int.parse(ccKindMatch.group(0)!) : 0;
                cubit.ccKind = ccKindValue;
                setState(() {});
              },
            ),
          ],
        ),
      ],
    );
  }
}
