package com.example.al.auto_run.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.al.auto_run.R;

public class GetbackpasswordActivity extends AppCompatActivity {
    private Button mToReturnLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getbackpassword);

        mToReturnLoginBtn=(Button)findViewById(R.id.button_to_login1);
        mToReturnLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetbackpasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}