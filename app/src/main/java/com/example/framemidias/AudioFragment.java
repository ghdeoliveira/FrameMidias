package com.example.framemidias;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class AudioFragment extends Fragment implements View.OnClickListener {

    TextView nomeCantor, nomeMusica, musicTime, musicDuration;
    SeekBar	seekBarVolume, seekBarProgress;
    MediaPlayer musicPlayer;

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_audio, container, false);

        musicTime = getActivity().findViewById(R.id.musicTime);
        musicDuration = getActivity().findViewById(R.id.musicDuration);
        seekBarVolume = getActivity().findViewById(R.id.seekBarVolume);
        seekBarProgress = getActivity().findViewById(R.id.seekBarProgress);
        ImageButton buttonPlay = (ImageButton)view.findViewById(R.id.buttonPlay);
        ImageButton buttonPause = (ImageButton)view.findViewById(R.id.buttonPause);
        ImageButton buttonStop = (ImageButton)view.findViewById(R.id.buttonStop);

        musicPlayer = MediaPlayer.create(getContext(), R.raw.bob);
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        buttonPlay.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonPlay) {
            if(musicPlayer.isPlaying()) {
                // is playing
                musicPlayer.pause();
            } else {
                // on pause
                musicPlayer.start();
            }
        } else if (view.getId() == R.id.buttonPause) {
            if(musicPlayer.isPlaying()) {
                // is playing
                musicPlayer.pause();
            } else {
                // on pause
                musicPlayer.start();
            }
        } else if (view.getId() == R.id.buttonStop) {
            if(musicPlayer.isPlaying()) {
                // is playing
                musicPlayer.stop();
                musicPlayer.setLooping(true);
            }
        }
    }

}