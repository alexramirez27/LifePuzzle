package com.xd.lifepuzzle;

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

    public void sendMale(View view)
    {
        // do something when "male" is called
        view.setEnabled(false);
        Button b = (Button) view;
        String s = getString(R.string.male);
        b.setText(s);
    }
    /**called when the user taps the "female" button*/
    public void sendFemale(View view)
    {
        // do something when "male" is called
        view.setEnabled(false);
        Button b = (Button) view;
        String s = getString(R.string.female);
        b.setText(s);
    }
    /**called when the user taps the "Sign Up" button*/
    public void sign_user_up(View view)
    {
        // do something when "male" is called
        view.setEnabled(false);
        Button b = (Button) view;
        String s = getString(R.string.button_signUp);
        b.setText(s);
    }
}
