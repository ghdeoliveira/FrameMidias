package com.example.framemidias;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Log;

public class VideoFragment extends Fragment implements View.OnClickListener {

    PlayerView exoPlayerView;
    String videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4";

    public VideoFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_video, container, false);

        exoPlayerView = (PlayerView)view.findViewById(R.id.exoPlayerView);
        ExoPlayer exoPlayer = new ExoPlayer.Builder(getContext()).build();
        exoPlayerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoURL);
        exoPlayer.addMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

        AudioManager audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        SeekBar barVolume = (SeekBar)view.findViewById(R.id.BarVolume);

        // Controle de volume
        barVolume.setProgress(50);
        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                float volume = progress / 100f;
                exoPlayer.setVolume(volume);
             }
             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {}
             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {}
        });

         return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnPlay) {
            exoPlayerView.onResume();
        } else if (view.getId() == R.id.btnPause) {
            exoPlayerView.onPause();
        } else if (view.getId() == R.id.btnRePlay) {
            //exoPlayerView.on
        }

    }
}