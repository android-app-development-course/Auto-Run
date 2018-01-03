package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;
import com.example.al.auto_run.RelatedData;
import com.example.al.auto_run.customview.HistogramCharView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/12/31.
 */

public class StatisticalActivity extends AppCompatActivity {
    public static final String PARAM_0 = "para0";
    private HistogramCharView mCharView;
    private TextView mDateTextView;
    private TextView mTitleTextView;
    private TextView mMilesTextView;
    private ImageView mImgPrev;
    private ImageView mImgNext;

    private List<Integer> stepNums = new ArrayList<>();

    public static final String RUN = "跑步统计";
    public static final String WALK = "步行统计";
    public static final String RIDE = "骑行统计";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        init();

        //get data from the history record activity
        Intent intent = getIntent();
        String title = intent.getStringExtra(PARAM_0);
        mTitleTextView.setText(title);
        mMilesTextView.setText(RelatedData.getCalorieByStep((int) getSum(stepNums)).toString() + "大卡");
        //get data from the shared preference
        String toolbarTitle = mTitleTextView.getText().toString();

        switch (toolbarTitle){
            case RUN : {
                setViewData(PreferenceHelper.DataofWeek_Run);
            }break;
            case WALK : {
                setViewData(PreferenceHelper.DataofWeek_Walk);
            }break;
            case RIDE : {
                setViewData(PreferenceHelper.DataofWeek_Ride);
            }break;
            default:break;
        }
    }

    private void init(){
        mCharView = findViewById(R.id.view_histogram);
        mDateTextView = findViewById(R.id.text_view_history_date);
        mTitleTextView = findViewById(R.id.txt_view_bar_statistical);
        mImgPrev = findViewById(R.id.img_prev);
        mImgNext = findViewById(R.id.img_next);
        mMilesTextView = findViewById(R.id.text_view_kaluli);
    }

    private void setViewData(String preferenceName){
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Sunday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Monday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Tuesday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Wednesday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Thursday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Friday)));
        stepNums.add(Integer.parseInt(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Saturday)));
        mCharView.setData(stepNums);
    }

    private float getSum(List<Integer> list){
        float cnt = 0;
        for (Integer integer : list){
            cnt += integer.intValue();
        }
        return cnt;
    }
}
