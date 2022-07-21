package com.xd.lifepuzzle;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar seekbarVol;
    AudioManager audioManager;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        mediaPlayer = MediaPlayer.create(this,R.raw.lofistudy);
        aSwitch = findViewById(R.id.switch_audio);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(getBaseContext(), "ON", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }
                if (isChecked == false) {
                    Toast.makeText(getBaseContext(), "OFF", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                }
            }
        });
    }
    /** called when the "Main Menu" button is pressed*/
    public void editGame(View view)
    {
        /**Brings user back to the Main Menu to allow selecting a different puzzle*/
        Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
    /**called when the user clicks the SIGNOUT button */
    public void sign_user_out(View view)
    {
        Intent intent = new Intent(SettingsActivity.this, FirstInstallActivity.class);
        startActivity(intent);
    }
}

