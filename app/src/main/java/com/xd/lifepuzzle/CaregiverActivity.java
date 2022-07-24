package com.xd.lifepuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;


/**
 * Description: allows the caregiver to see data about the time it takes the user to complete
 *              each puzzle they do
 * Post-condition: new user added to our database
 */
public class CaregiverActivity extends AppCompatActivity {

    ArrayList barArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        createBarChart();
    }

    /**
     * Description: Creates Bar Chart of the time it takes the user to complete the puzzle
     */
    private void createBarChart(){

        BarChart barChart = findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArrayList, "Time for Completion");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //color bar data set
        barDataSet.setColor(R.color.light_blue);
        //text color
        barDataSet.setValueTextColor(Color.BLACK);
        //setting text size
        barDataSet.setValueTextSize(20f);
        //barChart.getDescription().setEnabled(true);

    }

    /**
     * Description: Gets Data and adds into barArrayList
     */

    private void getData()
    {
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(1,10));
        barArrayList.add(new BarEntry(2,20));
        barArrayList.add(new BarEntry(3,30));
        barArrayList.add(new BarEntry(4,40));
        barArrayList.add(new BarEntry(5,50));
        barArrayList.add(new BarEntry(6,60));

    }

    /**
     * Description: opens add member activity
     */
    public void launchAddMember(View v){
        Intent i = new Intent(this, AddMemberActivity.class);
        startActivity(i);
    }

    /**
     * Description: opens edit member activity
     */

    public void launchEditMember(View v){
        Intent i = new Intent(this, EditMemberActivity.class);
        startActivity(i);
    }


}
