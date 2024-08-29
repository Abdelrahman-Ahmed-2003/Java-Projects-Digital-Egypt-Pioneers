package com.example.cairometro;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


public class SearchFragment extends Fragment implements View.OnClickListener {

EditText input ;
TextView text ;
Button show;
String location = "";
Location loc2,station;
MetroGraph met = new MetroGraph();
    public SearchFragment() {
        super(R.layout.fragment_search);    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          input = view.findViewById(R.id.editTextText);
         text =view.findViewById(R.id.textView5);
         show= view.findViewById(R.id.button3);
         show.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<Address> addressList2 = geocoder.getFromLocationName(input.getText().toString(), 1);
            if (addressList2.isEmpty()) {
                Toast.makeText(getActivity(), "places not found", Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(this, "show done", Toast.LENGTH_SHORT).show();
            Location loc = new Location("");
            loc.setLongitude(addressList2.get(0).getLongitude());
            loc.setLatitude(addressList2.get(0).getLatitude());
            int index = search(loc);
            text.setVisibility(View.VISIBLE);
            text.setText(met.allLines.get(index)+" Station");

        } catch (IOException e) {
            Toast.makeText(getActivity(), "error in show data", Toast.LENGTH_SHORT).show();
        }
    }
}