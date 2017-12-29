package com.example.al.auto_run.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.al.auto_run.MainActivity;
import com.example.al.auto_run.R;

public class EditpersoninformationActivity extends AppCompatActivity {
    ImageButton eReturnPersonInfo;
    Button submitPersonInfo;
    EditText eName;
    EditText eSex;
    EditText eSign;
    EditText eHeight;
    EditText eWeight;
    Intent intent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpersoninformation);

        eReturnPersonInfo=(ImageButton)findViewById(R.id.personinfo_reback);
        submitPersonInfo=(Button)findViewById(R.id.submit);
        eName=(EditText) findViewById(R.id.editname);
        eSex=(EditText) findViewById(R.id.editsex);
        eSign=(EditText) findViewById(R.id.editsign);
        eHeight=(EditText) findViewById(R.id.editheight);
        eWeight=(EditText) findViewById(R.id.editweight);

        intent = getIntent();
        bundle = intent.getExtras();
        String _edName = bundle.getString("edName");
        String _edSex = bundle.getString("edSex");
        String _edSign = bundle.getString("edSign");
        String _edHeight = bundle.getString("edHeight");
        String _edWeight = bundle.getString("edWeight");
        eName.setText(_edName);
        eSex.setText(_edSex);
        eSign.setText(_edSign);
        eHeight.setText(_edHeight);
        eWeight.setText(_edWeight);

        eReturnPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditpersoninformationActivity.this,PersoninformationActivity.class);
                startActivity(intent);
            }
        });
        submitPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr=eName.getText().toString();
                String sexStr=eSex.getText().toString();
                String signStr=eSign.getText().toString();
                String heightStr=eHeight.getText().toString();
                String weightStr=eWeight.getText().toString();

                Toast.makeText(EditpersoninformationActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

                bundle.putString("Name",nameStr);
                bundle.putString("Sex",sexStr);
                bundle.putString("Sign",signStr);
                bundle.putString("Height",heightStr);
                bundle.putString("Weight",weightStr);
                intent.putExtras(bundle);
                setResult(PersoninformationActivity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
