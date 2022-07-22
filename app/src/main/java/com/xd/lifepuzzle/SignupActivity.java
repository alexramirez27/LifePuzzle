package com.xd.lifepuzzle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    // Profile Picture and Change Button
    ImageView profilePicture;
    Button changeButton;
    private final int SELECT_PHOTO = 101;
    private final int CAPTURE_PHOTO = 102;
    final private int REQUEST_CODE_WRITE_STORAGE = 1;

    // Give Firebase Authentication
    private FirebaseAuth mAuth;

    // EditTexts
    private EditText editTextName, editTextAge, editTextEmail, editTextPassword, editTextPhoneNo;

    // RadioButtons
    private RadioButton radioButtonMale, radioButtonFemale;

    // Buttons
    private Button signUpBtn;

    // Spinner
    private Spinner stages;

    // boolHolder
    private int holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        editTextName = (EditText) findViewById(R.id.yourName);
        editTextAge = (EditText) findViewById(R.id.yourAge);
        editTextEmail = (EditText) findViewById(R.id.yourEmail);
        editTextPhoneNo = (EditText) findViewById(R.id.yourPhoneNumber);
        editTextPassword = (EditText) findViewById(R.id.yourPassword);

        radioButtonMale = (RadioButton) findViewById(R.id.radioGenderM);
        radioButtonMale.setOnClickListener(v -> {
            holder = 0;
        });

        radioButtonFemale = (RadioButton) findViewById(R.id.radioGenderF);
        radioButtonFemale.setOnClickListener(v -> {
            holder = 1;
        });

        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(v -> {
            if (v.getId() == R.id.signUpBtn) {
                signUpBtn(holder);
            }
        });

    }
    /**called when the user taps the "male" button*/
//    public void sendMale(View view)
//    {
//        // "male" button gets clicked and becomes disabled
//        findViewById(R.id.button).setEnabled((false));
//        ((Button)findViewById(R.id.button)).setText(R.string.chosen_button);
//    }
    /**called when the user taps the "female" button*/
//    public void sendFemale(View view)
//    {
//        // "female" button gets clicked and becomes disabled
//        findViewById(R.id.button4).setEnabled(false);
//        ((Button)findViewById(R.id.button4)).setText(R.string.chosen_button);
//    }
    /**called when the user taps the "Sign Up" button*/
//    public void sign_user_up(View v)
//    {
//        // sign user up and open "Main Menu"
//        v.setEnabled(false);
//        Intent i = new Intent(this, MainMenuActivity.class);
//        startActivity(i);
//    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.signUpBtn) {
//            signUpBtn();
//        }
    }

    /** Description: opens weblink to medical info page for users/caretakers unsure
     * of patient's dementia stage before signing up
     */
    public void stageFinder(View v)
    {
        Intent initateDiagnosis = new Intent(Intent.ACTION_VIEW, Uri.parse("https://alzheimer.ca/en/about-dementia/do-i-have-dementia/how-get-tested-dementia"));
        startActivity(initateDiagnosis);
    }

    private void signUpBtn(int holder){
        String fullName = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNo = editTextPhoneNo.getText().toString().trim(); // Maybe I should remove phoneNo
        String password = editTextPassword.getText().toString().trim();
        String gender = "Male";

        if ( holder == 0 ) {
            gender = "Male";
        }
        else if ( holder == 1) {
            gender = "Female";
        }

        if(fullName.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextName.setError("Email is required");
            editTextName.requestFocus();
            return;
        }

        if(phoneNo.isEmpty()){
            editTextName.setError("Phone number is required");
            editTextName.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        String finalGender = gender;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        User user = new User(fullName, age, email, phoneNo, finalGender);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                                    if(task1.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "User has been signed up successfully!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(SignupActivity.this, "Failed to sign up! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Failed to sign up! Try again!", Toast.LENGTH_LONG).show();
                    }
                });

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }






}
