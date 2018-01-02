package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.Cloud.MyUser;
import com.example.al.auto_run.R;
import com.example.al.auto_run.utils.ActivityCollector;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by windy on 2017/11/16.
 */

public class LoginActivity extends BaseActivity {
    private Button mToRegisterBtn;
    private Button mLoginBtn;
    private Button mGetBackPasswordBtn;

    EditText edt_login_username;
    EditText edt_login_password;

    BmobUser bmobUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mToRegisterBtn = (Button)findViewById(R.id.button_register);
        mLoginBtn = (Button)findViewById(R.id.button_login);
        mGetBackPasswordBtn = (Button)findViewById(R.id.button_getBackPassword);

        bmobUser = BmobUser.getCurrentUser(MyUser.class);
        edt_login_username=findViewById(R.id.edt_login_username);
        edt_login_password=findViewById(R.id.edt_login_password);

        if(bmobUser!=null)
        {
            edt_login_username.setText(bmobUser.getUsername());
            edt_login_password.setText("******");
        }
        edt_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                bmobUser=null;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                if(bmobUser==null) {
                    bmobUser = new BmobUser();
                    bmobUser.setUsername(edt_login_username.getText().toString().trim());
                    bmobUser.setPassword(edt_login_password.getText().toString().trim());
                    bmobUser.login(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "登录成功！",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, OriginActivty.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "用户名或密码错误！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    bmobUser = BmobUser.getCurrentUser(MyUser.class);
                    Toast.makeText(getApplicationContext(), "登录成功！",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OriginActivty.class);
                    startActivity(intent);
                }
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
