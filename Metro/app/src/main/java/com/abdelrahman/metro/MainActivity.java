package com.abdelrahman.metro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.WaveDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import mumayank.com.airlocationlibrary.AirLocation;

public class MainActivity extends AppCompatActivity implements WaveDetector.WaveListener, TextToSpeech.OnInitListener,ShakeDetector.ShakeListener,AirLocation.Callback{

    private LinearLayout container;
    MetroGraph met;
    Spinner firstSpinner, secondSpinner;
    String startStation = "select Station", endStation = "select Station";
    TextView result;
    SharedPreferences pref;
    ImageView soundI;
    boolean sound = true;
    TextToSpeech tts;
    boolean check = false;
    String lastStartStation = "";
    String lastEndStation = "";
    EditText editText;
    int tempNumOfRoutes = 0;

    AirLocation airLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);
        container = findViewById(R.id.container);
        met = new MetroGraph();
        firstSpinner = findViewById(R.id.firstSpinner);
        secondSpinner = findViewById(R.id.secondSpinner);
        result = findViewById(R.id.resultText);
        soundI = findViewById(R.id.soundI);
        tts = new TextToSpeech(this,this);
        editText = findViewById(R.id.editText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, met.allLines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);


        // Load preferences
       /* pref = getSharedPreferences("players", MODE_PRIVATE);
        startStation = pref.getString("startStation", "select Station");
        endStation = pref.getString("endStation", "select Station");
        metro.routes = pref.getString("routes", "");
        metro.optimalRoute = pref.getString("optimalRoute", "");
        //sound = pref.getBoolean("sound",true);*/
        if(!sound) {
            soundI.setImageResource(R.drawable.soundoff);
            tts.stop();
        }

        firstSpinner.setSelection(met.allLines.indexOf(startStation));
        secondSpinner.setSelection(met.allLines.indexOf(endStation));

        // Set spinner listeners
        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    startStation = firstSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    endStation = secondSpinner.getSelectedItem().toString();// Immediate update
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("startStation", startStation);
        editor.putString("endStation", endStation);
        editor.putBoolean("sound",sound);
        Toast.makeText(this, pref.getString("endStation", "not found"), Toast.LENGTH_SHORT).show();
        /*editor.putString("routes", metro.routes);
        String temp = metro.optimalRoute + "\n" + metro.countStations + "\n" + metro.ticketPrice + "\n" + metro.timeToArrive;
        editor.putString("optimalRoute", temp);*/
        editor.apply();
        super.onBackPressed();
    }

    public void shake(Spinner spinner){
        YoYo.with(Techniques.Bounce).duration(700).repeat(3).playOn(spinner);
        result.setText("");
    }

    @SuppressLint("SetTextI18n")
    public void subCalc() {
        if (firstSpinner.getSelectedItemPosition() == 0) {
            shake(firstSpinner);
            return;
        }

        if (secondSpinner.getSelectedItemPosition() == 0 || Objects.equals(startStation, endStation)) {
            shake(secondSpinner);
            return;
        }

        met.findAllRoutes(startStation,endStation);

            // Create a new instance of CustomButton
        container.removeAllViews();
        result.setText("");
        for (int i = 1; i <= met.allRoutes.size(); i++) {  // Example: Adding 10 buttons
            Button button = new Button(this);
            button.setText("route " + i);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            button.setPadding(16, 16, 16, 16);
            //button.setMargin(10, 10, 10, 10);  // Add margins if needed

            // Set OnClickListener for the button
            final int buttonIndex = i;  // To identify which button was clicked
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the button click
                    handleButtonClick(buttonIndex);
                }
            });

            // Add the button to the LinearLayout
            container.addView(button);
        }
    }


    private List<String> getLineIfOnSameLine(String station1, String station2) {
        List temp = new ArrayList<>();
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

    private void addDirection(String station1, String station2, List<String>directions,List<String> route)
    {
        boolean reversed = false;
        String temp = "";
        List<String>line = getLineIfOnSameLine(station1,station2);
        if(line == null)
            return;
        if(route.indexOf(station1) < route.indexOf(station2) && line.indexOf(station1) > line.indexOf(station2)) {
            Collections.reverse(line);
            reversed = true;
        }
        else if(route.indexOf(station1) > route.indexOf(station2) && line.indexOf(station1) < line.indexOf(station2)) {
            Collections.reverse(line);
            reversed = true;
        }
        if (directions.isEmpty())
            temp = line.get(line.size()-1);
        else if(!Objects.equals(directions.get(directions.size() - 1), line.get(line.size() - 1)))
            temp = line.get(line.size()-1);


        System.out.println(line);
        if(reversed && Objects.equals(temp, "Kit Kat")) {
            directions.add("Adly mansour");
        }
        else if(Objects.equals(temp, "Kit Kat") && !reversed) {
            directions.add("Cairo University or Rod Elfarag");
        }
        else {
            directions.add(temp);
        }
    }


    @SuppressLint("SetTextI18n")
    private void handleButtonClick(int buttonId) {
        tempNumOfRoutes = buttonId;
        tts.stop();
        boolean reversed = false;

        List<String> route = met.allRoutes.get(buttonId - 1);
        List<String> directions = new ArrayList<>();
        List<String> transferStations = new ArrayList<>();

        addDirection(route.get(0),route.get(1),directions, route);
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
                        if(nextLine.indexOf(currentStation) > nextLine.indexOf(nextStation)) {
                            Collections.reverse(nextLine);
                            reversed = true;

                        }
                        if(nextLine.get(nextLine.size() - 1) != "Kit Kat")
                            directions.add(nextLine.get(nextLine.size() - 1));  // Add the last station of the next line
                    }
                }
            }
        }

        addDirection(route.get(route.size()-1),route.get(route.size()-2),directions,route);

        StringBuilder summary = new StringBuilder();
        int j = 0;
        for(int i = 0 ; i < route.size();++i)
        {
            if(((met.Eltafreaa1.contains(startStation) && met.Eltafreaa2.contains(endStation)) ||(met.Eltafreaa2.contains(startStation) && met.Eltafreaa1.contains(endStation))) && buttonId == 1)
            {
                if(route.get(i) == "Kit Kat")
                {
                    summary.append("Kit Kat").append("\n\n"+"direction is"+directions.get(0)).append("\n");
                }
                summary.append(route.get(i)).append(", ");
            }

            else {
                if(transferStations.contains(route.get(i)))
                {
                    summary.append(route.get(i)).append("\n\n"+"direction is "+directions.get(j)).append("\n");
                    ++j;
                }
                summary.append(route.get(i)).append(", ");
            }
        }

        System.out.println(directions);
        if(directions.get(directions.size()-1) == "")
        {
            summary.append("\ndirections is" + directions.get(directions.size()-2)).append("\n\n");
        }
        else summary.append("\ndirections is" + directions.get(directions.size()-1)).append("\n\n");

        // Generate summary
        int count = route.size();
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
        summary.append("*The number of stations: ").append(count).append(" station(s).\n\n");
        summary.append("*The ticket price: ").append(ticket).append(" Egyptian pounds.\n\n");


        if (sound) {
            tts.speak(summary.toString(), TextToSpeech.QUEUE_ADD, null, null);
        }
        result.setText(summary.toString());
    }


    @SuppressLint("SetTextI18n")
    public void calc(View view) {
        check = true;
        lastEndStation = endStation;
        lastStartStation = startStation;
        subCalc();
    }

    public void flip(View view) {


        String temp = startStation;
        startStation = endStation;
        endStation = temp;
        container.removeAllViews();
        result.setText("");

        firstSpinner.setSelection(met.allLines.indexOf(startStation));
        secondSpinner.setSelection(met.allLines.indexOf(endStation));


        // Calculate after switching if needed
        if(firstSpinner.getSelectedItemPosition() == 0 && secondSpinner.getSelectedItemPosition() == 0)
        {
            shake(firstSpinner);
            shake(secondSpinner);
            Toast.makeText(this, "please select start and end stations", Toast.LENGTH_SHORT).show();
            return;
        }
        if(firstSpinner.getSelectedItemPosition() == 0)
        {
            shake(firstSpinner);
            Toast.makeText(this, "please select start station", Toast.LENGTH_SHORT).show();
            return;
        }
        if(secondSpinner.getSelectedItemPosition() == 0)
        {
            shake(secondSpinner);
            Toast.makeText(this, "please select end station", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!check || (lastStartStation != endStation || lastEndStation != startStation))
        {
            Toast.makeText(this, "I can't update route not calculated", Toast.LENGTH_SHORT).show();

        }
        else
        {
            lastEndStation = endStation;
            lastStartStation = startStation;
            subCalc();
            check = true;
        }
    }

    @Override
    public void onWave() {
        container.removeAllViews();
        tts.stop();
        result.setText("");
        startStation = "select Station";
        endStation = "select Station";
    }

    @Override
    public void onInit(int status)  {
        tts.setLanguage(new Locale("en"));
    }


    public void changeSound(View view) {

        if(sound){
            soundI.setImageResource(R.drawable.soundoff);
            tts.stop();
        }else{
            soundI.setImageResource(R.drawable.soundon);
        }
        sound = !sound;
    }

    @Override
    public void onShakeDetected() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onShakeStopped() {
        ++tempNumOfRoutes;
        if(tempNumOfRoutes > met.allRoutes.size()) {
            Toast.makeText(this, "sorry no routes founded", Toast.LENGTH_SHORT).show();
            tts.stop();
            return;
        }
        handleButtonClick(tempNumOfRoutes);
        /*result.setText(metro.routes+"\n" + metro.optimalRoute+"\n"+ metro.countStations + "\n" +
                metro.ticketPrice + "\n" + metro.timeToArrive);*/
    }

    public void map(View view) {
        if(firstSpinner.getSelectedItemPosition() == 0){
            Toast.makeText(this, "please choose start station", Toast.LENGTH_SHORT).show();
            return;
        }

//        @SuppressLint("DefaultLocale") String uri = String.format("google.navigation:q=%f,%f",metro.lat.get(metro.allLines.indexOf(startStation)) ,metro.lon.get(metro.allLines.indexOf(startStation)));
        @SuppressLint("DefaultLocale") String uri = String.format("geo:0,0?q=%f,%f",met.lat.get(met.allLines.indexOf(startStation))
                ,met.lon.get(met.allLines.indexOf(startStation)));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No map application available.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void nearest(View view) {
        airLocation = new AirLocation(this, this, true, 0, "");
        airLocation.start();
    }

    public int search(Location loc1) {
        if (met.lon == null || met.lat == null || met.lon.size() == 0 || met.lat.size() == 0) {
            Toast.makeText(this, "Location data is missing", Toast.LENGTH_SHORT).show();
            return -1;
        }

        float mindis = 10000000.0f;
        int minindex = -1;
        Location loc2 = new Location("");

        // Ensuring we don't go out of bounds

        for (int i = 1; i < met.allLines.size(); i++) {
            loc2.setLatitude(met.lat.get(i));
            loc2.setLongitude(met.lon.get(i));
            float distance = loc1.distanceTo(loc2) / 1000;
            if (distance < mindis) {
                minindex = i;
                mindis = distance;
            }
        }

        if (minindex == -1) {
            Toast.makeText(this, "No nearby stations found", Toast.LENGTH_SHORT).show();
        }

        return minindex;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(@NonNull ArrayList<Location> locations) {
        Location loc1 =new Location("");
        loc1.setLatitude(locations.get(0).getLatitude());
        loc1.setLongitude(locations.get(0).getLongitude());
        firstSpinner.setSelection(search(loc1));
        startStation = firstSpinner.getSelectedItem().toString();
        container.removeAllViews();
        result.setText("");
    }

    @Override
    public void onFailure(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
        Toast.makeText(this, "error in getting location", Toast.LENGTH_SHORT).show();
    }

    public void show(View view) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList2 = geocoder.getFromLocationName(editText.getText().toString(), 1);
            if (addressList2.isEmpty()) {
                Toast.makeText(this, "places not found", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "show done", Toast.LENGTH_SHORT).show();
            Location loc = new Location("");
            loc.setLongitude(addressList2.get(0).getLongitude());
            loc.setLatitude(addressList2.get(0).getLatitude());
            secondSpinner.setSelection(search(loc));
            endStation = secondSpinner.getSelectedItem().toString();
            container.removeAllViews();
            result.setText("");
        } catch (IOException e) {
            Toast.makeText(this, "error in show data", Toast.LENGTH_SHORT).show();
        }
    }
}
