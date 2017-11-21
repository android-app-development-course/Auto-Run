package com.example.al.auto_run.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.R;
import com.example.al.auto_run.adapters.ViewPagerAdapter;
import com.example.al.auto_run.fragments.AutoOriFragment;
import com.example.al.auto_run.fragments.RideOriFragment;
import com.example.al.auto_run.fragments.RunOriFragment;
import com.example.al.auto_run.fragments.WalkOriFragment;
import com.example.al.auto_run.utils.ActivityCollector;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/11/10.
 */

public class OriginActivty extends BaseActivity
                implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private NavigationView mNavigationView;

    private TabLayout mTabLayout;
    private ViewPager mViewPage;

    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main));

        initView();


        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
       // mToolBar.setNavigationIcon(R.drawable.ic_indicator);
        mNavigationView.setNavigationItemSelectedListener(this);

        List<Fragment> fragments = new ArrayList<Fragment>(){
            {add(new AutoOriFragment()); add(new WalkOriFragment());
             add(new RunOriFragment()); add(new RideOriFragment());}
        };
        String[] titles = {"Auto", "健走", "跑步", "骑行"};
        mViewPage.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPage);
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

             super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_person_info) {
            // Handle the camera action
        } else if (id == R.id.nav_history_record) {

        } else if (id == R.id.nav_id_binding) {

        } else if (id == R.id.nav_detect_back) {

        } else if (id == R.id.nav_about_us) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(OriginActivty.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                   ActivityCollector.destroyAll();
                }
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    private void initView(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolBar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.nav_view);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPage = findViewById(R.id.view_pager);
    }
}
