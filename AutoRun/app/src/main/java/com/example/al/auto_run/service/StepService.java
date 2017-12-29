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
    private SharedPreferences mSharePreference_run;
    private SharedPreferences mSharePreference_walk;
    private SharedPreferences mSharePreference_ride;
    private SharedPreferences.Editor mEdit_run;
    private SharedPreferences.Editor mEdit_walk;
    private SharedPreferences.Editor mEdit_ride;

    private final static int GRAY_SERVICE_ID = 1002;

    private StepValuePassListener mValuePassListener = new StepValuePassListener() {
        @Override
        public void stepChanged(int steps,int state) {
            if(state==2){
                mEdit_run.putString("steps_run", steps + "");
                mEdit_run.commit();
            }
            else if(state==1){
                mEdit_walk.putString("steps_walk",steps+"");
                mEdit_walk.commit();
            }
            else if(state==3){
                mEdit_ride.putString("steps_ride",steps+"");
                mEdit_ride.commit();
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
        this.mSharePreference_run = getSharedPreferences("relevant_data_run", Activity.MODE_PRIVATE);
        this.mSharePreference_walk =getSharedPreferences("relevant_data_walk", Activity.MODE_PRIVATE);
        this.mSharePreference_ride=getSharedPreferences("relevant_data_ride", Activity.MODE_PRIVATE);
        this.mEdit_run = this.mSharePreference_run.edit();
        this.mEdit_walk =this.mSharePreference_walk.edit();
        this.mEdit_ride=this.mSharePreference_ride.edit();

    }



    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
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
        mEdit_run.putString("steps_run", "0");
        mEdit_walk.putString("steps_walk","0");
        mEdit_ride.putString("steps_ride","0");
        mEdit_run.commit();
        mEdit_walk.commit();
        mEdit_ride.commit();
        super.onDestroy();
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }

    //重置StepCount
    public void resetValues() {
        mEdit_run.putString("steps_run","0");
        mEdit_walk.putString("steps_walk","0");
        mEdit_ride.putString("steps_ride","0");
        mEdit_run.commit();
        mEdit_walk.commit();
        this.mStepCount.setSteps(0);
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
