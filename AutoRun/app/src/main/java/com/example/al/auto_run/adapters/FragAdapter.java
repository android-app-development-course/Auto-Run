package com.example.al.auto_run.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2017/11/11.
 */

public class FragAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> tabNames;

    public FragAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabIndicators) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
        this.tabNames = tabIndicators;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
