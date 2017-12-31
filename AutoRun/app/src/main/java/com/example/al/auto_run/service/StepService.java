package com.example.al.auto_run.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.StepCount;
import com.example.al.auto_run.StepDetector;
import com.example.al.auto_run.custominterface.StepValuePassListener;
import com.example.al.auto_run.custominterface.UpdateUiCallBack;

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
            else if(state==3){
                int nowsteps=Integer.parseInt(PreferenceHelper.getSteps_ride(getApplicationContext()));
                PreferenceHelper.setSteps_ride(getApplicationContext(),steps +nowsteps+ "");
            }
            //Log.i("service","ok");
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
        PreferenceHelper.setSteps_walk(getApplicationContext(),"0");
        PreferenceHelper.setSteps_run(getApplicationContext(),"0");
        PreferenceHelper.setSteps_ride(getApplicationContext(),"0");
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
