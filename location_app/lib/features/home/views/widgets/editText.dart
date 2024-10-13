import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:geolocator/geolocator.dart';
import 'package:location_app/core/area.dart';
import 'package:location_app/core/locaiton/current_location.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';
import 'package:quickalert/quickalert.dart';

class TakeText extends StatefulWidget {
  const TakeText({super.key});

  @override
  State<TakeText> createState() => _TakeTextState();
}

class _TakeTextState extends State<TakeText> {
  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  final TextEditingController controller = TextEditingController();
  final CurrentLocation currentLocation = CurrentLocation();

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(children: [
          Expanded(
            child: Form(
                key: formKey,
                child: TextFormField(
                  style: const TextStyle(
                    color: Colors.white,
                  ),
                  validator: (value) {
                    if (value!.isEmpty) {
                      return "Please enter What you need to do :)";
                    }
                    return null;
                  },
                  controller: controller,
                  decoration: const InputDecoration(
                    labelText: 'Enter your Destenation',
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
                        color:
                            Colors.white, // Keep it the same as enabledBorder
                      ),
                    ),
                    focusedErrorBorder: OutlineInputBorder(
                      borderSide: BorderSide(
                        color: Color.fromARGB(
                            255, 4, 33, 73), // Same as focusedBorder
                      ),
                    ),
                  ),
                )),
          ),
          IconButton(
            onPressed: () async {
              var cubit = context.read<DataCubit>();
              String address = await cubit.getLocation();
              if (address == 'No address available' ||
                  address == 'Failed to get position' ||
                  address == 'Error in get location') {
                QuickAlert.show(
                    headerBackgroundColor: const Color.fromARGB(255, 2, 26, 46),
                    backgroundColor: const Color.fromARGB(255, 4, 33, 73),
                    context: context,
                    type: QuickAlertType.error,
                    title: 'error in get location',
                    text: address,
                    barrierDismissible: true,
                    showConfirmBtn: false);
                await Future.delayed(const Duration(seconds: 5));
                if (Navigator.canPop(context)) {
                  Navigator.of(context).pop();
                }
              } else {
                setState(() {
                  controller.text = address;
                });
              }
            },
            icon: const Icon(Icons.location_on, color: Colors.white),
          ),
        ]),
        const SizedBox(height: 20),
        ElevatedButton(
          style: ButtonStyle(
              backgroundColor: WidgetStateProperty.all<Color>(
                  const Color.fromARGB(255, 2, 26, 46))),
          onPressed: () async {
            if (formKey.currentState!.validate()) {
              var cubit = context.read<DataCubit>();
              if (cubit.destenations.isNotEmpty &&
                  cubit.destenations.last.name == controller.text) {
                QuickAlert.show(
                    headerBackgroundColor: const Color.fromARGB(255, 2, 26, 46),
                    backgroundColor: const Color.fromARGB(255, 4, 33, 73),
                    context: context,
                    type: QuickAlertType.error,
                    title: 'this is location is last location',
                    text:
                        'Please enter another location or spacefic area in it which not founded in another loication',
                    barrierDismissible: true,
                    showConfirmBtn: false);
                await Future.delayed(const Duration(seconds: 5));
                if (Navigator.canPop(context)) {
                  Navigator.of(context).pop();
                }
              } else {
                final DataState state;
                Area destenation = Area(controller.text, 0, 0);
                await cubit.addDestenation(destenation);

                state = cubit.state;

                if (state is DataLoading) {
                  cubit.setShow(false);
                  controller.clear();
                  setState(() {});
                } else if (state is DataError) {
                  final state = cubit.state;
                  String error = (state as DataError).error;
                  if (error ==
                          'PlatformException(IO_ERROR, gipi: UNAVAILABLE, null, null)' ||
                      error ==
                          'PlatformException(IO_ERROR, UNAVAILABLE: Unable to resolve host geomobileservices-pa.googleapis.com, null, null)') {
                    error = 'Please check your internet connection';
                  }
                  QuickAlert.show(
                      headerBackgroundColor:
                          const Color.fromARGB(255, 2, 26, 46),
                      backgroundColor: const Color.fromARGB(255, 4, 33, 73),
                      context: context,
                      type: QuickAlertType.error,
                      title: 'error in get location',
                      text: error,
                      barrierDismissible: true,
                      showConfirmBtn: false);
                  await Future.delayed(const Duration(seconds: 5));
                  if (Navigator.canPop(context)) {
                    Navigator.of(context).pop();
                  }
                }
              }
            }
          },
          child: const Text(
            'Add',
            style: TextStyle(color: Colors.white),
          ),
        ),
      ],
    );
  }
}
