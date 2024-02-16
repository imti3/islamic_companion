package com.imtiaj.islamiccompanion;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;


public class PrayerAlarmActivity extends AppCompatActivity {
    LinearLayout btnStop;
    TextView tvPrayerName;
    TextView tvPrayerTime;
    String prayerTime = "";
    String prayerName = "";
    MediaPlayer mediaPlayer; // Declare MediaPlayer as a class variable

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(2621440);
        }

        setContentView(R.layout.activity_prayer_alarm);
        this.prayerTime = getIntent().getStringExtra("prayerTime");
        this.prayerName = getIntent().getStringExtra("prayerName");
        this.btnStop = findViewById(R.id.btnStop);
        this.tvPrayerName = findViewById(R.id.tvPrayerName);
        this.tvPrayerTime = findViewById(R.id.tvPrayerTime);
        this.tvPrayerName.setText(this.prayerName);
        this.tvPrayerTime.setText(this.prayerTime);
        mediaPlayer = MediaPlayer.create(this, R.raw.adhan);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        this.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release(); // Release MediaPlayer
                    mediaPlayer = null; // Set MediaPlayer to null
                }
                PrayerAlarmActivity.this.finishAndRemoveTask();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // Release MediaPlayer
            mediaPlayer = null; // Set MediaPlayer to null
        }
    }
}
