package com.example.cairometro;

import static android.content.Context.MODE_PRIVATE;
import static com.example.cairometro.MyApp.getSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class settingsFragment extends Fragment implements View.OnClickListener {
    MainActivity activity = (MainActivity) getActivity();
    ImageView soundI;
  boolean sound = true;
    SharedPreferences pref;

    public settingsFragment() {super(R.layout.fragment_settings);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // tts = new TextToSpeech(this,this);
       soundI= view.findViewById(R.id.imageView3);
        pref = getActivity().getSharedPreferences("Stations", Context.MODE_PRIVATE);
        sound = pref.getBoolean("Soundsettings",true);
        if(!sound) {
            soundI.setImageResource(R.drawable.nosound);
            //activity.reciveSound(sound);
        }
        soundI.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        if(sound){
            soundI.setImageResource(R.drawable.nosound);
            // tts.stop();
        }else{
            soundI.setImageResource(R.drawable.soundon);
        }
        sound = !sound;
        activity.reciveSound(sound);
    }
}