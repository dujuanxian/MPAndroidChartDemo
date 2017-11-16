package com.example.jdu.mpandroidchartdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CircleAnimator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_animator);

        View circleView = findViewById(R.id.circle);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCircle(view);
                animateCircleBg();
            }
        });
    }

    private void animateCircleBg() {
        View circleBgView = findViewById(R.id.circle_bg);
        ObjectAnimator animX = ObjectAnimator.ofFloat(circleBgView, "scaleX", 1f, 0.8f, 1f);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animY = ObjectAnimator.ofFloat(circleBgView, "scaleY", 1f, 0.8f, 1f);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(1000);
        animSetXY.start();
    }

    private void animateCircle(View view) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.4f, 1f);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.4f, 1f);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(1000);
        animSetXY.start();
    }
}
