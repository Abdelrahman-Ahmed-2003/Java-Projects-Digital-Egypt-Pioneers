import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';

class DisplayDest extends StatefulWidget {
  const DisplayDest({super.key});

  @override
  State<DisplayDest> createState() => _DisplayDestState();
}

class _DisplayDestState extends State<DisplayDest> {
  @override
  Widget build(BuildContext context) {
    DataCubit cubit = context.read<DataCubit>();
    return BlocBuilder<DataCubit, DataState>(
      builder: (context, state) {
        return Container(
          height: MediaQuery.of(context).size.height * 0.2,
          child: ListView.separated(
            itemCount: cubit.destenations.length,
            itemBuilder: (context, index) {
              return Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius:
                      BorderRadius.circular(15), // Set the border radius here
                ),
                child: ListTile(
                  title: Text(cubit.destenations[index].name),
                  trailing: ElevatedButton(
                      style:
                          ElevatedButton.styleFrom(backgroundColor: Colors.black),
                      onPressed: () {
                        cubit.setShow(false);
                        cubit.removeDestenation(index);
                      },
                      child: const Text('Delete')),
                ),
              );
            },
            separatorBuilder: (BuildContext context, int index) {
              return const SizedBox(height: 10);
            },
          ),
        );
      },
    );
  }
}
