package com.example.jdu.mpandroidchartdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart mChart;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_radar_chart:
                    return true;
                case R.id.navigation_highlighter:
                    Intent circleAnimatorIntent = new Intent(MainActivity.this, CircleAnimator.class);
                    startActivity(circleAnimatorIntent);
                    return true;
                case R.id.navigation_line_chart:
                    Intent chartIntent = new Intent(MainActivity.this, ChartActivity.class);
                    startActivity(chartIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RadarChart radarChart = (RadarChart) findViewById(R.id.radar_chart);
        radarChart.getDescription().setEnabled(false);

        ArrayList<RadarEntry> entryList1 = createEntryList();
        ArrayList<RadarEntry> entryList2 = createEntryList();

        RadarDataSet radarDataSet1 = new RadarDataSet(entryList1, null);
        RadarDataSet radarDataSet2 = new RadarDataSet(entryList2, null);

        radarDataSet1.setColor(Color.GRAY);
        radarDataSet1.setDrawHighlightCircleEnabled(true);
        radarDataSet1.setHighlightCircleStrokeColor(Color.YELLOW);
        radarDataSet1.setHighlightCircleStrokeWidth(2);
        radarDataSet1.setFillAlpha(60);
        radarDataSet1.setDrawFilled(true);
        radarDataSet1.setDrawValues(false);

        radarDataSet2.setColor(Color.BLUE);
        radarDataSet2.setDrawHighlightCircleEnabled(false);
        radarDataSet2.setDrawFilled(true);
        radarDataSet2.setDrawValues(false);

        List<IRadarDataSet> radarDataSets = new ArrayList<>();
        radarDataSets.add(radarDataSet1);
        radarDataSets.add(radarDataSet2);
        RadarData radarData = new RadarData(radarDataSets);

        radarChart.setData(radarData);
        radarChart.animate();


        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setDrawLabels(false);

    }

    private ArrayList<RadarEntry> createEntryList() {
        ArrayList<RadarEntry> entries = new ArrayList<RadarEntry>();

        for (int i = 0; i < 5; i++) {
            float mult = 5 / 2f;
            float val = (float) (Math.random() * mult) + 2;
            entries.add(new RadarEntry(val, i));
        }
        return entries;
    }

}
