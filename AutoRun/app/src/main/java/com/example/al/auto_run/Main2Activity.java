package com.example.al.auto_run;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    Button bnt_22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bnt_22=(Button)findViewById(R.id.button1);
        //实例化以上控件
        bnt_22.setOnClickListener(this);
        //设置以上按钮的点击事件
    }
    public void onClick(View v) {

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
