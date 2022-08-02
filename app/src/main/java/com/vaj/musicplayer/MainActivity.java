package com.vaj.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
ImageView playBtn,pauseBtn;
TextView lapsedTimeTv,totalTimeTv;
SeekBar seekBar;
String fileURL="https://pwdown.com/113558/Raataan%20Lambiyan%20-%20Shershaah.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //mediaPlayer=MediaPlayer.create(this,R.raw.song);


        mediaPlayer=new MediaPlayer();

        mediaPlayer.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());

        try {
            mediaPlayer.setDataSource(fileURL);
            mediaPlayer.prepare();


        } catch (IOException e) {
            e.printStackTrace();
        }

        playBtn=findViewById(R.id.playBtn);
        pauseBtn=findViewById(R.id.pauseBtn);
        lapsedTimeTv=findViewById(R.id.lapsedTimeTv);
        totalTimeTv=findViewById(R.id.totalTimeTv);
        seekBar=findViewById(R.id.seekBar);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                pauseBtn.setVisibility(View.GONE);
                playBtn.setVisibility(View.VISIBLE);
            }
        });

        totalTimeTv.setText(mediaPlayer.getDuration()/1000/60+":"+mediaPlayer.getDuration()/1000%(60));
        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer.isPlaying())
                    lapsedTimeTv.setText(mediaPlayer.getCurrentPosition()/1000/60+":"+mediaPlayer.getCurrentPosition()/1000%(60));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());


                //.setProgress(mMediaPlayer.getCurrentPosition());
            }
        },0,1000);







       //



    }
}