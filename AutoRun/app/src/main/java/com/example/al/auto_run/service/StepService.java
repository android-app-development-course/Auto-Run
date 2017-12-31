package com.example.al.auto_run.service;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.al.auto_run.GpsCheck;
import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.StepCount;
import com.example.al.auto_run.StepDetector;
import com.example.al.auto_run.activity.RecordActivity;
import com.example.al.auto_run.custominterface.StepValuePassListener;
import com.example.al.auto_run.custominterface.UpdateUiCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by finnfu on 16/9/27.
 */

/*
* 后台计步的service
* */

public class StepService extends Service {
    private final IBinder mBinder = new StepBinder();
    private UpdateUiCallBack mCallback;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private StepCount mStepCount;
    private StepDetector mStepDetector;
    private PowerManager.WakeLock wakeLock;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new StepService.MyLocationListener();
    private boolean isFirst=true;
    LatLng last = new LatLng(0, 0);//上一个定位点
    List<LatLng> points = new ArrayList<LatLng>();//位置点集合
    private long firstTime=0;
    private boolean isRide=false;
    private double RideDistant=0;

    private final static int GRAY_SERVICE_ID = 1002;

    private StepValuePassListener mValuePassListener = new StepValuePassListener() {
        @Override
        public void stepChanged(int steps,int state) {
            if(state==2){
                int nowsteps=Integer.parseInt(PreferenceHelper.getSteps_run(getApplicationContext()));
                PreferenceHelper.setSteps_run(getApplicationContext(),steps +nowsteps+ "");
            }
            else if(state==1){
                int nowsteps=Integer.parseInt(PreferenceHelper.getSteps_walk(getApplicationContext()));
                PreferenceHelper.setSteps_walk(getApplicationContext(),steps +nowsteps+ "");
            }
            else if(state==3&&isRide){
                double nowsteps=Double.parseDouble(PreferenceHelper.getSteps_ride(getApplicationContext()));
                PreferenceHelper.setSteps_ride(getApplicationContext(),formatdf(RideDistant +nowsteps)+ "");
            }
            //Log.i("StepService",String.valueOf(state));
            mCallback.updateUi();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        //Log.i("onBind","ok");
        return this.mBinder;
    }

    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        initLocation();
        startLocation();
        mLocationClient.start();

        this.wakeLock = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "StepService");
        this.wakeLock.acquire();
        this.mStepDetector = new StepDetector();
        this.mSensorManager = ((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        this.mSensor = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.mSensorManager.registerListener(this.mStepDetector, this.mSensor, SensorManager.SENSOR_DELAY_UI);

        this.mStepCount = new StepCount();
        this.mStepCount.initListener(this.mValuePassListener);
        this.mStepDetector.initListener(this.mStepCount);

    }

    private void startLocation() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this.getApplicationContext(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Log.d("TTTT", "弹出提示");
        }
        return;
    }
    private void initLocation(){
        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if(location==null)return;

            if(isFirst) {
                //第一个点很重要，决定了轨迹的效果，gps刚开始返回的一些点精度不高，尽量选一个精度相对较高的起始点
                LatLng ll = null;
                ll = getMostAccuracyLocation(location);
                if(ll == null){
                    return;
                }
                isFirst = false;
                last = ll;
                firstTime = System.currentTimeMillis();
                return;//画轨迹最少得2个点，首地定位到这里就可以返回了
            }
            //从第二个点开始

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //Log.i("StepService",ll.toString());

            //sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，可以设置点之间距离大于为5米才添加到集合中
            double distance=DistanceUtil.getDistance(last, ll);

            if (distance < 5||distance>400) {
                //Log.i("StepService",String.valueOf(distance));
                return;
            }
            double gapTime=(System.currentTimeMillis()-firstTime)/1000;
            firstTime=System.currentTimeMillis();
            double speed= distance/gapTime;
            //Log.i("StepService",String.valueOf(speed));
            if(speed>5&&speed<9&& GpsCheck.isGpsOpen(getApplicationContext())){
                isRide=true;
                RideDistant+=distance;
                RideDistant=formatdf(RideDistant);
            }else isRide=false;
            last = ll;
        }
    }

    //double保留两位小数
    private double formatdf(double a){
        DecimalFormat df   =new   java.text.DecimalFormat("#.00");
        df.format(a);
        return a;
    }

    private LatLng getMostAccuracyLocation(BDLocation location){

        if (location.getRadius()>40) {//gps位置精度大于40米的点直接弃用
            return null;
        }

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

        if (DistanceUtil.getDistance(last, ll ) > 10) {
            last = ll;
            points.clear();//有任意连续两点位置大于10，重新取点
            return null;
        }
        points.add(ll);
        last = ll;
        //有5个连续的点之间的距离小于10，认为gps已稳定，以最新的点为起始点
        if(points.size() >= 2){
            points.clear();
            return ll;
        }
        return null;
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        Toast.makeText(this, "开始自动记录", Toast.LENGTH_SHORT).show();
        /*
        * 灰色保活,使服务成为无通知栏显示的前台服务
        * */
        if(Build.VERSION.SDK_INT<18){
            startForeground(0,new Notification());
        }else{
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        return START_STICKY;
    }


    public void onDestroy() {
        this.mSensorManager.unregisterListener(this.mStepDetector);
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        this.wakeLock.release();
        resetValues();
        if (mLocationClient != null)
        {
            mLocationClient.stop();
        }
        super.onDestroy();
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }

    //重置StepCount
    public void resetValues() {
        PreferenceHelper.setSteps_walk(getApplicationContext(),"0");
        PreferenceHelper.setSteps_run(getApplicationContext(),"0");
        PreferenceHelper.setSteps_ride(getApplicationContext(),"0");
    }

    public boolean onUnbind(Intent paramIntent) {
        return super.onUnbind(paramIntent);
    }

    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }



    public static class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

}
