package com.example.al.auto_run.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.al.auto_run.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class GetbackpasswordActivity extends AppCompatActivity {
    private Button mToReturnLoginBtn;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getbackpassword);

        mToReturnLoginBtn=(Button)findViewById(R.id.button_to_login1);
        email=(EditText)findViewById(R.id.email);

        mToReturnLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emails=email.getText().toString().trim();
                BmobUser.resetPasswordByEmail(emails, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(), "重置密码请求成功，请到" + emails + "邮箱进行密码重置操作"
                                    + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "重置失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}