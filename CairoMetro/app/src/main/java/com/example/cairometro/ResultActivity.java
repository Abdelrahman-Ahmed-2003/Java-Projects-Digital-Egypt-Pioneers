package com.example.cairometro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity implements ShakeDetector.ShakeListener, TextToSpeech.OnInitListener {

    String startStation = "";
    String endStation = "";
    boolean check = false;
    String lastStartStation = "";
    String lastEndStation = "";
    TextView Fromstation;
    TextView Tostation;
    ImageView imageView;
    TextView time;
    TextView stations;
    TextView line;
    TextView price;
    TextView optimal;
    TextView Alternatives;
    TextToSpeech ttss;
    Button tripButton;
    SharedPreferences pref ;
    boolean sound = true; // Default value
    MetroGraph met = new MetroGraph();
    int RouteIndex = 0;
    private boolean isTripActive = false;



    public static List<String> optimalroute = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        Fromstation = findViewById(R.id.Fromstation);
        Tostation = findViewById(R.id.Tostation);
        imageView = findViewById(R.id.imageView);
        time = findViewById(R.id.time);
        stations = findViewById(R.id.stations);
        line = findViewById(R.id.line);
        price = findViewById(R.id.price);
        optimal = findViewById(R.id.textView);
        Alternatives = findViewById(R.id.textView8);
        tripButton =findViewById(R.id.button2);

        // Initialize TextToSpeech
        ttss = new TextToSpeech(this, this);

        // Retrieve data from intent
        Intent intent = getIntent();
        startStation = intent.getStringExtra("string_key_1");
        endStation = intent.getStringExtra("string_key_2");
        sound = intent.getBooleanExtra("Soundsettings", true);
      //  if(!sound) Toast.makeText(this, "sound false", Toast.LENGTH_SHORT).show();// Retrieve sound value, default is true
        Log.d("ResultActivity", "Sound is: " + sound);

        // Set stations text
        Fromstation.setText(startStation);
        Tostation.setText(endStation);

        // Initialize Sensey for shake detection
        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);

        // Mark check as true
        check = true;
        lastEndStation = endStation;
        lastStartStation = startStation;

        // Calculate routes
        met.findAllRoutes(startStation, endStation);
        check = true;
        lastEndStation = endStation;
        lastStartStation = startStation;
        //subCalc();
       // handleButtonClick(RouteIndex);
    }

    @SuppressLint("SetTextI18n")
    public void subCalc() {
        met.findAllRoutes(startStation, endStation);
        //handleButtonClick(RouteIndex);
        if(RouteIndex == 0) optimalroute = met.allRoutes.get(0);
    }

    private List<String> getLineIfOnSameLine(String station1, String station2) {
        List<String> temp = new ArrayList<>();
        if (met.firstLine.contains(station1) && met.firstLine.contains(station2)) {
            temp.addAll(met.firstLine);
            return temp;
        } else if (met.secondLine.contains(station1) && met.secondLine.contains(station2)) {
            temp.addAll(met.secondLine);
            return temp;
        } else if (met.thirdLine.contains(station1) && met.thirdLine.contains(station2)) {
            temp.addAll(met.thirdLine);
            return temp;
        } else if (met.Eltafreaa1.contains(station1) && met.Eltafreaa1.contains(station2)) {
            temp.addAll(met.Eltafreaa1);
            return temp;
        } else if (met.Eltafreaa2.contains(station1) && met.Eltafreaa2.contains(station2)) {
            temp.addAll(met.Eltafreaa2);
            return temp;
        }
        return null; // Stations are not on the same line
    }

    private void addDirection(String station1, String station2, List<String> directions, List<String> route) {
        boolean reversed = false;
        String temp = "";
        List<String> line = getLineIfOnSameLine(station1, station2);
        if (line == null)
            return;
        if (route.indexOf(station1) < route.indexOf(station2) && line.indexOf(station1) > line.indexOf(station2)) {
            Collections.reverse(line);
            reversed = true;
        } else if (route.indexOf(station1) > route.indexOf(station2) && line.indexOf(station1) < line.indexOf(station2)) {
            Collections.reverse(line);
            reversed = true;
        }
        if (directions.isEmpty())
            temp = line.get(line.size() - 1);
        else if (!Objects.equals(directions.get(directions.size() - 1), line.get(line.size() - 1)))
            temp = line.get(line.size() - 1);

        if (reversed && Objects.equals(temp, "Kit Kat")) {
            directions.add("Adly mansour");
        } else if (Objects.equals(temp, "Kit Kat") && !reversed) {
            directions.add("Cairo University or Rod Elfarag");
        } else {
            directions.add(temp);
        }
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttss.setLanguage(Locale.US);
            subCalc();
            handleButtonClick(RouteIndex);
        } else {
            Toast.makeText(this, "TTS initialization failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleButtonClick(int buttonId) {
        boolean reversed = false;
        line.setText("Line: ");
        stations.setText("Stations: ");
        price.setText("Ticket Price: ");
        time.setText("Time: ");

        List<String> route = met.allRoutes.get(buttonId);
        List<String> directions = new ArrayList<>();
        List<String> transferStations = new ArrayList<>();

        addDirection(route.get(0), route.get(1), directions, route);
        for (int i = 1; i < route.size() - 1; i++) {
            String prevStation = route.get(i - 1);
            String currentStation = route.get(i);
            String nextStation = route.get(i + 1);

            if (met.ts.contains(currentStation)) {
                List<String> prevLine = getLineIfOnSameLine(prevStation, currentStation);
                List<String> nextLine = getLineIfOnSameLine(currentStation, nextStation);

                boolean isTransferStation = prevLine == null || nextLine == null || !prevLine.equals(nextLine);

                if (isTransferStation) {
                    transferStations.add(currentStation);

                    if (nextLine != null) {
                        if (nextLine.indexOf(currentStation) > nextLine.indexOf(nextStation)) {
                            Collections.reverse(nextLine);
                            reversed = true;
                        }
                        if (!Objects.equals(nextLine.get(nextLine.size() - 1), "Kit Kat"))
                            directions.add(nextLine.get(nextLine.size() - 1));// Add the last station of the next line
                    }
                }
            }
        }

        addDirection(route.get(route.size() - 1), route.get(route.size() - 2), directions, route);

        StringBuilder summary = new StringBuilder();
        int j = 0;
        for (int i = 0; i < route.size(); ++i) {
            if (((met.Eltafreaa1.contains(startStation) && met.Eltafreaa2.contains(endStation)) || (met.Eltafreaa2.contains(startStation) && met.Eltafreaa1.contains(endStation))) && buttonId == 1) {
                if (Objects.equals(route.get(i), "Kit Kat")) {
                    summary.append("Kit Kat\n\n");
                    line.append(directions.get(0));
                }
                summary.append(route.get(i)).append("\n ");
            } else {
                if (transferStations.contains(route.get(i))) {
                    summary.append(route.get(i)+"\n\n");
                    line.append(directions.get(j));
                    ++j;
                }
                summary.append(route.get(i)).append("\n ");
            }
        }

        if (Objects.equals(directions.get(directions.size() - 1), "")) {
            line.append(" " + directions.get(directions.size() - 2));
        } else {
            line.append(" " + directions.get(directions.size() - 1));
        }

        // Generate summary
        int count = route.size();
        int times = 2*count;
        int ticket;
        if (count <= 9) {
            ticket = 8;
        } else if (count <= 16) {
            ticket = 10;
        } else if (count <= 23) {
            ticket = 14;
        } else {
            ticket = 17;
        }
        String ti = "";
        if (times >= 60) {
            int hours = times / 60;
            int minutes = times % 60;
             ti = hours + " Hour, " + minutes + " Minute";
        } else {
            ti= times + " Minute";
        }

        String tic = ticket + " L.E";
        price.append(tic);
        String cou = count + " Station";
        stations.append(cou);
        time.append(ti);
        optimal.setText(summary.toString());

        // Use TextToSpeech if sound is enabled
        System.out.println(sound);
        System.out.println(summary);
        if (sound) {
            ttss.speak(summary, TextToSpeech.QUEUE_ADD, null, null);
        }
    }



    @Override
    public void onBackPressed() {
        ttss.shutdown();
        super.onBackPressed();
    }

    public void goback(View view) {
        onBackPressed();
    }

    @Override
    public void onShakeDetected() {

    }

    @Override
    public void onShakeStopped() {
        ++RouteIndex;
        ttss.stop();

        if(RouteIndex <= met.allRoutes.size()){
            Alternatives.setVisibility(View.VISIBLE);
            handleButtonClick(RouteIndex);
        }else{
            ttss.stop();
            Toast.makeText(this, "No More Routes", Toast.LENGTH_SHORT).show();
        }
    }


    public void onTripButtonClick(View view) {
        if (isTripActive) {
            onCancelTripClick(); // Stop the trip
        } else {
            onStartTripClick(); // Start the trip
        }

        isTripActive = !isTripActive; // Toggle the state
        updateButtonState(); // Update the button UI
    }


    public void onStartTripClick() {
        // Start WorkManager task for periodic notifications
        Constraints con = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        PeriodicWorkRequest tripNotificationRequest = new PeriodicWorkRequest.Builder(Mywork.class, 5, TimeUnit.SECONDS)
                .setConstraints(con)
                .build();

        WorkManager.getInstance(this).enqueue(tripNotificationRequest);

        Toast.makeText(this, "Trip started! Notifications will be sent every 2 minutes.", Toast.LENGTH_SHORT).show();
    }

    public void onCancelTripClick() {
        // Cancel any ongoing WorkManager tasks related to trips
        WorkManager.getInstance(this).cancelAllWork();
        Toast.makeText(this, "Trip canceled.", Toast.LENGTH_SHORT).show();
    }

    private void updateButtonState() {
        if (isTripActive) {
            tripButton.setText("Stop Trip");
            tripButton.setBackgroundColor(Color.RED); // Set button background to red when the trip is active
        } else {
            tripButton.setText("Start Trip");
            tripButton.setBackgroundColor(Color.BLUE); // Set button background to green when the trip is inactive
        }
    }
    @Override
    protected void onDestroy() {
        Sensey.getInstance().stop();
        super.onDestroy();
    }
}