package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.al.auto_run.R;

public class PersoninformationActivity extends AppCompatActivity {
    ImageButton pReturnMainInfo;
    Button pEditPersonInfo;
    TextView pName;
    TextView pSex;
    TextView pSign;
    TextView pHeight;
    TextView pWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinformation);

        pReturnMainInfo=(ImageButton)findViewById(R.id.personinformation_reback);
        pEditPersonInfo=(Button)findViewById(R.id.editPersonInformation);
        pName=(TextView)findViewById(R.id._name);
        pSex=(TextView)findViewById(R.id._sex);
        pSign=(TextView)findViewById(R.id._sign);
        pHeight=(TextView)findViewById(R.id._height);
        pWeight=(TextView)findViewById(R.id._weight);

        pReturnMainInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersoninformationActivity.this,OriginActivty.class);
                startActivity(intent);
            }
        });
        pEditPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _nameStr=pName.getText().toString();
                String _sexStr=pSex.getText().toString();
                String _signStr=pSign.getText().toString();
                String _heightStr=pHeight.getText().toString();
                String _weightStr=pWeight.getText().toString();

                Intent intent=new Intent(PersoninformationActivity.this,EditpersoninformationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("edName",_nameStr);
                bundle.putString("edSex",_sexStr);
                bundle.putString("edSign",_signStr);
                bundle.putString("edHeight",_heightStr);
                bundle.putString("edWeight",_weightStr);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == PersoninformationActivity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String _Name = bundle.getString("Name");
            String _Sex = bundle.getString("Sex");
            String _Sign = bundle.getString("Sign");
            String _Height = bundle.getString("Height");
            String _Weight = bundle.getString("Weight");
            pName.setText(_Name);
            pSex.setText(_Sex);
            pSign.setText(_Sign);
            pHeight.setText(_Height);
            pWeight.setText(_Weight);
        }
    }
}
