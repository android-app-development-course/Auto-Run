package com.example.al.auto_run.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.al.auto_run.fragments.AutoOriFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/11/11.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;
    private List<String> tagList = new ArrayList<String>();
    private FragmentManager fm;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.fm=fm;
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position){
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tagList.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public static String makeFragmentName(int viewId, long index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    public void update(int item) {
        Fragment fragment = fm.findFragmentByTag(tagList.get(item));
        if (fragment != null) {
            switch (item) {
                case 0:
                    ((AutoOriFragment) fragment).Change();
                    break;
                case 1:
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    }
}
