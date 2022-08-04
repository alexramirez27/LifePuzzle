package com.xd.lifepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EditMemberActivity extends AppCompatActivity {
    public static String CURRENT_MEMBER_KEY = "current member";

    GridView gridView;
    List<String> names;
    //    String[] names;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        names = new ArrayList<>();
        getGridData();
        setGridView();
        gridViewOnClickListener();

    }

    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    private void getGridData(){

//        names = new String[] {"Jill Smith", "Christy Kelley", "Alexander Walker", "Eric Vazquez",
//                "John Hopkins", "Becky Alvarez", "Michelle Moore", "Dean Thomas",
//                "Jorge Schmitt", "Cody Harrison", "David Perkins", "Daniel Jordan",
//                "Cynthia Lane", "Jeremiah Lamb", "Rhonda Jones", "Thomas Barnes",
//                "Faith Rose", "Philip Mitchell", "Angel Moreno", "Jacob Jenkins",
//                "Matthew Jones","Jo Davis" , "Monica Barker" , "Amy Collins" };

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
        gridView = findViewById(R.id.editMemberGridView);
        ProfileAdapter profileAdapter = new ProfileAdapter(EditMemberActivity.this, names, images);
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
                bundle.putString(CURRENT_MEMBER_KEY, names.get(position));

                Intent intent = new Intent(getApplicationContext(), AddMemberActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });
    }
}
