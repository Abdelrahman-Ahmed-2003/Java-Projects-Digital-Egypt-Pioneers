package com.example.cairometro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;


public class MapFragment extends Fragment {
    PhotoView photoView;



    public MapFragment() {super(R.layout.fragment_map);}
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoView =(PhotoView) view.findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.background);
    }


}