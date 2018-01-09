package com.example.al.auto_run.activity;

/**
 * Created by Al on 2017/11/19.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al.auto_run.Cloud.MyUser;
import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.R;
import com.example.al.auto_run.adapters.FragAdapter;
import com.example.al.auto_run.fragments.lvFragment;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HistoryRecordActivity extends AppCompatActivity {


    TabLayout Tab;
    ViewPager viewPager;
    ImageButton return_btn;
    ArrayList<String> tabIndicators;
    ArrayList<Fragment> Fragments;
    ListView history_lv;

    private TextView mStatiscTxtView;
    private Spinner mSpinner;

    private String mSpinnerSelectedValue = "跑步统计";

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //从bundle中获取
                    String LoadTab = msg.getData().getString("LoadTab");
                    initViewPager(LoadTab);
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main));

        Tab=(TabLayout)findViewById(R.id.history_tab);
        viewPager=(ViewPager)findViewById(R.id.history_vp);
        return_btn=(ImageButton)findViewById(R.id.return_btn);
        mStatiscTxtView = findViewById(R.id.txt_view_statistical);
        mSpinner = findViewById(R.id.center_spinner);

        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryRecordActivity.this.finish();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] items = getResources().getStringArray(R.array.spitem);
                mSpinnerSelectedValue = items[i] + "统计";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mSpinnerSelectedValue = "跑步统计";
            }
        });

        mStatiscTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryRecordActivity.this, StatisticalActivity.class);
                intent.putExtra(StatisticalActivity.PARAM_0, mSpinnerSelectedValue);
                startActivity(intent);
            }
        });

        initTab();

    }
    private void initTab(){
        ViewCompat.setElevation(Tab,10);
        Tab.setupWithViewPager(viewPager);
        /* ****************************************************************/


        MyUser user= BmobUser.getCurrentUser(MyUser.class);

        BmobQuery<SimpleRecord> query=new BmobQuery<SimpleRecord>();

        query.addWhereEqualTo("username",user);

        query.addQueryKeys("AthleticsMonth");

        query.groupby(new String[]{"AthleticsMonth"});

        query.findObjects(new FindListener<SimpleRecord>() {
            @Override
            public void done(List<SimpleRecord> list, BmobException e) {
                if(e==null){


                    Bundle month=new Bundle();
                    Message msg = new Message();
                    String LoadTab=list.get(0).getAthleticsMonth();
                    String TempString=LoadTab;

                    for (int i=1;i<list.size();i++)
                    {
                        if(!TempString.equals(list.get(i).getAthleticsMonth()))
                        {
                            LoadTab = LoadTab+","+list.get(i).getAthleticsMonth();
                        }
                        TempString=list.get(i).getAthleticsMonth();
                    }

                    msg.what=0;
                    month.putString("LoadTab",LoadTab);
                    msg.setData(month);
                    mHandler.sendMessage(msg);

                }else{
                    Toast.makeText(getApplicationContext(),"未获取到数据,请确定是否有保存运动记录", Toast.LENGTH_SHORT).show();
                }
            }

        });

        /* ****************************************************************/
    }

    private void initViewPager(String LoadTab){
        Fragments=new ArrayList<>();
        tabIndicators=new ArrayList<>();

        String tempTab[]=LoadTab.split(",");
        for(int i=0;i<tempTab.length;i++)
        {
            tabIndicators.add(tempTab[i]);
        }

        for(int i=0;i<tabIndicators.size();i++) {
           /* temp = lvFragment.newInstance(10+i);*/
            lvFragment temp=new lvFragment();
            temp.input(this,tabIndicators.get(i).trim());
            Fragments.add(temp);
        }


        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager(),
                Fragments,tabIndicators));

        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void actionStart(Context context, String... param){
        Intent intent = new Intent(context, HistoryRecordActivity.class);
        context.startActivity(intent);
    }
}



