import 'package:flutter/material.dart';
import 'package:location_app/features/history/views/histroy.dart';
import 'package:location_app/features/home/views/homePage.dart';


class PageViewP extends StatefulWidget {
  const PageViewP({super.key});

  @override
  State<PageViewP> createState() => _PageViewPState();
}

class _PageViewPState extends State<PageViewP> {
  final PageController _pageController = PageController();
  int selectedIndex = 0;
  void _onItemTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
    _pageController.jumpToPage(index);
  }

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      body: PageView(
              controller: _pageController,
              onPageChanged: (index) {
                setState(() {
                  selectedIndex = index;
                });
              },
              children: const[
                HomePage(),
                HistoryPage(),
              ],
            ),
            bottomNavigationBar: BottomNavigationBar(
              backgroundColor: const Color.fromARGB(255, 4, 33, 73),
              items: const <BottomNavigationBarItem>[
                BottomNavigationBarItem(
                  icon: Icon(Icons.home),
                  label: 'Home',
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.history),
                  label: 'History',
                ),
              ],
              selectedItemColor: Colors.teal,
              unselectedItemColor: Colors.white,
              currentIndex: selectedIndex,
              type: BottomNavigationBarType.fixed,
              onTap: _onItemTapped
            ),
    );
  }
}
