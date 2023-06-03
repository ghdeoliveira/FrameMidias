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
    MediaPlayer musicPlayer;

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_audio, container, false);

        TextView musicTime = (TextView)view.findViewById(R.id.musicTime);
        TextView musicDuration = (TextView)view.findViewById(R.id.musicDuration);

        SeekBar seekBarVolume = (SeekBar)view.findViewById(R.id.seekBarVolume);
        SeekBar seekBarProgress = (SeekBar)view.findViewById(R.id.seekBarProgress);

        ImageButton buttonPlay = (ImageButton)view.findViewById(R.id.buttonPlay);
        ImageButton buttonPause = (ImageButton)view.findViewById(R.id.buttonPause);
        ImageButton buttonStop = (ImageButton)view.findViewById(R.id.buttonStop);

        musicPlayer = MediaPlayer.create(getContext(), R.raw.bob);
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        String duration = millisecondsToString(musicPlayer.getDuration());
        musicDuration.setText(duration);

        buttonPlay.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        // Configura barra de execução faixa
        seekBarProgress.setMax(musicPlayer.getDuration());
        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser) {
                    musicPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Configura controle de volume
        seekBarVolume.setProgress(50);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                float volume = progress / 100f;
                musicPlayer.setVolume(volume,volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicPlayer != null) {
                    if(musicPlayer.isPlaying()) {
                        try {
                            final double current = musicPlayer.getCurrentPosition();
                            final String elapsedTime = millisecondsToString((int) current);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    musicTime.setText(elapsedTime);
                                    seekBarProgress.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                    }
                }
            }
        }).start();

        return view;

    } // end onCreateView

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
                musicPlayer.stop();
                musicPlayer.setLooping(true);
            }
        } else {
            musicPlayer.stop();
            musicPlayer.reset();
        }
    }

    // Manipula getDuration para String
    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes+":";
        if(seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }
}