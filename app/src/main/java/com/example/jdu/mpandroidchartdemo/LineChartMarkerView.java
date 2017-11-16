package com.example.jdu.mpandroidchartdemo;

import android.content.Context;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.logging.Logger;

public class LineChartMarkerView extends MarkerView{

    final static Logger logger = Logger.getLogger("LineChartMarkerView");

    public LineChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public MPPointF getOffset() {
        MPPointF mOffset = new MPPointF();
        mOffset.x = -(getWidth() / 2);
        mOffset.y = -(getHeight() / 2);
        return mOffset;
    }
}
