package com.xd.lifepuzzle;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    // Profile Picture and Change Button
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

    // For Dialog
    private Button changeBtn;
    private ImageView profilePicture;

    // Firebase Storage Objects
    //private FirebaseStorage storage;
    private StorageReference mStorageRef;

    public Uri mImageUri;

    // Upload
    private static final int PICK_IMAGE_REQUEST = 1;

    // More Variables
    private DatabaseReference mDatabaseRef;

    private ProgressBar mProgressBar;

    private File imgFileName;

    private StorageTask mUploadTask;

    private TextView mTextViewShowUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //show "Hide password" feature using eye icon
        ImageView showHidePassword = findViewById(R.id.show_hide_password);
        showHidePassword.setImageResource(R.drawable.ic_hide_pwd);
        showHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    // if password is visible then hide it
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // icon is changed
                    showHidePassword.setImageResource(R.drawable.ic_hide_pwd);
                }
                else
                {
                    // if password is hidden then display it
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // icon is changed
                    showHidePassword.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        // Firebase Storage
        // storage = FirebaseStorage.getInstance();


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"); // This means we will save it in a folder called uploads in our storage
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();

        editTextName = (EditText) findViewById(R.id.yourName);
        editTextAge = (EditText) findViewById(R.id.yourAge);
        editTextEmail = (EditText) findViewById(R.id.yourEmail);
        editTextPhoneNo = (EditText) findViewById(R.id.yourPhoneNumber);
        editTextPassword = (EditText) findViewById(R.id.yourPassword);
        mTextViewShowUploads = (TextView) findViewById(R.id.text_view_show_uploads);

        radioButtonMale = (RadioButton) findViewById(R.id.radioGenderM);
        radioButtonMale.setOnClickListener(v -> {
            holder = 0;
        });

        radioButtonFemale = (RadioButton) findViewById(R.id.radioGenderF);
        radioButtonFemale.setOnClickListener(v -> {
            holder = 1;
        });

        // Dialog below
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        changeBtn = (Button) findViewById(R.id.changeBtn);

        // Call Change Picture Button
        //changeBtn.setOnClickListener(v -> selectImage(SignupActivity.this));

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });


        // Important buttons
        changeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser(SignupActivity.this);
            }
        });



        signUpBtn = (Button) findViewById(R.id.signUpBtn);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(SignupActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ( v.getId() == R.id.signUpBtn ) {
                        signUpBtn(holder);
                    }
                }

            }
        });

    }


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

    // Get file extension from the image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void signUpBtn(int holder){

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

                            Toast.makeText(SignupActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            // retrieve the download URL and send it to the DB
                            // reference: https://stackoverflow.com/a/55503926

                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            //createNewPost(imageUrl);
                                            Upload upload = new Upload(editTextName.getText().toString().trim(), imageUrl);
                                            String uploadId = mDatabaseRef.push().getKey();
                                            mDatabaseRef.child(uploadId).setValue(upload);
                                        }
                                    });
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

//        How to push new user to database
//        User user = new User("test 5", "18", "bob@gmail.com", "1234567", "Male");

        String key = myRef.push().getKey();
        User user = new User(fullName, age, email, phoneNo, finalGender, key);
        myRef.child(key).setValue(user);



        Bundle bundle = new Bundle();
        bundle.putString(LoginActivity.CURRENT_USER_KEY, key);

        //uploadPicture(); // Maybe move this to when submit is clicked

        Information.userID = key;

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /** called when the medicalAvatar is clicked */
    public void assistDiagnosis(View v)
    {
        /** opens webpage to AlzheimerSociety(default_location:Canada) to allow caregiver/early-stage
         * patients learn about the different stages and the procedure for getting diagnosed for
         * a particular stage
         */
        Intent diagnosis = new Intent(Intent.ACTION_VIEW, Uri.parse("https://alzheimer.ca/en/about-dementia/do-i-have-dementia/how-get-tested-dementia"));
        startActivity(diagnosis);
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
            profilePicture.setImageURI(mImageUri);
        }

    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }


}
