package com.example.al.auto_run.activity;

/**
 * Created by Al on 2017/11/19.
 */

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.al.auto_run.adapters.FragAdapter;
import com.example.al.auto_run.fragments.lvFragment;
import com.example.al.auto_run.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

public class HistoryRecordActivity extends AppCompatActivity {

    TabLayout Tab;
    ViewPager viewPager;
    ImageButton return_btn;
    ArrayList<String> tabIndicators;
    ArrayList<Fragment> Fragments;
    ListView history_lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main));

        Tab=(TabLayout)findViewById(R.id.history_tab);
        viewPager=(ViewPager)findViewById(R.id.history_vp);
        return_btn=(ImageButton)findViewById(R.id.return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryRecordActivity.this.finish();
            }
        });





        initTab();
        initViewPager();
    }
    private void initTab(){
        ViewCompat.setElevation(Tab,10);
        Tab.setupWithViewPager(viewPager);
        tabIndicators=new ArrayList<>();
        tabIndicators.add("11月");
        tabIndicators.add("10月");
        tabIndicators.add("9月");
    }

    private void initViewPager(){
        Fragments=new ArrayList<>();
        for(int i=1;i<=3;i++) {
           /* temp = lvFragment.newInstance(10+i);*/
            lvFragment temp=new lvFragment();
            temp.input(this);
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
}



