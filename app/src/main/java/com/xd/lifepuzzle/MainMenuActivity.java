package com.xd.lifepuzzle;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString(LoginActivity.CURRENT_USER_KEY);

        Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
