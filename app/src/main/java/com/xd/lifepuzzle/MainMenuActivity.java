package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Description: puzzle selection page where the user can select a member that they want to
 *              pick a puzzle from (choose a puzzle of my pet simba from
 *              his 5 photos on the next page
 */


public class MainMenuActivity extends AppCompatActivity {

//    public static String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        try{
//            Intent intent = getIntent();
        if (Information.userID != null){
            Log.v("TAG", "Information class");
            Log.v("TAG", Information.userID);
        }
//            currentUserID = intent.getStringExtra(LoginActivity.CURRENT_USER_KEY);
//            Log.v("TAG", currentUserID);
//        } catch (Exception e){
//
//        }



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
