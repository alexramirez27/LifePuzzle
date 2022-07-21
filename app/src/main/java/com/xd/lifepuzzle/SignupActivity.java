package com.xd.lifepuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Description: allows the user to sign up a new user into our database
 * Post-condition: new user added to our database
 */
public class SignupActivity extends AppCompatActivity {
    private Button maleButton;
    private Button femaleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        maleButton = findViewById(R.id.button1);
        femaleButton = findViewById(R.id.button4);
    }

    /**
     * Description: called when the user taps the "male" button
     */
    public void sendMale(View v)
    {
        // on clicking, button changes to "selected" and turns light blue leaving "FEMALE" button greyed out
        // on clicking, Alert is send out to display what the user's current choice is
        maleButton.setBackgroundColor(getResources().getColor(R.color.light_blue));
        femaleButton.setBackgroundColor(Color.GRAY);
    }
    /**
     * Description: called when the user taps the "female" button
     * */
    public void sendFemale(View v)
    {
        // on clicking, button changes to "selected" and turns light blue leaving "MALE" button greyed out
        // on clicking, Alert is send out to display what the user's current choice is
        maleButton.setBackgroundColor(Color.GRAY);
        femaleButton.setBackgroundColor(getResources().getColor(R.color.light_blue));
    }
    /**
     * Description: called when the user taps the "Sign Up" button
     * */
    public void sign_user_up(View v)
    {
        // upon clicking, button
        Intent intent = new Intent(SignupActivity.this, MainMenuActivity.class);
        startActivity(intent);
        Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
    }

    /** Description: opens weblink to medical info page for users/caretakers unsure
     * of patient's dementia stage before signing up
     */
    public void stageFinder(View v)
    {
        Intent initateDiagnosis = new Intent(Intent.ACTION_VIEW, Uri.parse("https://alzheimer.ca/en/about-dementia/do-i-have-dementia/how-get-tested-dementia"));
        startActivity(initateDiagnosis);
    }

    /**
     * Description: opens dialog box to take a photo or get one from file explorer
     */
    public void selectPhoto(View v) {
        Toast.makeText(SignupActivity.this, "No photo", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
