package com.example.al.auto_run.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al.auto_run.Cloud.DetailRecord;
import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HistoryDetailed extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detailed);
        ImageButton history_detailed_reback = (ImageButton) findViewById(R.id.history_detailed_reback);
        final TextView history_detailed_distance = findViewById(R.id.history_detailed_distance);
        final TextView history_detailed_stepCount = findViewById(R.id.history_detailed_stepCount);
        final TextView history_detailed_time = findViewById(R.id.history_detailed_time);
        final TextView history_detailed_dateAndType = findViewById(R.id.history_detailed_dateAndType);


        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");

    /* ********************************************************************************/


        BmobQuery<DetailRecord> query = new BmobQuery<DetailRecord>();

        query.addWhereEqualTo("RecordID", ID);

        query.findObjects(new FindListener<DetailRecord>() {
            @Override
            public void done(List<DetailRecord> list, BmobException e) {
                if (e == null) {
                    history_detailed_distance.setText(Float.toString(list.get(0).getAthleticsLength()));
                    history_detailed_stepCount.setText(list.get(0).getAthleticsDetail());
                    history_detailed_dateAndType.setText(list.get(0).getAthleticsType());
                    history_detailed_time.setText(list.get(0).getAthleticsTime());
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        /* *********************************************************************************/


        history_detailed_reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDetailed.this.finish();
            }
        });
    }
}

