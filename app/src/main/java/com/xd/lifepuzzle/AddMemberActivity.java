package com.xd.lifepuzzle;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class AddMemberActivity extends AppCompatActivity implements View.OnClickListener {
//    lateinit var cameraResult : ActivityResultLauncher<Intent>

    //public ActivityResultLauncher<Intent> cameraResult;
    private EditText editTextName;
    private EditText editTextRelationship;

    // Give Firebase Authentication
    private FirebaseAuth mAuth;

    // Firebase Objects
    //private FirebaseDatabase database;
    //private DatabaseReference mDatabaseRef;
    // private DatabaseReference myRef;

    DatabaseReference myRef;
    //private FirebaseStorage storage;
    private StorageReference mStorageRef;

    private File imgFileName;

    // Change Picture Objects
    private ImageView memberPhoto;
    private Button memberChangeBtn;
    private Button member_save_button;
    public Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private Button member_delete_button;

    private TextView mTextViewShowUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);



        // Maybe this should go above the try
        mStorageRef = FirebaseStorage.getInstance().getReference("MemberPictures"); // This means we will save it in a folder called uploads in our storage
        // mDatabaseRef = FirebaseDatabase.getInstance().getReference("memberUploads");

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_member);

        mAuth = FirebaseAuth.getInstance();
//        Resources res = getResources();
//        cameraResult = res.getStringArray(R.array.camera_options_list);

//        try{
//            Bundle bundle = getIntent().getExtras();
//            String currentMember = bundle.getString(EditMemberActivity.CURRENT_MEMBER_KEY);
//            Toast.makeText(AddMemberActivity.this, currentMember, Toast.LENGTH_SHORT).show();
//        } catch (Exception e){
//            Toast.makeText(AddMemberActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//        }

        editTextName = findViewById(R.id.member_name);
        editTextRelationship = findViewById(R.id.member_relationship);

        // Change Picture, Dialog below
        memberPhoto = (ImageView) findViewById(R.id.memberPhoto);
        memberChangeBtn = (Button) findViewById(R.id.memberChangeBtn);
        member_save_button = (Button) findViewById(R.id.member_save_button);


        // Important buttons
        memberChangeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser(AddMemberActivity.this);
            }
        });
//
        member_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddMemberActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ( v.getId() == R.id.member_save_button ) {
                        saveBtn();
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        // Text
    }

    /**
     * Description: opens dialog box to take a photo or get one from file explorer
     */
//    public void onAddMemberPhotoClicked(View view){
//
//        openFileChooser(AddMemberActivity.this);
//        //Toast.makeText(AddMemberActivity.this, "No onAddMemberPhotoClicked", Toast.LENGTH_SHORT).show();
//        //Intent intent = new Intent(this, MainMenuActivity.class);
//        //startActivity(intent);
//
//    }

    /**
     * Description: gets an audio file from file explorer
     */
//    public void onAddAudioMessageClicked(View view){
//        Toast.makeText(AddMemberActivity.this, "No onAddAudioMessageClicked", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
//    }
//
//    /**
//     * Description: gets a video file from file explorer
//     */
//    public void onAddVideoClicked(View view){
//        Toast.makeText(AddMemberActivity.this, "No onAddVideoClicked", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
//    }

    /**
     * Description: saves member to database and opens main menu
     */
//    public void onSaveMemberClicked(View view){
//        Toast.makeText(AddMemberActivity.this, "onSaveMemberClicked", Toast.LENGTH_SHORT).show();
//        //saveBtn();
//        database = FirebaseDatabase.getInstance();
//        mStorageRef = FirebaseStorage.getInstance().getReference("memberUploads");
//        myRef = database.getReference("Members");
//
//        // creates unique ID
//        String key = myRef.push().getKey();
//
//        // creates new user
//        Member member = new Member(name.getText().toString(), relationship.getText().toString(), key);
////        User user = new User("John Smith", "64", "jsmith@gmail.com", "Male", "123124213", member);
//        // sends member to firebase
//
////         Creates Member which can be references on Main Menu
////        myRef.child(name.getText().toString());
//        // Creates Unique ID per puzzle which can be used on puzzle selection
//        if (MainMenuActivity.currentUserID != null){
//            Log.v("TAG", MainMenuActivity.currentUserID);
//            myRef.child(MainMenuActivity.currentUserID).child(name.getText().toString()).child(key).setValue(member);
////            myRef.setValue(MainMenuActivity.currentUserID);
////            myRef.child(MainMenuActivity.currentUserID).setValue(name.getText().toString());
////            myRef.child(MainMenuActivity.currentUserID).child(key).setValue(MainMenuActivity.currentUserID);
//
//        }
//
//        // saveBtn();
//
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
//    }

    /**
     * Description: deletes member from database
     * Post-condition: member is deleted from database
     */
//    public void onDeleteMemberClicked(View view){
//        Toast.makeText(AddMemberActivity.this, "onDeleteMemberClicked", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
//    }

    // Get file extension from the image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // Copy and Pasted from SignupActivity
    private void saveBtn(){

//        Toast.makeText(AddMemberActivity.this, "Save Member clicked", Toast.LENGTH_SHORT).show();
//
//        database = FirebaseDatabase.getInstance();
//        mStorageRef = FirebaseStorage.getInstance().getReference("memberUploads");
//        myRef = database.getReference("Members");

        // Creates Unique ID
        //String key = myRef.push().getKey();

        // Creates new user
        //Member member = new Member(name.getText().toString(), relationship.getText().toString(), key);




        String memberName = editTextName.getText().toString().trim();
        String memberRelationship = editTextRelationship.getText().toString().trim();


        if(memberName.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(memberRelationship.isEmpty()){
            editTextRelationship.setError("Relationship is required");
            editTextRelationship.requestFocus();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Members");

        Log.v("TAG", "member here");

        if ( mImageUri != null ) {
            // Toast.makeText(SignupActivity.this, "The mImageUri is not null", Toast.LENGTH_LONG).show();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 50);

                            Toast.makeText(AddMemberActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String tempImageUrl = uri.toString();

                                            String key = myRef.push().getKey();
                                            Member member = new Member(memberName, memberRelationship, tempImageUrl, key);
                                            myRef.child(key).setValue(member);

                                            // TODO: get user key
                                            Log.v("TAG", "new user here");
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString(LoginActivity.CURRENT_USER_KEY, key);

                                            //uploadPicture(); // Maybe move this to when submit is clicked

                                            Intent intent = new Intent(AddMemberActivity.this, MainMenuActivity.class);
//                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        }
                                    });
                                }
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddMemberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

//        String memberName = editTextName.getText().toString().trim();
//        String memberRelationship = editTextRelationship.getText().toString().trim();
//
//
//        if(memberName.isEmpty()){
//            editTextName.setError("Name is required");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if(memberRelationship.isEmpty()){
//            editTextRelationship.setError("Relationship is required");
//            editTextRelationship.requestFocus();
//            return;
//        }
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Members");
//
////        How to push new user to database
//        User user = new User("test 5", "18", "bob@gmail.com", "1234567", "Male");
//
//        String key = myRef.push().getKey();
//        Member member = new Member(memberName, memberRelationship, key);
//        //Member member = new Member(memberName, memberRelationship, key);
//        myRef.child(key).setValue(member);
//
////         Creates Unique ID per puzzle which can be used on puzzle selection
//        if ( MainMenuActivity.currentUserID != null ) {
//            myRef.child(MainMenuActivity.currentUserID).child(name.getText().toString()).child(key).setValue(member);
//        }


//        // TODO: get user key
//        Bundle bundle = new Bundle();
//        bundle.putString(LoginActivity.CURRENT_USER_KEY, key);
////
////        //uploadPicture(); // Maybe move this to when submit is clicked
////
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    private void openFileChooser(Context context) {
        final CharSequence[] options = { "Select from Gallery","Cancel" };

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick Profile Picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

//                if (options[item].equals("Open Camera")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    //takePicture.setType("image/*");
//                    startActivityForResult(takePicture, 0);

                if (options[item].equals("Select from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ) {
            mImageUri = data.getData();
            memberPhoto.setImageURI(mImageUri);
        }

    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

}
