package com.example.al.auto_run.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.CalendarCount;
import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;
import com.example.al.auto_run.StepAlertManagerUtils;
import com.example.al.auto_run.adapters.ViewPagerAdapter;
import com.example.al.auto_run.custominterface.UpdateUiCallBack;
import com.example.al.auto_run.fragments.AutoOriFragment;
import com.example.al.auto_run.fragments.RideOriFragment;
import com.example.al.auto_run.fragments.RunOriFragment;
import com.example.al.auto_run.fragments.WalkOriFragment;
import com.example.al.auto_run.service.StepService;
import com.example.al.auto_run.utils.ActivityCollector;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/11/10.
 */

public class OriginActivty extends BaseActivity
                implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private NavigationView mNavigationView;

    private TabLayout mTabLayout;
    private ViewPager mViewPage;

    private long firstTime = 0;

    private ViewPagerAdapter viewPagerAdapter;

    private StepService mService;
    private boolean mIsRunning;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                viewPagerAdapter.update(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main));

        CalendarCount.initData(this);
        Log.i("Today", PreferenceHelper.getDataofToday(this));
        StepAlertManagerUtils.set0SeparateAlertManager(this);

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
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);

        mViewPage.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPage);

        startLocation();

        startStepService();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

             super.onBackPressed();
        }
    }

    private void startLocation() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Log.d("TTTT", "弹出提示");
        }
        return;
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
            Intent intent=new Intent(OriginActivty.this,PersoninformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history_record) {
            Intent intent=new Intent(OriginActivty.this, HistoryRecordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_id_binding) {

        } else if (id == R.id.nav_detect_back) {

        } else if (id == R.id.nav_about_us) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("tag", "onNewINtent执行了");
        setIntent(intent);
        int TypeNum = intent.getIntExtra("TypeNum",-1);
        if(TypeNum!=-1){
            Log.i("TypeNum",String.valueOf(TypeNum));
            viewPagerAdapter.update(TypeNum+1);
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(OriginActivty.this, "再按一次退出app", Toast.LENGTH_SHORT).show();
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

    protected void onDestroy() {
        super.onDestroy();
//        stopStepService();
    }

    protected void onPause() {
        unbindStepService();
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        if (this.mIsRunning){
            bindStepService();
        }
    }



    private UpdateUiCallBack mUiCallback = new UpdateUiCallBack() {
        @Override
        public void updateUi() {
            Message message = mHandler.obtainMessage();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService.StepBinder binder = (StepService.StepBinder) service;
            mService = binder.getService();
            mService.registerCallback(mUiCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void bindStepService() {
        bindService(new Intent(this, StepService.class), this.mConnection, Context.BIND_AUTO_CREATE);
        //Log.i("bindService","successful");
    }

    private void unbindStepService() {
        unbindService(this.mConnection);
    }

    private void startStepService() {
        this.mIsRunning = true;
        Log.d("mainactivity》》","启动服务"+startService(new Intent(this, StepService.class)));
    }

    private void stopStepService() {
        this.mIsRunning = false;
        if (this.mService != null)
            stopService(new Intent(this, StepService.class));
    }
}
