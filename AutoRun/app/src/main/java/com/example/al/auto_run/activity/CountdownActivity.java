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
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.R;
import com.example.al.auto_run.utils.ActivityCollector;
import com.githang.statusbar.StatusBarCompat;

public class CountdownActivity extends BaseActivity {

    private TextView Tv;
    private Animation mAnimation;
    private RelativeLayout countdown_Layout;
    private Bundle bundle;
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1qd));

        //新页面接收数据
        bundle = this.getIntent().getExtras();
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
                Intent intent=new Intent(CountdownActivity.this,RecordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Tv.setAnimation(mAnimation);
        mAnimation.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出倒计时", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
