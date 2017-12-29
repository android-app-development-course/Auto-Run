package com.example.al.auto_run;

import android.content.Intent;
import android.os.Bundle;

import com.example.al.auto_run.activity.LoginActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get share preference
        //if have login start the origin activity
        //else start login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
