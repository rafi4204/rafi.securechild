package com.example.rafiw.securechild;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class selectDevice extends AppCompatActivity {
    Button parent_device;
    Button child_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);



        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
            parent_device=(Button)findViewById(R.id.parent_device);
        child_device=(Button)findViewById(R.id.child_device);

        parent_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectDevice.this,Menu.class);
                startActivity(intent);

            }
        });
        child_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectDevice.this,ChildMenu.class);
                startActivity(intent);

            }
        });



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




    }
}
