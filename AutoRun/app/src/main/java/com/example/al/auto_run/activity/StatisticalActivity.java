package com.example.al.auto_run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;
import com.example.al.auto_run.RelatedData;
import com.example.al.auto_run.customview.HistogramCharView;

import java.text.DecimalFormat;
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

    private List<Float> stepNums = new ArrayList<>();

    public static final String RUN = "跑步统计";
    public static final String WALK = "健走统计";
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
        mMilesTextView.setText(RelatedData.getCalorieByStep((int) getSum(stepNums)) + "大卡");
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
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Sunday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Monday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Tuesday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Wednesday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Thursday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Friday)));
        stepNums.add(setformat(PreferenceHelper.getDataofWeek(this, preferenceName, PreferenceHelper.Saturday)));
        mCharView.setData(stepNums);
    }

    private float setformat(String s){
        float f=Float.parseFloat(s);
        DecimalFormat decimalFormat=new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(f);
        return Float.parseFloat(p);
    }

    private float getSum(List<Float> list){
        float cnt = 0;
        for (Float item : list){
            cnt += item.intValue();
        }
        return cnt;
    }
}
