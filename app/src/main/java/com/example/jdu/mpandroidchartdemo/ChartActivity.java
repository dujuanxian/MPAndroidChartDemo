package com.example.jdu.mpandroidchartdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ChartActivity extends AppCompatActivity {
    private LineChart chart;
    final static Logger logger = Logger.getLogger("ChartActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = findViewById(R.id.line_chart);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);
        disableAxis();
        addData();
        setChartValueSelectedListener();
        chart.animateX(500);
    }

    private void addData() {
        ArrayList<Entry> entryList = createEntryList();
        LineDataSet lineDataSet = createLineDataSet(entryList);

        LineData data = new LineData(lineDataSet);
        data.setDrawValues(false);

        chart.setData(data);
    }

    private void setChartValueSelectedListener() {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setHighlightAnimation(h);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private LineDataSet createLineDataSet(ArrayList<Entry> entryList) {
        LineDataSet set = new LineDataSet(entryList, null);

        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(5f);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setDrawCircleHole(true);
        set.setCircleColorHole(Color.rgb(43, 196, 90));
        set.setCircleHoleRadius(4f);
        set.setHighlightEnabled(true);
        set.setDrawHighlightIndicators(false);
        setGradientFill(set);

        return set;
    }

    private void setHighlightAnimation(Highlight highlight) {
        float xPx = highlight.getXPx();
        float yPx = highlight.getYPx();

        View circleView = findViewById(R.id.circle);
        showCircle(circleView, xPx, yPx);
        scaleCircle(circleView, 1.4f);

        View circleBgView = findViewById(R.id.circle_bg);
        showCircle(circleBgView, xPx, yPx);
        scaleCircle(circleBgView, 0.8f);
    }

    private void showCircle(View view, float xPx, float yPx) {
        view.setVisibility(View.VISIBLE);
        view.setX(xPx - view.getWidth()/2f);
        view.setY(yPx - view.getHeight()/2f);
    }

    private void disableAxis() {
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setEnabled(false);
        YAxis axisRight = chart.getAxisRight();
        axisRight.setEnabled(false);
    }

    private void setGradientFill(LineDataSet set) {
        set.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient_bg);
        set.setFillDrawable(drawable);
    }

    private void scaleCircle(View view, float scaleSize) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", 1f, scaleSize, 1f);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", 1f, scaleSize, 1f);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(1000);
        animSetXY.start();
    }

    private ArrayList<Entry> createEntryList() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < 20; i++) {
            float mult = 5 / 2f;
            float val = (float) (Math.random() * mult) + 5;
            yVals1.add(new Entry(i, val));
        }

        return yVals1;
    }

}
