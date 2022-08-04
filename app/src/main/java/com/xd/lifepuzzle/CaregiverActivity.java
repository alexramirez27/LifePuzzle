package com.xd.lifepuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Description: allows the caregiver to see data about the time it takes the user to complete
 *              each puzzle they do
 * Post-condition: new user added to our database
 */
public class CaregiverActivity extends AppCompatActivity {

    ArrayList barArrayList;
    boolean displayingAverageGraph = false;
    TextView graphTitle;
    int displayMemberNumber = 0;
    String currentUserID;

    //variables storing user information
    String patientAge;
    String patientEmail;
    String patientGender;
    String patientName;
    String patientPhoneNo;

    //ArrayLists for the Members
    ArrayList<ArrayList<Integer>> completionTimes;
    List<String> names;
    List<String> relationships;
    List<String> uniqueIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        currentUserID = Information.userID;

        try{
            getMemberData();
        } catch (Exception e){

        }

    }

    /**
     * Description: Creates Bar Chart of the time it takes the user to complete the puzzle
     */
    private void createBarChart(){

        BarChart barChart = findViewById(R.id.barchart);
        BarDataSet barDataSet;

        if(displayingAverageGraph == true){ //calculates the average of each of the past 7 games
            createAverageGraph();
            barDataSet = new BarDataSet(barArrayList, "Average Time for Completion in Seconds");
            graphTitle.setText("Average Completion Times for " + names.get(displayMemberNumber));
        }
        else{ //get completion times of the past 7 games
            createNormalGraph(displayMemberNumber);
            barDataSet = new BarDataSet(barArrayList, "Time for Completion in Seconds");
            graphTitle.setText("Completion Times for: " + names.get(displayMemberNumber));
        }

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //color bar data set
        barDataSet.setColor(R.color.dark_blue);

        //text color
        barDataSet.setValueTextColor(Color.BLACK);

        //setting text size
        barDataSet.setValueTextSize(20f);

        //disable the description
        barChart.getDescription().setEnabled(false);

        //redraws the new graph
        barChart.invalidate();

        //disables touch interaction
        barChart.setTouchEnabled(false);

        //disables the x axis labels
        barChart.getXAxis().setDrawLabels(false);

        //disables the x axis lines
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);

    }

    /**
     * Description: This method activates when the "Change Graph" button is clicked.
     * Changes the boolean value and redraws the graph
     */

    public void switchBarChart(View v){

        if(displayingAverageGraph == true){
            displayingAverageGraph = false;
        }
        else if(displayingAverageGraph == false){
            displayingAverageGraph = true;
        }

        createBarChart();
    }

    /**
     * Description: This method creates the average graph. Max 7 bars and each bar calculates the avg of at most 7 games
     * If there are less than 7 games played, it will take the avg of that and plot one bar
     * If there are less than or equal to 49 games played, it will take the avg of groups of 7 games.
     * For example: 17 games played: first bar represents avg of first 7 games,
     * second bar represents avg of next 7 games, third bar represents avg of next 3 games.
     * If there are more than 49 games played, then it will take the avg of groups of 7 games, but the most recent ones.
     * For example, 51 games played, it will ignore the first 2 games. Then use the rest of the 49 data points to construct graph.
     */

    private void createAverageGraph(){

        // calculates the average time for 7 games

        barArrayList = new ArrayList();

        ArrayList<Integer> memberCompletionTimesList = completionTimes.get(displayMemberNumber);
        Integer size = memberCompletionTimesList.size();

        int numberOfFullBars = 0;
        int numberOfTimesInPartialBar = 0;
        int startingIndex = 0;

        if(size < 49){

            if(size < 7){
                numberOfTimesInPartialBar = size;
            }

            else if(size % 7 == 0){
                numberOfFullBars = size/7;
            }

            else{
                numberOfFullBars = (size/7);
                numberOfTimesInPartialBar = size - (numberOfFullBars * 7);
            }
        }

        else{ //get the 49 most recent completionTimes
            numberOfFullBars = 7;
            startingIndex = size - 49;
        }

        for(int i = 0; i < numberOfFullBars; i++){
            float currentSum = 0;
            float currentAvg = 0;
            for(int j = 0; j < 7; j++){
                currentSum += memberCompletionTimesList.get(i*7 + j + startingIndex);
            }
            currentAvg = currentSum/7;
            barArrayList.add(new BarEntry(i+1,currentAvg));
        }

        if(numberOfTimesInPartialBar != 0){
            float currentSum = 0;
            float currentAvg = 0;
            for(int i = 0; i < numberOfTimesInPartialBar; i++){
                currentSum += memberCompletionTimesList.get((numberOfFullBars*7) + i);
            }
            currentAvg = currentSum / numberOfTimesInPartialBar;
            barArrayList.add(new BarEntry(numberOfFullBars + 1, currentAvg));
        }

    }

    /**
     * Description: creates the normal graph which displays the past 7 completion times
     */

    private void createNormalGraph(int displayMemberNumber)
    {

        barArrayList = new ArrayList();

        ArrayList<Integer> memberCompletionTimesList = completionTimes.get(displayMemberNumber);
        int firstIndex = 0;
        if(memberCompletionTimesList.size() > 7){
            firstIndex = memberCompletionTimesList.size()-7;
        }

        if(memberCompletionTimesList.size() < 7){
            for(int i = 0; i < memberCompletionTimesList.size(); i++){
                barArrayList.add(new BarEntry(i, memberCompletionTimesList.get(firstIndex+i)));
            }
        }

        else{
            for(int i = 0; i < 7; i++){
                barArrayList.add(new BarEntry(i, memberCompletionTimesList.get(firstIndex+i)));
            }
        }
//        example of how to add into barArrayList in case I forget:
//        barArrayList.add(new BarEntry(1,10));
    }

    /**
     * Description: Gets Member data from the Firebase Database and stores them into arrayLists
     */

    private void getMemberData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Members").child(currentUserID);
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
                            Log.v("DB","DB is working! for names!");
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
                Log.v("DB", "it is working");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                graphTitle = findViewById(R.id.graphTitle);
                createBarChart();
                getUserInformation();

            }
        },1000);

    }

    /**
     * Description: Gets User data from the Firebase Database and stores them into variables
     */

    private void getUserInformation(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //retrieve user image from Database
        DatabaseReference imgRef = database.getReference("Users").child(currentUserID).child("imageUrl");

        imgRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

                try {
                    String imgUrl = dataSnapshot.getValue().toString();
                    Log.v("DB", imgUrl);

                    ImageView patientImageView = findViewById(R.id.patientImageView);
                    Picasso.with(CaregiverActivity.this).load(imgUrl).into(patientImageView);
                } catch(Exception e){
                    Toast.makeText(CaregiverActivity.this,"No Patient Photo",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
                Toast.makeText(CaregiverActivity.this, "No patient Image", Toast.LENGTH_SHORT).show();
            }
        });


        //retrieve user information from database
        DatabaseReference myRef = database.getReference("Users").child(currentUserID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                try{
                    patientAge = snapshot.child("age").getValue().toString();
                } catch(Exception e){
                    patientAge = "Not Available";
                }

                try{
                    patientEmail = snapshot.child("email").getValue().toString();;
                } catch(Exception e){
                    patientEmail = "Not available";
                }

                try{
                    patientGender = snapshot.child("gender").getValue().toString();;
                } catch(Exception e){
                    patientGender = "Not available";
                }

                try{
                    patientName = snapshot.child("name").getValue().toString();;
                } catch(Exception e){
                    patientName = "Not Available";
                }

                try{
                    patientPhoneNo = snapshot.child("phoneNo").getValue().toString();;
                } catch(Exception e){
                    patientPhoneNo = "Not Available";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });


        // Delays the program. Makes sure that information is fetched before it is set
        //Calls setUserInformation()
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                setUserInformation();
            }
        }, 1000);

    }

    /**
     * Description: Sets the User Info into the scrollView
     */

    private void setUserInformation(){

        TextView nameOfPatientText = (TextView) findViewById(R.id.nameOfPatientEdit);
        TextView ageOfPatientText = (TextView) findViewById(R.id.ageOfPatientEdit);
        TextView genderOfPatientText = (TextView) findViewById(R.id.genderOfPatientEdit);
        TextView stageOfDementiaPatientText = (TextView) findViewById(R.id.stageOfPatientEdit);
        TextView emailOfPatientText = (TextView) findViewById(R.id.emailOfPatientEdit);
        TextView phoneNumberText = (TextView) findViewById(R.id.phoneNoOfPatientEdit);

        nameOfPatientText.setText(patientName);
        ageOfPatientText.setText(patientAge);
        genderOfPatientText.setText( patientGender);
        //stage of dementia
        emailOfPatientText.setText(patientEmail);
        phoneNumberText.setText(patientPhoneNo);
    }

    /**
     * Description: Updates the Bar Chart to the Previous Member's Bar Chart
     */

    public void previousMemberButtonClicked(View v){
        if(displayMemberNumber != 0){
            displayMemberNumber--;

            try{
                createBarChart();
            } catch(Exception e){
                Log.v("DB","Failed to create barchart");
            }
        }
    }

    /**
     * Description: Updates the Bar Chart to the Next Member's Bar Chart
     */

    public void nextMemberButtonClicked(View v){
        if(displayMemberNumber < names.size()-1){
            displayMemberNumber++;

            try{
                createBarChart();
            } catch(Exception e){
                Log.v("DB","Failed to create barchart");
            }
        }
    }

    /**
     * Description: Submits Updated User Information to the Database
     */

    public void sendUserInfoToDB(View v){
        patientName = ((TextView)findViewById(R.id.nameOfPatientEdit)).getText().toString();
        patientAge = ((TextView)findViewById(R.id.ageOfPatientEdit)).getText().toString();
        patientGender = ((TextView)findViewById(R.id.genderOfPatientEdit)).getText().toString();
        //stage
        patientEmail = ((TextView)findViewById(R.id.emailOfPatientEdit)).getText().toString();
        patientPhoneNo = ((TextView)findViewById(R.id.phoneNoOfPatientEdit)).getText().toString();

        HashMap user = new HashMap();
        user.put("age",patientAge);
        user.put("email",patientEmail);
        user.put("gender",patientGender);
        user.put("name",patientName);
        user.put("phoneNo",patientPhoneNo);
        user.put("uniqueID",currentUserID);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        myRef.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){
                    Toast.makeText(CaregiverActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(CaregiverActivity.this, "Failed to Update",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /**
     * Description: opens add member activity
     */
    public void launchAddMember(View v){
        Intent i = new Intent(this, AddMemberActivity.class);
        startActivity(i);
    }


}
