package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PuzzleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        Intent i = getIntent();
        String puzzleID = i.getStringExtra("puzzleID");

        ((TextView)findViewById(R.id.textView9)).setText(puzzleID);
    }
}
