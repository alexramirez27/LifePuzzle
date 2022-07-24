package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Description: Allows the user to select a user to login to from our database
 * Post-condition: database accessed to retrieve user
 */
public class LoginActivity extends AppCompatActivity {

    public static String CURRENT_USER_KEY = "current user";

    GridView gridView;
    String[] names;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        getGridData();
        setGridView();
        gridViewOnClickListener();

    }

    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    private void getGridData(){

        names = new String[] {"Jill Smith", "Christy Kelley", "Alexander Walker", "Eric Vazquez",
                              "John Hopkins", "Becky Alvarez", "Michelle Moore", "Dean Thomas",
                              "Jorge Schmitt", "Cody Harrison", "David Perkins", "Daniel Jordan",
                              "Cynthia Lane", "Jeremiah Lamb", "Rhonda Jones", "Thomas Barnes",
                              "Faith Rose", "Philip Mitchell", "Angel Moreno", "Jacob Jenkins",
                              "Matthew Jones","Jo Davis" , "Monica Barker" , "Amy Collins" };

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
        gridView = findViewById(R.id.loginGridView);
        ProfileAdapter profileAdapter = new ProfileAdapter(LoginActivity.this, names, images);
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
                bundle.putString(CURRENT_USER_KEY, names[position]);

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });
    }

}
