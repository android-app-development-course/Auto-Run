package com.example.al.auto_run;

import android.content.Intent;
import android.os.Bundle;

import com.example.al.auto_run.activity.LoginActivity;
import com.example.al.auto_run.utils.ActivityCollector;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Bmob.initialize(this, "3d058fed850cb1ceca65c59a7227e7ff");
        //get share preference
        //if have login start the origin activity
        //else start login activity
        if(ActivityCollector.getSize()==1){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent=new Intent(MainActivity.this,ActivityCollector.currentActivity().getClass());
            startActivity(intent);
            finish();
        }
    }
}
