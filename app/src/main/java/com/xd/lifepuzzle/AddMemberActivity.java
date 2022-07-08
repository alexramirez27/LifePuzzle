package com.xd.lifepuzzle;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        try{
            Bundle bundle = getIntent().getExtras();
            String currentMember = bundle.getString(EditMemberActivity.CURRENT_MEMBER_KEY);
            Toast.makeText(AddMemberActivity.this, currentMember, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(AddMemberActivity.this, "No Member", Toast.LENGTH_SHORT).show();
        }



    }
}
