package com.example.cairometro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cairometro.databinding.ActivityMainBinding;

import mumayank.com.airlocationlibrary.AirLocation;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    HomeFragment hf = new HomeFragment();
    MapFragment mf= new MapFragment();
    SearchFragment Searchf=new SearchFragment();
    settingsFragment settingsf = new settingsFragment();
    boolean soundSettings = true;
    SharedPreferences pref ;
    String fstation ="";
    String sstation="";
    String start = "",end = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = getSharedPreferences("Stations", MODE_PRIVATE);
        fstation = pref.getString("startStation", "");
        sstation = pref.getString("endStation", "");
        soundSettings = pref.getBoolean("Soundsettings",true);

        if(!fstation.isEmpty() && !sstation.isEmpty()) {
            receiveStations(fstation,sstation);

        }
        replaceFragment(hf);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(hf);
            } else if (item.getItemId() == R.id.map) {
                replaceFragment(mf);
            } else if (item.getItemId() == R.id.location) {
                replaceFragment(Searchf);
            } else if (item.getItemId() == R.id.settings) {
                replaceFragment(settingsf);
            }

            return true;
        });
    } // Added the missing closing brace here

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager(); // Fixed typo here
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // Fixed typo here
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.commit();
    }

    void receiveStations(String fS,String ss){
        Intent intent= new Intent(MainActivity.this,ResultActivity.class);
        intent.putExtra("string_key_1", fS);
        intent.putExtra("string_key_2", ss);
        intent.putExtra("Soundsettings", soundSettings);
        start=fS;
        end = ss;
        startActivity(intent);


    }
    void reciveSound(boolean sound){
        soundSettings = sound;
       // Toast.makeText(this, "recive sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("startStation", start);
        editor.putString("endStation", end);
        editor.putBoolean("Soundsettings",soundSettings);
        editor.apply();
        super.onBackPressed();
    }
}

