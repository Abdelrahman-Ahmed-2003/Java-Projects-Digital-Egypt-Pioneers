package com.example.cairometro;

import android.os.Build;

import java.util.*;

public class MetroGraph {
    private final Map<String, List<String>> metroMap;
    List<List<String>> allRoutes;
    List<String>ts = new ArrayList<>(Arrays.asList("Elsadat","Elshohada","Jamal Abdulnasser","El-Ataba","Cairo university"));

    public MetroGraph() {
        metroMap = new HashMap<>();

        // Initialize metro lines
        addLine(firstLine);
        addLine(secondLine);
        addLine(thirdLine);
        addLine(Eltafreaa1);
        addLine(Eltafreaa2);

        // Add connections between lines
        connectStations("Elsadat", "Elsadat"); // Line 1 and Line 2
        connectStations("Elshohada", "Elshohada"); // Line 1 and Line 2
        connectStations("Jamal Abdulnasser", "Jamal Abdulnasser"); // Line 1 and Line 3
        connectStations("El-Ataba", "El-Ataba"); // Line 2 and Line 3
        connectStations("Cairo university", "Cairo university"); // Line 2 and Line 3

        connectStations("Kit Kat", "Kit Kat"); // Line 3 and Eltafreaa1
        connectStations("Kit Kat", "Kit Kat"); // Line 3 and Eltafreaa2
    }

    // Add all stations from a line to the graph
    private void addLine(List<String> line) {
        for (int i = 0; i < line.size() - 1; i++) {
            String station1 = line.get(i);
            String station2 = line.get(i + 1);
            addEdge(station1, station2);
            addEdge(station2, station1);
        }
    }

    // Add an edge between two stations
    private void addEdge(String station1, String station2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            metroMap.putIfAbsent(station1, new ArrayList<>());
        }
        metroMap.get(station1).add(station2);
    }

    // Connect two stations from different lines
    private void connectStations(String station1, String station2) {
        addEdge(station1, station2);
        addEdge(station2, station1);
    }

    // Record the transfer stations


    // Find all possible routes between two stations
    public void findAllRoutes(String start, String end) {
        List<List<String>> routes = new ArrayList<>();
        findRoutesDFS(start, end, new HashSet<>(), new ArrayList<>(), routes);
        allRoutes = new ArrayList<>(routes);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allRoutes.sort(Comparator.comparingInt(List::size));
        }
    }

    // DFS to find all routes
    private void findRoutesDFS(String current, String end, Set<String> visited, List<String> path, List<List<String>> routes) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            routes.add(new ArrayList<>(path));
        } else {
            //System.out.println(current);
            //System.out.println(Objects.requireNonNull(metroMap.getOrDefault(current, new ArrayList<>())));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                for (String neighbor : Objects.requireNonNull(metroMap.getOrDefault(current, new ArrayList<>()))) {
                    if (!visited.contains(neighbor)) {
                        findRoutesDFS(neighbor, end, visited, path, routes);
                    }
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
    }





    // Define your metro lines here as final lists
    public final List<String> firstLine = Arrays.asList("Helwan", "Ain.Helwan", "Helwan University",
            "Wadi hof", "Hadik Helwan", "Elmasara", "Tora Elasmant", "Kotcika",
            "Tora Elbalad", "Thakanat Elmaadi", "Elmaadi", "Hadik Elmaadi",
            "Dar Elsalam", "Elzahra", "Maregerges", "Elmalek Elsaleh",
            "Elsaida Zainab", "Saad Zaghlol", "Elsadat", "Jamal Abdulnasser",
            "Ahmed Oraby", "Elshohada", "Ghamra", "Eldemerdash", "Manshia.Elsadr",
            "Kobre.Eloba", "Hamamat.Elkoba", "Saria.Elkoba", "Hadaik.Elzaiton",
            "Helmit.Elzaiton", "Elmataria", "Ain.Shams", "Ezbet.Elnakhl", "Elmarg",
            "Elmarg.Elgededa");

    public final List<String> secondLine = Arrays.asList("El Munib", "saqiat makki", "dawahi algiza", "Algiza",
            "Faisal", "Cairo university","El bohoth", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah");

    public final List<String> thirdLine = Arrays.asList("Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero","safa hegaze", "Kit Kat");

    public final List<String> Eltafreaa1 = new ArrayList<>(Arrays.asList("Kit Kat","Elsodan", "embaba", "Albohee", "Alkomiaa Alarabia", "Altareek Aldaeree", "rod elfarag"));
    public final List<String> Eltafreaa2 = new ArrayList<>(Arrays.asList("Kit Kat","Eltawfiqia", "wadi Elneel", "game3t eldwal",
            "bolak Aldakror", "Cairo university"));
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
            "Faisal", "Cairo university","El bohoth", "Dokki", "El-opera", "Elsadat", "Muhammad Naguib",
            "El-Ataba", "Elshohada", "Masarah", "Rod El-farag", "Saint Teresa", "Al-Khalafawi",
            "El-mazalat", "faculty of Agriculture", "Shubra Al-Khaimah",

            "Adly Mansour", "Highstep", "Omar bin al-khattab",
            "Quba", "Hisham Barakat", "El-nozha", "Nadi El-Shams", "Alf Maskan", "Heliopolis",
            "Aaron", "A-Ahram", "Koliat El-panat", "Al-Estad", "ArdAl-Mared", "Abbasiya",
            "Abdo Pasha", "Al-Gesh", "Babal-sharya", "El-Ataba", "Jamal Abdulnasser",
            "Maspero","safa hegaze", "Kit Kat",

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
}
