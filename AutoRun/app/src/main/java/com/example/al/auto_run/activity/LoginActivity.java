package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.R;
import com.example.al.auto_run.utils.ActivityCollector;

/**
 * Created by windy on 2017/11/16.
 */

public class LoginActivity extends BaseActivity {
    private Button mToRegisterBtn;
    private Button mLoginBtn;
    private Button mGetBackPasswordBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToRegisterBtn = (Button)findViewById(R.id.button_register);
        mLoginBtn = (Button)findViewById(R.id.button_login);
        mGetBackPasswordBtn = (Button)findViewById(R.id.button_getBackPassword);

        mToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OriginActivty.class);
                startActivity(intent);
                finish();
            }
        });

        mGetBackPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GetbackpasswordActivity.class);
                startActivity(intent);
            }
        });

        ActivityCollector.addActivity(this);
    }
}
