package com.xd.lifepuzzle;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMemberActivity extends AppCompatActivity {
//    lateinit var cameraResult : ActivityResultLauncher<Intent>
    public ActivityResultLauncher<Intent> cameraResult;
    private EditText name;
    private EditText relationship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.member_name);
        relationship = findViewById(R.id.member_relationship);

//        Resources res = getResources();
//        cameraResult = res.getStringArray(R.array.camera_options_list);

        try{
            Bundle bundle = getIntent().getExtras();
            String currentMember = bundle.getString(EditMemberActivity.CURRENT_MEMBER_KEY);
            Toast.makeText(AddMemberActivity.this, currentMember, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(AddMemberActivity.this, "No Member", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Description: opens dialog box to take a photo or get one from file explorer
     */
    public void onAddMemberPhotoClicked(View view){
        Toast.makeText(AddMemberActivity.this, "No onAddMemberPhotoClicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

//        Dialog dialogBoxes = DialogBoxes()
//        val bundle = Bundle()
//        bundle.putString(DialogBoxes.WHICH_KEY, DialogBoxes.CAMERA_KEY)
//        bundle.putString(DialogBoxes.CAMERA_KEY, "Pick Profile Picture")
//        dialogBoxes.arguments = bundle
//
//        dialogBoxes.show(supportFragmentManager, "tag")
//        dialogBoxes.getArguments()


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
//        cameraResult.launch(intent);

//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
//        cameraResult.launch(intent)
    }

    /**
     * Description: gets an audio file from file explorer
     */
    public void onAddAudioMessageClicked(View view){
        Toast.makeText(AddMemberActivity.this, "No onAddAudioMessageClicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    /**
     * Description: gets a video file from file explorer
     */
    public void onAddVideoClicked(View view){
        Toast.makeText(AddMemberActivity.this, "No onAddVideoClicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    /**
     * Description: saves member to database and opens main menu
     */
    public void onSaveMemberClicked(View view){
        Toast.makeText(AddMemberActivity.this, "onSaveMemberClicked", Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Members");

        // creates unique ID
        String key = myRef.push().getKey();

        // creates new user
        Member member = new Member(name.getText().toString(), relationship.getText().toString(), key);
//        User user = new User("John Smith", "64", "jsmith@gmail.com", "Male", "123124213", member);
        // sends member to firebase

//         Creates Member which can be references on Main Menu
//        myRef.child(name.getText().toString());
        // Creates Unique ID per puzzle which can be used on puzzle selection
        if (MainMenuActivity.currentUserID != null){
            Log.v("TAG", MainMenuActivity.currentUserID);
            myRef.child(MainMenuActivity.currentUserID).child(name.getText().toString()).child(key).setValue(member);
//            myRef.setValue(MainMenuActivity.currentUserID);
//            myRef.child(MainMenuActivity.currentUserID).setValue(name.getText().toString());
//            myRef.child(MainMenuActivity.currentUserID).child(key).setValue(MainMenuActivity.currentUserID);

        }


        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    /**
     * Description: deletes member from database
     * Post-condition: member is deleted from database
     */
    public void onDeleteMemberClicked(View view){
        Toast.makeText(AddMemberActivity.this, "onDeleteMemberClicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
