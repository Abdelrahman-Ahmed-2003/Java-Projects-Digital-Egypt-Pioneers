package com.abdelrahman.metro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {
    public  boolean is_reversedl1 = false;
    public  boolean is_reversedl2 = false;
    public  boolean is_reversedl3 = false;

    public ArrayList<String> startStation = new ArrayList<>();
    public ArrayList<String> endStation = new ArrayList<>();

    List<List<String>> allRoutes = new ArrayList<>();
    List<Integer> routeLengths = new ArrayList<>();


    public  final String FIRST_LINE_FORWARD = "Elmarg Direction";
    public  final String FIRST_LINE_REVERSED = "Helwan Direction";
    public  final String SECOND_LINE_FORWARD = "Shubra Al-Khaimah Direction";
    public  final String SECOND_LINE_REVERSED = "El Munib Direction";
    public   String THIRD_LINE_FORWARD = "Kit Kat Direction";
    public   String THIRD_LINE_REVERSED = "Adly Mansour Direction";

    public  String routes = "";
    public  String optimalRoute = "";
    public String lastOptimalRoute = "";
    public String startLineOptimal = "";
    public String endLineOptimal = "";

    public  String timeToArrive = "",ticketPrice = "";
    public  String countStations = "";
    List<String> interchangeStations =  new ArrayList<>();
    List<String> tempInterchangeStations =  new ArrayList<>();
    List<String> route = new ArrayList<>();



    public void defult()
    {
        interchangeStations.clear();
        tempInterchangeStations.clear();
        is_reversedl1 = false;
        is_reversedl2 = false;
        is_reversedl3 = false;


        startStation.clear();
        endStation.clear();

        allRoutes.clear();
        routeLengths.clear();
        route.clear();

        THIRD_LINE_FORWARD = "Kit Kat Direction";
        THIRD_LINE_REVERSED = "Adly Mansour Direction";

        routes = "";
        optimalRoute = "";

       timeToArrive = "";
       ticketPrice = "";
        countStations = "";
        lastOptimalRoute = "";
        startLineOptimal = "";
        endLineOptimal = "";
        tempThirdLine = new ArrayList<>(thirdLine);
        Eltafre3teen.clear();
        tempEltafreaa1 = new ArrayList<>(Eltafreaa1);
        tempEltafreaa2 = new ArrayList<>(Eltafreaa2);
    }

    public   final ArrayList<String> allLines = new ArrayList<>(Arrays.asList("select Station","Helwan", "Ain.Helwan", "Helwan University",
            "Wadi hof", "Hadik Helwan", "Elmasara", "Tora Elasmant", "Kotcika",
            "Tora Elbalad", "Thakanat Elmaadi", "Elmaadi", "Hadik Elmaadi",
            "Dar Elsalam", "Elzahra", "Maregerges", "Elmalek Elsaleh",
            "Elsaida Zainab", "Saad Zaghlol", "Elsadat", "Jamal Abdulnasser",
            "Ahmed Oraby", "Elshohada", "Ghamra", "Eldemerdash", "Manshia.Elsadr",
            "Kobre.Eloba", "Hamamat.Elkoba", "Saria.Elkoba", "Hadaik.Elzaiton",
            "Helmit.Elzaiton", "Elmataria", "Ain.Shams", "Ezbet.Elnakhl", "Elmarg",
            "Elmarg.Elgededa","El Munib", "saqiat makki", "dawahi algiza", "Algiza",
            "Faisal", "Cairo university", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah","Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero", "Kit Kat","Elsodan", "embaba", "Albohee", "Alkomiaa Alarabia", "Altareek Aldaeree", "rod elfarag","Eltawfiqia", "wadi Elneel", "game3t eldwal",
            "bolak Aldakror", "Cairo university"));


    public  final ArrayList<String> firstLine = new ArrayList<>(Arrays.asList("Helwan", "Ain.Helwan", "Helwan University",
            "Wadi hof", "Hadik Helwan", "Elmasara", "Tora Elasmant", "Kotcika",
            "Tora Elbalad", "Thakanat Elmaadi", "Elmaadi", "Hadik Elmaadi",
            "Dar Elsalam", "Elzahra", "Maregerges", "Elmalek Elsaleh",
            "Elsaida Zainab", "Saad Zaghlol", "Elsadat", "Jamal Abdulnasser",
            "Ahmed Oraby", "Elshohada", "Ghamra", "Eldemerdash", "Manshia.Elsadr",
            "Kobre.Eloba", "Hamamat.Elkoba", "Saria.Elkoba", "Hadaik.Elzaiton",
            "Helmit.Elzaiton", "Elmataria", "Ain.Shams", "Ezbet.Elnakhl", "Elmarg",
            "Elmarg.Elgededa"));

    public  final ArrayList<String> secondLine = new ArrayList<>(Arrays.asList("El Munib", "saqiat makki", "dawahi algiza", "Algiza",
            "Faisal", "Cairo university", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah"));

    public  ArrayList<String> thirdLine = new ArrayList<>(Arrays.asList("Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero", "Kit Kat"));
    ArrayList<String> tempThirdLine = new ArrayList<>(thirdLine);

    public  final ArrayList<String> Eltafreaa1 = new ArrayList<>(Arrays.asList("Elsodan", "embaba", "Albohee", "Alkomiaa Alarabia", "Altareek Aldaeree", "rod elfarag"));
    public  final ArrayList<String> Eltafreaa2 = new ArrayList<>(Arrays.asList("Eltawfiqia", "wadi Elneel", "game3t eldwal",
            "bolak Aldakror", "Cairo university"));

    public  ArrayList<String> tempEltafreaa1 = new ArrayList<>(Eltafreaa1);
    public  ArrayList<String> tempEltafreaa2 = new ArrayList<>(Eltafreaa2);
    public   ArrayList<String> Eltafre3teen = new ArrayList<>() ;

    public  void knowAllLine(String station,ArrayList<String>add) {
        if (firstLine.contains(station)) {
            add.add("line1");
        }if (thirdLine.contains(station) || Eltafreaa2.contains(station)||Eltafreaa1.contains(station)) {
            add.add("line3");
        }if (secondLine.contains(station)) {
            add.add("line2");
        }
    }

    public ArrayList<String> getTheLine(String line){
        if (line == "line1") {
            return firstLine;
        }if (line == "line2") {
            return secondLine;
        }if (line == "line3") {
            return thirdLine;
        }
        else return null;
    }

    public  List<String> getSimpleRoute(String start, String end, ArrayList<String> line) {
        System.out.println(start + "      "+ end);
        System.out.println(line);
        if(line.contains("Adly Mansour"))
            line = checkEltafreaa(start,end);
        System.out.println(line);

        int startIndex = line.indexOf(start);
        int endIndex = line.indexOf(end);


        List<String> route = new ArrayList<>(line.subList(Math.min(startIndex, endIndex), Math.max(startIndex, endIndex) + 1));
        if (startIndex > endIndex) {
            Collections.reverse(route);
            if (line == firstLine) {
                is_reversedl1 = true;
            } else if (line == secondLine) {
                is_reversedl2 = true;
            } else if (line == tempThirdLine) {
                is_reversedl3 = true;
            }
        }
        return route;
    }

    public  void getInterchangeStations(String startLine, String endLine) {
        tempInterchangeStations.clear();
        if ((startLine == "line1" && endLine == "line2") || (startLine == "line2" && endLine == "line1")) {
            tempInterchangeStations.add("Elsadat");
            tempInterchangeStations.add("Elshohada");
        }
        if ((startLine == "line2" && endLine == "line3") || (startLine == "line3" && endLine == "line2")) {
            tempInterchangeStations.add("El-Ataba");
            tempInterchangeStations.add("Cairo university");
        }
        if ((startLine == "line1" && endLine == "line3") || (startLine == "line3" && endLine == "line1")) {
            tempInterchangeStations.add("Jamal Abdulnasser");
        }
        interchangeStations.addAll(tempInterchangeStations);
    }

    public  List<String> getMergedRoute(String start, String interchangeStation, String end, ArrayList<String> startLine, ArrayList<String> endLine) {
        List<String> route1 = getSimpleRoute(start, interchangeStation, startLine);
        List<String> route2 = getSimpleRoute(interchangeStation, end, endLine);
        List<String> mergedRoute = new ArrayList<>(route1);
        mergedRoute.addAll(route2.subList(1, route2.size()));
        System.out.println("route one is " + route1);
        System.out.println("route two is "+route2);
        System.out.println("merged route is " +mergedRoute);
        return mergedRoute;
    }

    public  String printDirection(ArrayList<String> line) {
        if (line == firstLine) {
            return ("Direction: " + (is_reversedl1 ? FIRST_LINE_REVERSED : FIRST_LINE_FORWARD)+ "\n");
        } else if (line == secondLine) {
            return ("Direction: " + (is_reversedl2 ? SECOND_LINE_REVERSED : SECOND_LINE_FORWARD)+ "\n");
        } else if (line == tempThirdLine) {
            return ("Direction: " + (is_reversedl3 ? THIRD_LINE_REVERSED : THIRD_LINE_FORWARD) + "\n");
        }
        return "";
    }

    public ArrayList<String> checkEltafreaa(String start , String end){
        tempThirdLine = new ArrayList<>(thirdLine);
        System.out.println("check tafreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaa");
        if ((Eltafreaa1.contains(start) && Eltafreaa2.contains(end)) || (Eltafreaa2.contains(start) && Eltafreaa1.contains(end) )){
            Collections.reverse(tempEltafreaa1);
            Eltafre3teen.addAll(tempEltafreaa1);
            Eltafre3teen.add("Kit Kat");
            Eltafre3teen.addAll(tempEltafreaa2);
            tempThirdLine = Eltafre3teen;
            THIRD_LINE_FORWARD = "El Munib Direction";
            THIRD_LINE_REVERSED ="rod elfarag Direction";
        }  else if (Eltafreaa2.contains(start) || Eltafreaa2.contains(end)) {
            tempThirdLine.addAll(Eltafreaa2);
            THIRD_LINE_FORWARD = "El Munib Direction";
        }else if (Eltafreaa1.contains(start) || Eltafreaa1.contains(end)) {
            tempThirdLine.addAll(Eltafreaa1);
            THIRD_LINE_FORWARD = "rod elfarag Direction";
        }
        return tempThirdLine;
    }

    public boolean checkList(List list)
    {
        for(List i : allRoutes){
            if(i.equals(list))
                return false;
        }
        return true;
    }

    public  void calc(String start , String end) {
        defult();
        knowAllLine(start,startStation);
        knowAllLine(end,endStation);

        for(int i = 0; i < startStation.size();++i)
        {
            for(int j = 0 ; j < endStation.size();++j)
            {
                ArrayList<String> startStationLine = getTheLine(startStation.get(i));
                ArrayList<String> endStationLine = getTheLine(endStation.get(j));

                System.out.println(startStation.get(i)+"      "+endStation.get(j));
                if (Objects.equals(startStation.get(i), endStation.get(j))) {
                    System.out.println("in same lineeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                    route = getSimpleRoute(start, end, startStationLine);
                    if(checkList(route)){

                        routes += ("Route in the same line : " + route + " (Length: " + route.size() + ")"+"\n");
                        routes += printDirection(startStationLine);
                        allRoutes.add(route);
                        routeLengths.add(route.size());
                    }
                } else {
                    System.out.println("not in same line eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

                    getInterchangeStations(startStation.get(i), endStation.get(j));

                    System.out.println("inter change stations is ");


                    for (String interchangeStation : tempInterchangeStations) {
                        System.out.println(interchangeStation);

                        route = getMergedRoute(start, interchangeStation, end, startStationLine, endStationLine);
                        System.out.println("mergeddddddddddddddddddddddddd");
                        if(checkList(route)) {
                            allRoutes.add(route);
                            routeLengths.add(route.size());
                            routes += ("\nRoute via " + interchangeStation + " from " + startStation.get(i) + " to " + endStation.get(j) + ": " + route + " (Length: " + route.size() + ")" + "\n \n");
                            routes += printDirection(startStationLine);
                            routes += printDirection(endStationLine);
                        }

                    }
                }

                is_reversedl1 = false;
                is_reversedl2 = false;
                is_reversedl3 = false;
                tempEltafreaa1 = new ArrayList<>(Eltafreaa1);
                tempEltafreaa2 = new ArrayList<>(Eltafreaa2);
                int optimalRouteIndex = routeLengths.indexOf(Collections.min(routeLengths));

                optimalRoute = "Optimal route is :"+(optimalRouteIndex+1)+") "+allRoutes.get(optimalRouteIndex);
                if(optimalRoute != lastOptimalRoute) {
                    lastOptimalRoute = optimalRoute;
                    startLineOptimal = startStation.get(i);
                    endLineOptimal = endStationLine.get(j);
                }

                route = allRoutes.get(optimalRouteIndex);
                int count = route.size();
                int time = 2 * count;
                int ticket;
                if (count <= 9)
                    ticket = 6;
                else if (count <= 16)
                    ticket = 8;
                else if (count <= 23)
                    ticket = 12;
                else
                    ticket = 15;
                countStations = "*The number of stations: " + count + " station(s).";
                ticketPrice = "*The ticket price: " + ticket + " Egyptian pounds.";
                if (time >= 60) {
                    int hours = time / 60;
                    int minutes = time % 60;
                    timeToArrive = "*The trip will take: " + hours + " Hour(s), " + minutes + " Minute(s).";
                } else {
                    timeToArrive = "*The trip will take: " + time + " minutes.";
                }
            }
        }

    }
}
