package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: puzzle selection page where the user can select a member that they want to
 *              pick a puzzle from (choose a puzzle of my pet simba from
 *              his 5 photos on the next page
 */


public class MainMenuActivity extends AppCompatActivity {

//    public static String currentUserID;

    GridView gridView;
    ArrayList<ArrayList<Integer>> completionTimes;
    List<String> names;
    List<String> relationships;
    List<String> uniqueIDs;
    int[] images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        try{
//            Intent intent = getIntent();
        if (Information.userID != null){
            Log.v("TAG", "Information class");
            Log.v("TAG", Information.userID);
        }
//            currentUserID = intent.getStringExtra(LoginActivity.CURRENT_USER_KEY);
//            Log.v("TAG", currentUserID);
//        } catch (Exception e){
//
//        }

        getMembers();
        //getGridData();
        //setGridView();
        //gridViewOnClickListener();

//  im not sure what the below code does..

//        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        loginViewModel.getNames().observe(this,names -> {
//            profileAdapter = new ProfileAdapter(MainMenuActivity2.this, names, images);
//            gridView.setAdapter(profileAdapter);
//        });


    }

    private void getMembers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Members").child(Information.userID);
        names = new ArrayList<String>();
        relationships = new ArrayList<String>();
        uniqueIDs = new ArrayList<String>();
        completionTimes = new ArrayList<ArrayList<Integer>>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                relationships.clear();
                uniqueIDs.clear();
                completionTimes.clear();

                for (DataSnapshot childrenSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot grandChildrenSnapshot : childrenSnapshot.getChildren()) {

                        //fetches names from the database
                        try {
                            names.add(grandChildrenSnapshot.child("name").getValue().toString());
                        } catch(Exception e){
                            Log.v("DB","Cannot retrieve name from DB!");
                        }

                        //fetches relationships from the database
                        try {
                            relationships.add(grandChildrenSnapshot.child("relationship").getValue().toString());
                        } catch(Exception e){
                            Log.v("DB", "Cannot retrieve relationship from DB!");
                        }

                        //fetches uniqueID from the database
                        try{
                            uniqueIDs.add(grandChildrenSnapshot.child("uniqueID").getValue().toString());
                        } catch(Exception e){
                            Log.v("DB", "Cannot retrieve uniqueID from DB!");
                        }

                        //fetches completionTimes from the database. Stores completion times in an arrayList of arrayList
                        ArrayList<Integer> completionTimesCurrentMember = new ArrayList<Integer>();
                        try{
                            for (DataSnapshot completionTimesSnapshot : grandChildrenSnapshot.child("completionTimes").getChildren()) {
                                completionTimesCurrentMember.add(Integer.parseInt(completionTimesSnapshot.getValue().toString()));
                                Log.v("DB completion times", completionTimesSnapshot.getValue().toString());
                            }
                        } catch(Exception e){ //if completion times do not exist, then
                            Log.v("DB", "Cannot retrieve completion times from DB!");
                        }
                        completionTimes.add(completionTimesCurrentMember);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                getGridData();
                setGridView();
                gridViewOnClickListener();
            }
        }, 1000);

    }



    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    private void getGridData(){

//                {"Jill Smith", "Christy Kelley", "Alexander Walker", "Eric Vazquez",
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

        gridView = findViewById(R.id.mainMenuGridView);
        ProfileAdapter profileAdapter = new ProfileAdapter(MainMenuActivity.this, names, images);
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
                bundle.putString(Information.userID, uniqueIDs.get(position));

                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent.putExtras(bundle);
//
                startActivity(intent);
            }

        });
    }


    /**
     * Description: opens settings page
     */
    public void launchSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);

    }

    /**
     * Description: opens caregiver page
     */
    public void launchCaregiver(View v){
//        Bundle bundle = new Bundle();
//        bundle.putString(CURRENT_USER_KEY, Information.userID);


        Intent i = new Intent(this, CaregiverActivity.class);
        startActivity(i);

        Intent intent = new Intent(getApplicationContext(), CaregiverActivity.class);
//        intent.putExtras(bundle);

        startActivity(intent);

    }

    /**
     * Description: opens puzzle selection page passing in the member
     *              so that puzzles from the member can be shown
     */
    public void launchPuzzle(View v){
        Intent i = new Intent(this, SelectGameActivity.class);
        i.putExtra("puzzleID", "Stacey");
        startActivity(i);
    }


}
