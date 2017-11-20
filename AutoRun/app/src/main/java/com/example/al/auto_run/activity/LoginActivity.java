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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToRegisterBtn = findViewById(R.id.button_register);
        mLoginBtn = findViewById(R.id.button_login);

        mToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OriginActivty.class);
                startActivity(intent);
            }
        });

        ActivityCollector.addActivity(this);
    }
}
