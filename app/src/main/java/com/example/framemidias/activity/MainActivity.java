package com.example.framemidias.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.framemidias.R;
import com.example.framemidias.fragments.audioFragment;
import com.example.framemidias.fragments.videoFragment;

public class MainActivity extends AppCompatActivity {

    private audioFragment audioFragment;
    private videoFragment videoFragment;
    private Button botaoAudio;
    private Button botaoVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoAudio = findViewById(R.id.buttonAudio);
        botaoVideo = findViewById(R.id.buttonVideo);

        audioFragment = new audioFragment();
        videoFragment = new videoFragment();

        //Configurar objeto para fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameConteudo, audioFragment);
        transaction.commit();

        botaoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, audioFragment);
                transaction.commit();
            }
        });

        botaoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, videoFragment);
                transaction.commit();
            }
        });
    }
}