package com.example.al.auto_run.activity;

/**
 * Created by Al on 2017/11/19.
 */

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.al.auto_run.R;
import com.githang.statusbar.StatusBarCompat;

public class CountdownActivity extends AppCompatActivity {

    TextView Tv;
    Animation mAnimation;
    RelativeLayout countdown_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1qd));

        countdown_Layout=(RelativeLayout)findViewById(R.id.countdown_layout);
        Tv =(TextView)findViewById(R.id.tv);

        mAnimation = AnimationUtils.loadAnimation(this,R.anim.countdown_animation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Tv.setText("2");
                countdown_2();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Tv.setAnimation(mAnimation);
        mAnimation.start();

    }
    private void countdown_2(){
        mAnimation = AnimationUtils.loadAnimation(this,R.anim.countdown_animation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Tv.setText("1");
                countdown_1();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Tv.setAnimation(mAnimation);
        mAnimation.start();
    }
    private void countdown_1(){
        mAnimation = AnimationUtils.loadAnimation(this,R.anim.countdown_animation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Tv.setText("Go");
                countdown_go();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Tv.setAnimation(mAnimation);
        mAnimation.start();
    }
    private void countdown_go(){
        mAnimation = AnimationUtils.loadAnimation(this,R.anim.countdown_animation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationEnd(Animation animation) {
                Tv.setText("");
                Intent intent=new Intent(getApplication(),RecordActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Tv.setAnimation(mAnimation);
        mAnimation.start();
    }
}
