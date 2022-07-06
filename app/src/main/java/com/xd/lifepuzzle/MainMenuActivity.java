package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }
    public void launchSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void launchCaregiver(View v){
        Intent i = new Intent(this, CaregiverActivity.class);
        startActivity(i);
    }

    public void launchPuzzle(View v){
        Intent i = new Intent(this, PuzzleActivity.class);
        i.putExtra("puzzleID", "Stacey");
        startActivity(i);
    }



}
