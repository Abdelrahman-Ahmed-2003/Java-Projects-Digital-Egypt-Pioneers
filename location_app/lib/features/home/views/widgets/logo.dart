import 'package:flutter/material.dart';

class Logo extends StatelessWidget {
  const Logo({super.key});

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.center, // Center everything inside the Stack
      children: [
        // Image as background
        Image.asset(
          'assets/logo/gas.png',
          color: const Color.fromARGB(255, 4, 33, 73),
          height: 70,
          width: 70,
        ),
        // Text over the image
        const Positioned(
          child: Text(
            'Reach Fast',
            style: TextStyle(
              fontFamily: 'Cursive',
              fontSize: 36,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
        ),
      ],
    );
  }
}
