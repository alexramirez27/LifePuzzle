package com.xd.lifepuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar seekbarVol;
    AudioManager audioManager;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        seekbarVol = findViewById(R.id.seekbar_Volume);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // get the Max volume
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // get current Volume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekbarVol.setMax(maxVolume);
        seekbarVol.setProgress(currentVolume);
        seekbarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // save switch state in shared preferences
        aSwitch = findViewById(R.id.switch_audio);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    // when switch is checked upon clicking
                    aSwitch.setChecked(true);
                    Intent intent = new Intent(SettingsActivity.this, BackgroundsoundService.class);
                    startService(intent);
                }
                else
                {
                    // when switch is unchecked upon clicking
                    aSwitch.setChecked(false);
                    stopService(new Intent(SettingsActivity.this, BackgroundsoundService.class));
                }
            }
        });
    }

    /** called when the "Main Menu" button is pressed*/
    public void editGame(View view)
    {
        /** Brings user back to the Main Menu to allow selecting a different puzzle */
        Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
    /**called when the user clicks the SIGN OUT button */
    public void sign_user_out(View view)
    {
        /** signs out user and brings user back to the MainActivity; music stops playing */
        stopService(new Intent(SettingsActivity.this, BackgroundsoundService.class));
        Intent intent = new Intent(SettingsActivity.this, FirstInstallActivity.class);
        startActivity(intent);
    }
}

