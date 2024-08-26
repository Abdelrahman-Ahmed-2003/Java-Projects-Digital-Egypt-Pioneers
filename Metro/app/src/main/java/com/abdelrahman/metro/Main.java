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
            "Elmarg.Elgededa",

            "El Munib", "saqiat makki", "dawahi algiza", "Algiza",
            "Faisal", "Cairo university","eeeeeeeel bohoth", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah",

            "Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero","ssssssafa hegaze", "Kit Kat",

            "Elsodan", "embaba", "Albohee", "Alkomiaa Alarabia", "Altareek Aldaeree", "rod elfarag",
            "Eltawfiqia", "wadi Elneel", "game3t eldwal",
            "bolak Aldakror", "Cairo university"));

    public ArrayList<Double>lat = new ArrayList<Double>(Arrays.asList(0.0,
            29.849015381224415,29.862631472590042,29.869467782737207,29.879095429759047,
            29.897148653234044,29.90611535683043,29.925973277469172,29.93627010729958,
            29.946772762905,29.953311510226982,29.96030965642587,29.970145515382583,
            29.98207113172986,29.995488823655844,30.006101613331214,30.017713907800335,
            30.02929186435961,30.037040193414303,30.044142155160316,30.053507766038233,
            30.05669215037982,30.06107553176591,30.069032110095588,30.077320767387008,
            30.08199056844837,30.08720188393137,30.091240084369026,30.097651440164604,
            30.105893107125183,30.113259883427126,30.121342216227948,30.131029109775373,
            30.139322269046072,30.152085340762625,30.163650706288614,


            29.981183939889316,29.995505402986055,30.005661702760996,30.010673793135386,
            30.01738429950686,30.02602151584257,30.035793134131467,30.03845162708406,
            30.041954305569508,30.044142824215893,30.045324734496536,30.05235148862691,
            30.061073999990544,30.070899999988814,30.080591194143178,30.08796315952489,
            30.097892159815466,30.10419984206895,30.113697908266342,30.122439877347954,


            30.14655402017395,30.143929094509037,30.140402701590055,30.13483410865743,
            30.13083229571723,30.12798764329346,30.125488616583024,30.119015386287508,
            30.108424976244912,30.101376131690362,30.0917211016018,30.08404168713946,
            30.072921457334566,30.073852106388973,30.07209639121528,30.064806230835355,
            30.061765228586484,30.05415872597679,30.052356762822345,30.053520618985903,
            30.05573369979621,30.06229147008327,30.066565620108182,30.07008543713197,
            30.07584571303229,30.08214356978791,30.0932380203818,30.096423455419924,
            30.101919231197364,
            30.06536458595308,30.058693598075354,30.05039279185314,30.03776189701506,
            30.025289318200464
    ));

    public ArrayList<Double>lon = new ArrayList<Double>(Arrays.asList(0.0,
            31.334233385133572,31.324865060670582,31.320060885955723,31.313578320861943,
            31.303958834914614,31.299510122835635,31.28754062854294,31.28181741890389,
            31.272975896882517,31.262953852396993,31.257642152141756,31.25060807585285,
            31.242174098564682,31.231176700482692,31.229628358696914,31.231208627731583,
            31.235424265191543,31.238362925688303,31.234423332779286,31.238731627649017,
            31.2420515073844,31.246047923733812,31.26461278372308,31.27779695386638,
            31.28753461281688,31.294102809863652,31.298908429357944,31.304561262831033,
            31.310479766151037,31.313961972965817,31.313721696758904,31.319088381359606,
            31.32441998339855,31.335684508824155,31.33836252644858,


            31.212320232680373,31.208655896719854,31.208113376328342,31.207082432124746,
            31.203926986903703,31.20115571788421,31.20016455551561,31.212242934583987,
            31.224978389397823,31.234419030537968,31.244163992493185,31.246802225213887,
            31.24604732853661,31.245098372428107,31.24540693438731,31.245491668818435,
            31.24539213679561,31.245635107364038,31.248664915143,31.244537667552958,


            31.421300002984808,31.40469141125897,31.394341584118997,31.38374276553671,
            31.372935556110246,31.360171656021475,31.348877135039302,31.34018436786482,
            31.33830388386664,31.332970336945476,31.32631457945336,31.32901705549961,
            31.317103116701308,31.301487162750437,31.283375003953907,31.27474672519988,
            31.266881211643216,31.25587348800692,31.246800127132705,31.23872984220802,
            31.23210153042315,31.22328163418657,31.213024640896506,31.204734031762364,
            31.2074680383469,31.210528921949923,31.209011320944725,31.19957801767406,
            31.184422870369296,
            31.202750372095124,31.20099031973684,31.19892389742699,31.19554930395524,
            31.201321612071286
          ));


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
            "Faisal", "Cairo university","eeeeeeeel bohoth", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah"));

    public  ArrayList<String> thirdLine = new ArrayList<>(Arrays.asList("Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero","ssssssafa hegaze", "Kit Kat"));
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
        if(line.contains("Adly Mansour")) {
            line = checkEltafreaa(start, end,line);
            System.out.println("line in condition in simpel route "+line);
        }
        System.out.println("line out condition in simpel route "+line);

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
            tempInterchangeStations.add("Jamal Abdulnasser");
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
        System.out.println("route one is " + route1);
        List<String> route2;
        if(interchangeStation == "Jamal Abdulnasser" && !endLine.contains("Adly Mansour")) {
            tempThirdLine.addAll(Eltafreaa2);
            route2 = getSimpleRoute(interchangeStation, "Cairo university", tempThirdLine);
            List<String>temp = getSimpleRoute("Cairo university", end,secondLine);
            route2.addAll(temp.subList(1,temp.size()));
        }
        else
            route2 = getSimpleRoute(interchangeStation, end, endLine);
        System.out.println("route two is "+route2);
        List<String> mergedRoute = new ArrayList<>(route1);
        mergedRoute.addAll(route2.subList(1, route2.size()));


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

    public ArrayList<String> checkEltafreaa(String start , String end,ArrayList<String>line){
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
            return tempThirdLine;
        }  else if (Eltafreaa2.contains(start) || Eltafreaa2.contains(end)) {
            tempThirdLine.addAll(Eltafreaa2);
            THIRD_LINE_FORWARD = "El Munib Direction";
            return tempThirdLine;
        }else if (Eltafreaa1.contains(start) || Eltafreaa1.contains(end)) {
            tempThirdLine.addAll(Eltafreaa1);
            THIRD_LINE_FORWARD = "rod elfarag Direction";
            return tempThirdLine;
        }
        return line;
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
                    endLineOptimal = endStation.get(j);
                }

                route = allRoutes.get(optimalRouteIndex);
                int count = route.size();
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
            }
        }

    }
}
