package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static String CURRENT_USER_KEY = "current user";

    GridView gridView;
    String[] names;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        gridView = findViewById(R.id.loginGridView);

        getGridData();

        ProfileAdapter profileAdapter = new ProfileAdapter(LoginActivity.this, names, images);
        gridView.setAdapter(profileAdapter);

        // TODO: if password protected open display

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(LoginActivity.this, "You clicked on " + names[position],
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(CURRENT_USER_KEY, names[position]);

                intent.putExtras(bundle);

                startActivity(intent);
            }

        });

    }

    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    // TODO: Fetch this data from SQL database
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

    // will open dialog fragment window if password protected

    // if not will open main menu and set user to this person
}
