package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    /**called when the user taps the "male" button*/
    public void sendMale(View view)
    {
        // "male" button gets clicked and becomes disabled
        findViewById(R.id.button).setEnabled((false));
        ((Button)findViewById(R.id.button)).setText(R.string.chosen_button);
    }
    /**called when the user taps the "female" button*/
    public void sendFemale(View view)
    {
        // "female" button gets clicked and becomes disabled
        findViewById(R.id.button4).setEnabled(false);
        ((Button)findViewById(R.id.button4)).setText(R.string.chosen_button);
    }
    /**called when the user taps the "Sign Up" button*/
    public void sign_user_up(View v)
    {
        // sign user up and open "Main Menu"
        v.setEnabled(false);
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }
}
