package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

//import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Allows the user to select a user to login to from our database
 * Post-condition: database accessed to retrieve user
 */
public class LoginActivity extends AppCompatActivity {

    public static String CURRENT_USER_KEY = "current user";

    private FirebaseAuth mAuth;

    private String userID = "0";

    private LoginViewModel loginViewModel;
    private ProfileAdapter profileAdapter;

    GridView gridView;
    List<String> names;
    int[] images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        mAuth = FirebaseAuth.getInstance();


        getNames();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getNames().observe(this, names -> {
            profileAdapter = new ProfileAdapter(LoginActivity.this, names, images);
            gridView.setAdapter(profileAdapter);

        });


    }

    private boolean getNames(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

//        How to push new user to database
//        User user = new User("test 5", "18", "bob@gmail.com", "1234567", "Male");

//        String key = myRef.push().getKey();
//        myRef.child(key).setValue(user);

        names = new ArrayList<>();

//        How to query all data from firebase using a for loop.
//        Should be able to query
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    names.add(ds.child("name").getValue(String.class));
                    String temp = ds.child("name").getValue(String.class);
                    // gets list of all names
                    Log.v("TAG", temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // TODO: Delays program, bad solution instead need to do live Data for grid view
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGridData();
                setGridView();
                gridViewOnClickListener();
            }
        }, 1000);

        return true;
    }



    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    private void getGridData(){


//        names = new String[] {"Jill Smith", "Christy Kelley", "Alexander Walker", "Eric Vazquez",
//                              "John Hopkins", "Becky Alvarez", "Michelle Moore", "Dean Thomas",
//                              "Jorge Schmitt", "Cody Harrison", "David Perkins", "Daniel Jordan",
//                              "Cynthia Lane", "Jeremiah Lamb", "Rhonda Jones", "Thomas Barnes",
//                              "Faith Rose", "Philip Mitchell", "Angel Moreno", "Jacob Jenkins",
//                              "Matthew Jones","Jo Davis" , "Monica Barker" , "Amy Collins" };

        images = new int[]{R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test,
                R.drawable.avatar_test, R.drawable.avatar_test, R.drawable.avatar_test};
    }

    /**
     * Description: sets gridview elements with name and photo of each profile
     * Post-condition: gridview is set to xml element and adapter is set
     */
    private void setGridView(){
        // TODO: use mutable live data for grid view as takes a while to get all users


        gridView = findViewById(R.id.loginGridView);
        profileAdapter = new ProfileAdapter(LoginActivity.this, names, images);
        gridView.setAdapter(profileAdapter);

    }

    /**
     * Description: opens main menu on grid item selected
     * Post-condition: current user changed to selected item
     */
    private void gridViewOnClickListener(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CURRENT_USER_KEY, names.get(position));

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });
    }

}
