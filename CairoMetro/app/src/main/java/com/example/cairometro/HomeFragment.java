package com.example.cairometro;

import static android.widget.Toast.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Objects;

import mumayank.com.airlocationlibrary.AirLocation;


public class HomeFragment extends Fragment implements View.OnClickListener, AirLocation.Callback {

    MetroGraph met;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }
Spinner spinner1;
Spinner spinner2;
Button button;
TextView nearstStationName;
ImageView location;
ImageView sw;
String startStation;
String endStation;
AirLocation airLocation;
    Location loc2;
    Location station = new Location("");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        met = new MetroGraph();
         spinner1= view.findViewById(R.id.spinner1);
         spinner2= view.findViewById(R.id.spinner2);
         button = view.findViewById(R.id.button);
         location = view.findViewById(R.id.locationicon);
        nearstStationName = view.findViewById(R.id.nearstStationName);
        sw = view.findViewById(R.id.switchicon);
         button.setOnClickListener(this);
         location.setOnClickListener(this);
         sw.setOnClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, met.allLines);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);


        spinner1.setSelection(met.allLines.indexOf(startStation));
        spinner2.setSelection(met.allLines.indexOf(endStation));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    startStation = spinner1.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    endStation = spinner2.getSelectedItem().toString();// Immediate update
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });

            airLocation = new AirLocation((Activity) requireContext(), this, true, 0, "");
            airLocation.start();

        }



    @Override
    public void onClick(View view) {
 MainActivity activity = (MainActivity) getActivity();

            if(view.getId() == R.id.button){

                if (spinner1.getSelectedItemPosition() == 0) {
                    shake(spinner1);
                    return;
                }

                if (spinner2.getSelectedItemPosition() == 0 || Objects.equals(startStation, endStation)) {
                    shake(spinner2);
                    return;
                }
                activity.receiveStations(startStation, endStation);
            }
            else if(view.getId() == R.id.locationicon){

                airLocation.start();
                String uri = String.format("geo:0,0?q="+station.getLatitude()+","+station.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            } else if (view.getId() == R.id.switchicon) {

                int spinner1Position = spinner1.getSelectedItemPosition();
                int spinner2Position = spinner2.getSelectedItemPosition();

                spinner1.setSelection(spinner2Position);
                spinner2.setSelection(spinner1Position);

            }
    }
    public void shake(Spinner spinner){
        YoYo.with(Techniques.Bounce).duration(700).repeat(3).playOn(spinner);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public int search(Location loc1)
    {
        float mindis = 1000000;int minindex = -1;

        for(int i = 1 ; i < met.lon.size();++i)
        {
            loc2 = new Location("");
            loc2.setLatitude(met.lat.get(i));
            loc2.setLongitude(met.lon.get(i));
            if(loc1.distanceTo(loc2)/1000 < mindis) {
                System.out.println("in ifffffffffffffffffffffffffffffffffffff");
                minindex = i;
                station = loc2;
                mindis = loc1.distanceTo(loc2)/1000;

            }
        }
        System.out.println("index isssssssssssssssssssssss ");
        System.out.println(minindex);
        return  minindex;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onFailure(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
        nearstStationName.setText("boooooooof");
    }

    @Override
    public void onSuccess(@NonNull ArrayList<Location> locations) {
        Location loc1 =new Location("");
        Location loc2 = new Location("");
        loc1.setLatitude(locations.get(0).getLatitude());
        loc1.setLongitude(locations.get(0).getLongitude());
        nearstStationName.setText(met.allLines.get(search(loc1)));
    }

}