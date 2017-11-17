package com.example.jdu.mpandroidchartdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class LineChartActivity extends AppCompatActivity{
    private LineChart chart;
    private Highlight[] highlightEntryList;
    final static Logger logger = Logger.getLogger("LineChartActivity");
    private ViewGroup marqueeViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
        SimpleMarqueeView marqueeView = getSimpleMarqueeView(datas);
        marqueeViewContainer = findViewById(R.id.marqueeview_container);
        marqueeViewContainer.addView(marqueeView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
        setDrawMarkers();

        chart.animateX(500);

        chart.highlightValues(highlightEntryList);
    }

    @NonNull
    private SimpleMarqueeView getSimpleMarqueeView(List<String> datas) {
        SimpleMarqueeView marqueeView = new SimpleMarqueeView(this);
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.setAnimDuration(2000);
        marqueeView.setFlipInterval(2500);
        marqueeView.setTextGravity(Gravity.CENTER_VERTICAL);
        marqueeView.setTextSize(18);
        marqueeView.setInAndOutAnim(R.anim.in_left, R.anim.out_right);
        marqueeView.startFlipping();
        return marqueeView;
    }

    private void setDrawMarkers() {
        chart.setDrawMarkers(true);
        LineChartMarkerView customMarkerView = new LineChartMarkerView(this, R.layout.line_chart_highlighter);
        chart.setMarker(customMarkerView);
    }

    private void addData() {
        ArrayList<Entry> entryList = createEntryList();
        setHighlightEntryList(entryList);

        LineDataSet lineDataSet = createLineDataSet(entryList);

        LineData data = new LineData(lineDataSet);
        data.setDrawValues(false);

        chart.setData(data);
    }

    private void setChartValueSelectedListener() {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                for (Highlight aHighlightEntryList : highlightEntryList) {
                    if (aHighlightEntryList.getX() == e.getX()) {
                        setHighlightAnimation(h);

                        marqueeViewContainer.removeAllViews();
                        final List<String> datas = Arrays.asList("离离原上草，一岁一枯荣。", "《赋得古原草送别》",  "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
                        marqueeViewContainer.addView(getSimpleMarqueeView(datas), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                }
                chart.highlightValues(highlightEntryList);
            }

            @Override
            public void onNothingSelected() {
                chart.highlightValues(highlightEntryList);
            }
        });
    }

    private LineDataSet createLineDataSet(ArrayList<Entry> entryList) {
        LineDataSet set = new LineDataSet(entryList, null);

        set.setLineWidth(2f);
        set.setDrawCircles(false);
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

        for (int i = 0; i < 50; i++) {
            float mult = 5 / 2f;
            float val = (float) (Math.random() * mult) + 5;
            yVals1.add(new Entry(i, val));
        }

        return yVals1;
    }

    public void setHighlightEntryList(ArrayList<Entry> entryList) {
        Highlight h1 = new Highlight(entryList.get(10).getX(), entryList.get(0).getY(), 0);
        Highlight h2 = new Highlight(entryList.get(20).getX(), entryList.get(1).getY(), 0);
        Highlight h3 = new Highlight(entryList.get(25).getX(), entryList.get(1).getY(), 0);
        Highlight h4 = new Highlight(entryList.get(35).getX(), entryList.get(1).getY(), 0);
        Highlight h5 = new Highlight(entryList.get(40).getX(), entryList.get(1).getY(), 0);
        Highlight h6 = new Highlight(entryList.get(42).getX(), entryList.get(1).getY(), 0);
        highlightEntryList = new Highlight[]{h1, h2, h3, h4, h5, h6};
    }
}
