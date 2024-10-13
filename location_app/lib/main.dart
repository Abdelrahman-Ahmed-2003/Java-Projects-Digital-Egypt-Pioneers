import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:location_app/core/dealingHive.dart';
import 'package:location_app/core/localHive.dart';
import 'package:location_app/features/home/modelV/destenationCubit.dart';
import 'package:location_app/features/pageview.dart';


void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await Hive.initFlutter();
  //await Hive.deleteBoxFromDisk('task_box');
  Hive.registerAdapter(StoreLocationAdapter());
  await Hive.openBox<StoreLocation>("calc_box");
  await Hive.openBox<double>("gasPrice");
  await DealingWithHive.getGasPrice();
  runApp(MultiBlocProvider(
    providers: [
      BlocProvider<DataCubit>(
        create: (context) => DataCubit(),
      ),
      // Add more BlocProviders here as needed
    ],
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const PageViewP(),
      
    );
  }
}

