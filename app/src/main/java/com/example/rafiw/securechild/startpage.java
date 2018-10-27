package com.example.rafiw.securechild;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class startpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);



// Custom animation speed or duration.
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setProgress((Float) animation.getAnimatedValue());
            }
        });
        animator.start();

        //  animationView.cancelAnimation();

//custom color
        /*PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
        animationView.addColorFilter(colorFilter);
        animationView.addColorFilterToLayer("hello_layer", colorFilter);
        animationView.addColorFilterToContent("hello_layer", "hello", colorFilter);*/
        // animationView.clearColorFilters();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(startpage.this, Parentsignup.class);
                startpage.this.startActivity(mainIntent);
                startpage.this.finish();
            }
        }, 5000);

    }
}
