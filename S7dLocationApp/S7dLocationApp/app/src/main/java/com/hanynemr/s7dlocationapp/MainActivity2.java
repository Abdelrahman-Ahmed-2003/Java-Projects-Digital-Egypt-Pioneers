package com.hanynemr.s7dlocationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mumayank.com.airlocationlibrary.AirLocation;

public class MainActivity2 extends AppCompatActivity implements AirLocation.Callback {

    AirLocation airLocation;
    float distance = 0.0F;
    float gas = 0.0f;
    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();


    private LinearLayout dynamicLayout;
    private ArrayList<EditText> editTextList;

    private ArrayList<TextView> textViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dynamicLayout = findViewById(R.id.dynamicLayout);
        editTextList = new ArrayList<>();
        textViewList = new ArrayList<>(); // Initialize the TextView list

        // Add initial EditText fields
        addEditText();
        addEditText();

        Button addButton = findViewById(R.id.buttonAddLocation);
        Button removeButton = findViewById(R.id.buttonRemoveLocation);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditText();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeEditText();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void show(View view) {
        distance = 0;
        gas = 0;
        if (locations.size() < 2) {
            Toast.makeText(this, "Please enter at least two valid places", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear previous TextViews from the layout and the list
        for (TextView tv : textViewList) {
            dynamicLayout.removeView(tv);
        }
        textViewList.clear(); // Clear the list

        // Create a new TextView for the segment summary
        TextView segmentTextView = new TextView(this);
        for (int i = 0; i < locations.size() - 1; ++i) {
            float segmentDistance = locations.get(i).distanceTo(locations.get(i + 1)) / 1000;
            distance += segmentDistance;

            // Append segment information
            segmentTextView.append("From " + names.get(i) + " to " + names.get(i + 1) + " is " + segmentDistance + " km\n");
        }
        gas = distance * 5;

        segmentTextView.append("Total distance is " + distance + " km\nThe car will consume " + gas + " L of gas in the trip\n");
        dynamicLayout.addView(segmentTextView);
        textViewList.add(segmentTextView); // Add to the list
    }

    public void recommended(View view) {
        if(textViewList.size() == 0)
        {
            Toast.makeText(this, "please press show first", Toast.LENGTH_SHORT).show();
            return;
        }

        for(int i = 1 ; i < textViewList.size();++i)
        {
            dynamicLayout.removeView(textViewList.get(i));
        }
        TextView temp = textViewList.get(0);
        textViewList = new ArrayList<>();
        textViewList.add(temp);


        ArrayList<Location> sortedLocations = new ArrayList<>();
        ArrayList<String> sortedNames = new ArrayList<>();

        Location currentLocation = locations.get(0);
        sortedLocations.add(currentLocation);
        sortedNames.add(names.get(0));

        locations.remove(0);
        names.remove(0);

        while (!locations.isEmpty()) {
            float minDistance = Float.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < locations.size(); i++) {
                float distance = currentLocation.distanceTo(locations.get(i));
                if (distance < minDistance) {
                    minDistance = distance;
                    minIndex = i;
                }
            }

            if (minIndex != -1) {
                currentLocation = locations.get(minIndex);
                sortedLocations.add(currentLocation);
                sortedNames.add(names.get(minIndex));

                locations.remove(minIndex);
                names.remove(minIndex);
            }
        }

        locations = new ArrayList<>(sortedLocations);
        names = new ArrayList<>(sortedNames);

        if (dynamicLayout.getChildCount() == 0) {
            show(view);
        }



        // Add recommendation header
        TextView recommendationHeader = new TextView(this);
        recommendationHeader.setText("\nMy recommendation route is:\n");
        dynamicLayout.addView(recommendationHeader);
        textViewList.add(recommendationHeader); // Add to the list

        distance = 0;
        gas = 0;
        for (int i = 0; i < locations.size() - 1; ++i) {
            float segmentDistance = locations.get(i).distanceTo(locations.get(i + 1)) / 1000;
            distance += segmentDistance;

            // Create and add a TextView for each recommended route segment
            TextView segmentTextView = new TextView(this);
            segmentTextView.setText("From " + names.get(i) + " to " + names.get(i + 1) + " is " + segmentDistance + " km");
            dynamicLayout.addView(segmentTextView);
            textViewList.add(segmentTextView); // Add to the list
        }
        gas = distance * 5;

        // Create and add a TextView for the total recommended summary
        TextView totalTextView = new TextView(this);
        totalTextView.setText("Total distance you will take is " + distance + " km\nThe car will consume " + gas + "L gas in the trip");
        dynamicLayout.addView(totalTextView);
        textViewList.add(totalTextView); // Add to the list
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(@NonNull ArrayList<Location> locations) {
        double latitude = locations.get(0).getLatitude();
        double longitude = locations.get(0).getLongitude();
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            editTextList.get(0).setText(addressList.get(0).getAddressLine(0));
        } catch (IOException e) {
            Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
        Toast.makeText(this, "error in getting location", Toast.LENGTH_SHORT).show();
    }



    public void my_loc(View view) {
        airLocation = new AirLocation(this, this, true, 0, "");
        airLocation.start();
    }

    private void addEditText() {

        Geocoder geocoder = new Geocoder(this);
        locations.clear(); // Clear the locations list before adding new ones
        names.clear();     // Clear the names list as well

        for(int i = 0; i < editTextList.size(); ++i){
            if(editTextList.get(i).toString().isEmpty())
            {
                continue;
            }
            try {
                List<Address> addressList2 = geocoder.getFromLocationName(editTextList.get(i).getText().toString(), 1);
                if (addressList2.isEmpty()) {
                    Toast.makeText(this, "Place not found: " + editTextList.get(i).getText().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Location loc = new Location("");
                loc.setLongitude(addressList2.get(0).getLongitude());
                loc.setLatitude(addressList2.get(0).getLatitude());
                locations.add(loc);
                names.add(editTextList.get(i).getText().toString());
                Toast.makeText(this, editTextList.get(i).getText().toString(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error in geocoding: " + editTextList.get(i).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }


        for (TextView tv : textViewList) {
            dynamicLayout.removeView(tv);
        }
        textViewList.clear();
        EditText editText = new EditText(this);
        editText.setHint("Enter address");
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        dynamicLayout.addView(editText);
        editTextList.add(editText);
    }

    private void removeEditText() {
        for (TextView tv : textViewList) {
            dynamicLayout.removeView(tv);
        }
        textViewList.clear();
        if(editTextList.size() == 2){
            Toast.makeText(this, "you need at least to input", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!editTextList.isEmpty()) {
            EditText editText = editTextList.remove(editTextList.size() - 1);
            dynamicLayout.removeView(editText);
        }
    }
}
