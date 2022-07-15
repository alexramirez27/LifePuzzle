package com.xd.lifepuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CaregiverActivity extends AppCompatActivity {

    ArrayList barArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver);
        createBarChart();

    }

    /**
     * Description: Creates Bar Chart
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
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

    }

    /**
     * Description: Gets Data and adds into barArrayList
     */

    private void getData()
    {
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(2f,10));
        barArrayList.add(new BarEntry(3f,20));
        barArrayList.add(new BarEntry(4f,30));
        barArrayList.add(new BarEntry(5f,40));
        barArrayList.add(new BarEntry(6f,50));
        barArrayList.add(new BarEntry(7f,60));

    }

    /**
     * Description: opens add member activity
     * currently I do not have AddMember.java yet, so I will implement this feature later
     */
//    public void launchAddMember(View v){
//        Intent i = new Intent(this, AddMemberActivity.class);
//        startActivity(i);
//    }



}
