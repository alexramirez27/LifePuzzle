package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Description: puzzle selection page where the user can select a member that they want to
 *              pick a puzzle from (choose a puzzle of my pet simba from
 *              his 5 photos on the next page
 */
public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    /**
     * Description: opens settings page
     */
    public void launchSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    /**
     * Description: opens caregiver page
     */
    public void launchCaregiver(View v){
        Intent i = new Intent(this, CaregiverActivity.class);
        startActivity(i);
    }

    /**
     * Description: opens puzzle selection page passing in the member
     *              so that puzzles from the member can be shown
     */
    public void launchPuzzle(View v){
        Intent i = new Intent(this, SelectGameActivity.class);
        i.putExtra("puzzleID", "Stacey");
        startActivity(i);
    }


}
