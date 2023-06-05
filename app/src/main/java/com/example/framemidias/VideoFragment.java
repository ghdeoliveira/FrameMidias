package com.example.framemidias;

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

public class VideoFragment extends Fragment implements View.OnClickListener {

    MediaPlayer videoPlayer;

    public VideoFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_video, container, false);

        VideoView videoView = (VideoView)view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.mr_robot;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        TextView videoTime = (TextView)view.findViewById(R.id.videoTime);
        TextView videoDuration = (TextView)view.findViewById(R.id.videoDuration);

        SeekBar barVolume = (SeekBar)view.findViewById(R.id.BarVolume);
        SeekBar barProgress = (SeekBar)view.findViewById(R.id.BarProgress);

        videoPlayer = MediaPlayer.create(getContext(), R.raw.mr_robot);
        videoPlayer.setLooping(true);
        videoPlayer.seekTo(0);
        videoPlayer.setVolume(0.5f, 0.5f);

        // Controle de volume
        barVolume.setProgress(50);
        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                float volume = progress / 100f;
                videoPlayer.setVolume(volume,volume);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {}
             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Controle de progresso do vídeo
        String duration = millisecondsToString(videoPlayer.getDuration());
        videoDuration.setText(duration);

        barProgress.setMax(videoPlayer.getDuration());
        barProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser) {
                    videoPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

         new Thread(new Runnable() {
             @Override
             public void run() {
                 while (videoPlayer != null) {
                     if(videoPlayer.isPlaying()) {
                         try {
                             final double current = videoPlayer.getCurrentPosition();
                             final String elapsedTime = millisecondsToString((int) current);
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     videoTime.setText(elapsedTime);
                                     barProgress.setProgress((int) current);
                                 }
                             });
                             Thread.sleep(1000);
                         } catch (InterruptedException e) {}
                     }
                 }
             }
         }).start();


         return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnPlay) {
            videoPlayer.start();
        } else if (view.getId() == R.id.btnPause) {
            videoPlayer.pause();
        } else if (view.getId() == R.id.buttonStop) {
            if(videoPlayer.isPlaying()) {
                videoPlayer.stop();
                videoPlayer = MediaPlayer.create(getContext(), R.raw.mr_robot);
            }
        }
    }

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