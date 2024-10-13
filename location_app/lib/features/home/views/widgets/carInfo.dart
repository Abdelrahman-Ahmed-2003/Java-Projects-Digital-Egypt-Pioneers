import 'package:flutter/material.dart';
import 'package:location_app/core/dealingHive.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';
import 'package:provider/provider.dart';

class CarInfo extends StatefulWidget {
  final TextEditingController gasCostController;
  CarInfo({super.key, required this.gasCostController});

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

  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
    //_loadGasPrice();
  }

  // Future<void> _loadGasPrice() async {
  //   double? gasPrice = await DealingWithHive.getGasPrice();
  //     widget.gasCostController.text = gasPrice?.toString() ?? '0.0';
  // }

  @override
  Widget build(BuildContext context) {
    var cubit = context.read<DataCubit>();
    widget.gasCostController.text = DealingWithHive.gasPrice.toString();
    return Column(
      children: [
        Form(
          autovalidateMode: AutovalidateMode.onUserInteraction,
          key: formKey,
          child: TextFormField(
            style: const TextStyle(
              color: Colors.white,
            ),
            validator: (value) {
              if(double.tryParse(value!) == null){
                return "Please enter a valid number";
              }
              else if (value.isEmpty || double.parse(value) == 0.0) {
                return "Gas cost can't be empty, non-numeric, or = 0.0";
              }
              return null;
            },
            controller: widget.gasCostController,
            decoration: const InputDecoration(
              labelText: 'price of 1L of Gas/KM',
              labelStyle: TextStyle(
                color: Colors.white,
              ),
              enabledBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Colors.white,
                ),
              ),
              focusedBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Color.fromARGB(255, 4, 33, 73),
                ),
              ),
              errorBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Colors.white, // Keep it the same as enabledBorder
                ),
              ),
              focusedErrorBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Color.fromARGB(255, 4, 33, 73), // Same as focusedBorder
                ),
              ),
            ),
          ),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            DropdownButton(
              dropdownColor: Colors.black,
              value: cubit.carKind.isEmpty ? "kind" : cubit.carKind,
              items: kindArray.map((String value) {
                return DropdownMenuItem(
                  value: value,
                  child: Text(value, style: const TextStyle(color: Colors.white)),
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
                  child: Text(value, style: const TextStyle(color: Colors.white)),
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