package com.abdelrahman.metro;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.WaveDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements WaveDetector.WaveListener, TextToSpeech.OnInitListener, ShakeDetector.ShakeListener {
    Main metro = new Main();
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);
        firstSpinner = findViewById(R.id.firstSpinner);
        secondSpinner = findViewById(R.id.secondSpinner);
        result = findViewById(R.id.resultText);
        soundI = findViewById(R.id.soundI);
        tts = new TextToSpeech(this,this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, metro.allLines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);

        // Load preferences
        pref = getSharedPreferences("players", MODE_PRIVATE);
        startStation = pref.getString("startStation", "select Station");
        endStation = pref.getString("endStation", "select Station");
        metro.routes = pref.getString("routes", "");
        metro.optimalRoute = pref.getString("optimalRoute", "");
        sound = pref.getBoolean("sound",true);
        if(!sound)
            soundI.setImageResource(R.drawable.soundoff);

        firstSpinner.setSelection(metro.allLines.indexOf(startStation));
        secondSpinner.setSelection(metro.allLines.indexOf(endStation));
        result.setText(metro.routes + metro.optimalRoute);

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
        editor.putString("routes", metro.routes);
        String temp = metro.optimalRoute + "\n" + metro.countStations + "\n" + metro.ticketPrice + "\n" + metro.timeToArrive;
        editor.putString("optimalRoute", temp);
        editor.apply();
        super.onBackPressed();
    }

    public void shake(Spinner spinner){
        YoYo.with(Techniques.Bounce).duration(700).repeat(3).playOn(spinner);
        result.setText("");
        metro.defult();
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

        metro.calc(startStation, endStation);

        if(metro.endLineOptimal == metro.startLineOptimal)
        {
            if(sound)
                tts.speak("you are in the same line",TextToSpeech.QUEUE_ADD,null,null) ;
        }
        else {
            for (String interchang : metro.interchangeStations) {
                if(metro.optimalRoute.contains(interchang)) {
                    if (sound)
                        tts.speak(interchang, TextToSpeech.QUEUE_ADD, null, null);
                }
            }
        }
        result.setText(metro.optimalRoute+"\n"+ metro.countStations + "\n" +
                metro.ticketPrice + "\n" + metro.timeToArrive);
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

        firstSpinner.setSelection(metro.allLines.indexOf(startStation));
        secondSpinner.setSelection(metro.allLines.indexOf(endStation));


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
            Toast.makeText(this, "please select start station then calculate route", Toast.LENGTH_SHORT).show();
            return;
        }
        if(secondSpinner.getSelectedItemPosition() == 0)
        {
            shake(secondSpinner);
            Toast.makeText(this, "please select end station then calculate route", Toast.LENGTH_SHORT).show();
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
        metro.defult();
        result.setText("");
        startStation = "select Station";
        endStation = "select Station";
    }

    @Override
    public void onInit(int status)  {
        tts.setLanguage(new Locale("ar"));
    }


    public void changeSound(View view) {

        if(sound){
            soundI.setImageResource(R.drawable.soundoff);
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
        result.setText(metro.routes+"\n" + metro.optimalRoute+"\n"+ metro.countStations + "\n" +
                metro.ticketPrice + "\n" + metro.timeToArrive);
    }
}
