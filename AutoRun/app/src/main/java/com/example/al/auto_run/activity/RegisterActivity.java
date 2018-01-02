package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.Cloud.MyUser;
import com.example.al.auto_run.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by windy on 2017/11/16.
 */

public class RegisterActivity extends BaseActivity {
    public Button mRegisterBtn;
    public Button btn_rgs;
    public EditText edt_rgs_username;
    public EditText edt_rgs_email;
    public EditText edt_rgs_password;
    BmobUser myUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myUser=new BmobUser();

        mRegisterBtn = findViewById(R.id.button_to_login);
        btn_rgs=findViewById(R.id.btn_rgs);
        edt_rgs_username=findViewById(R.id.edt_rgs_username);
        edt_rgs_email=findViewById(R.id.edt_rgs_email);
        edt_rgs_password=findViewById(R.id.edt_rgs_password);

        btn_rgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser.setUsername(edt_rgs_username.getText().toString().trim());
                myUser.setEmail(edt_rgs_email.getText().toString().trim());
                myUser.setPassword(edt_rgs_password.getText().toString().trim());

                myUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser s, BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(),"注册成功!" ,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"注册失败，请检查注册信息" ,
                                    Toast.LENGTH_SHORT).show();
                            Log.e("error", "done:rgsFaild ",e );
                        }
                    }
                });
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void register(){

    }
}
