package com.example.al.auto_run.activity;

/**
 * Created by Al on 2017/11/19.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.al.auto_run.BaseActivity;
import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;
import com.example.al.auto_run.customanim.CircularAnim;
import com.example.al.auto_run.customview.TasksCompletedView;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity implements SensorEventListener {
    TextView tv_kilometre;
    TextView tv_title;
    TextView tv_num_kilometre;
    TextView tv_timer;
    TextView tv_num_speeder;
    TextView tv_speeder;
    TextView tv_little_num_kilometre;
    Chronometer timer;
    Chronometer little_timer;

    ImageButton Btn_stop;
    ImageButton Btn_comeon;
    ImageButton Btn_gps;

    RelativeLayout record_relativeLayout;

    LinearLayout linearLayout_comeonbtn;
    LinearLayout linearLayout_overbtn;
    LinearLayout linearLayout_stopbtn;

    Animation animation_stopbtn;
    Animation animation_comeon;
    Animation animation_over;
    AnimationSet mAnimationSet;

    public MyClickListener myClickListener=new MyClickListener();
    private TasksCompletedView mTasksView;
    private int mTotalProgress;
    private int mCurrentProgress;
    private Handler mHandler = new Handler();
    private boolean isClick;

    private MapView mMapView;
    public BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new RecordActivity.MyLocationListener();
    private boolean isFirst=true;

    LatLng last = new LatLng(0, 0);//上一个定位点
    List<LatLng> points = new ArrayList<LatLng>();//位置点集合
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private MyLocationData locData;
    float mCurrentZoom = 18f;//默认地图缩放比例值
    MapStatus.Builder builder;
    Polyline mPolyline;//运动轨迹图层

    //起点图标
    // BitmapDescriptor startBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_24dp);
    //终点图标
    //BitmapDescriptor finishBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_24dp);

    ImageButton Btn_getlocal;
    ImageButton Btn_outoflocal;
    RelativeLayout baidumap_layout;

    private SensorManager mSensorManager;
    double lastX;
    private BDLocation mlocation=null;
    private LatLng mll=null;
    public int mileage=0;
    long mRecordTime=0;
    private String[] SportType={"健走","跑步","骑行"};
    private int TypeNum;
    private int Steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_record);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main2));

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        TypeNum =Integer.parseInt(bundle.getString("Type"));
        Steps =getSteps();
        initMap();
        initLocation();
        startLocation();
        mLocationClient.start();

        //记录界面
        initVariable();
        initView();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];

        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;

            if (isFirst) {
                lastX = x;
                return;
            }

            locData = new MyLocationData.Builder().accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat).longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            float myalpha;
            switch (view.getId()){
                case R.id.btn_comeon:

                    if (mRecordTime != 0) {
                        timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                        little_timer.setBase(little_timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    }else {
                        timer.setBase(SystemClock.elapsedRealtime());
                        little_timer.setBase(SystemClock.elapsedRealtime());
                    }
                    timer.start();
                    little_timer.start();

                    onResume();
                    myalpha= (float) 1;
                    tv_kilometre.setAlpha(myalpha);
                    tv_num_kilometre.setAlpha(myalpha);
                    tv_num_speeder.setAlpha(myalpha);
                    tv_speeder.setAlpha(myalpha);
                    tv_timer.setAlpha(myalpha);
                    timer.setAlpha(myalpha);
                    tv_title.setText(SportType[TypeNum]+"中");
                    linearLayout_comeonbtn.setVisibility(View.GONE);
                    linearLayout_overbtn.setVisibility(View.GONE);
                    linearLayout_stopbtn.setVisibility(View.VISIBLE);
                    animation_stopbtn=AnimationUtils.loadAnimation(getApplication(),R.anim.scale_stopbtn_bebig);
                    animation_stopbtn.setAnimationListener(new myanimatinListener());
                    linearLayout_stopbtn.startAnimation(animation_stopbtn);
                    break;
                case R.id.btn_gps:
                    view.animate().rotationBy(90);

                    CircularAnim.show(baidumap_layout).triggerView(Btn_gps).go(
                            new CircularAnim.OnAnimationEndListener() {
                                @Override
                                public void onAnimationEnd() {
                                    record_relativeLayout.setVisibility(View.INVISIBLE);
                                }
                            }
                    );
                    break;
                case R.id.btn_outoflocal:
                    view.animate().rotationBy(90);
                    CircularAnim.hide(baidumap_layout).triggerView(Btn_gps).go();
                    record_relativeLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_stop:
                    timer.stop();
                    little_timer.stop();
                    mRecordTime = SystemClock.elapsedRealtime();
                    onPause();
                    myalpha= (float) 0.5;
                    tv_kilometre.setAlpha(myalpha);
                    tv_num_kilometre.setAlpha(myalpha);
                    tv_num_speeder.setAlpha(myalpha);
                    tv_speeder.setAlpha(myalpha);
                    tv_timer.setAlpha(myalpha);
                    timer.setAlpha(myalpha);
                    tv_title.setText(SportType[TypeNum]+"已暂停");

                    animation_stopbtn= AnimationUtils.loadAnimation(RecordActivity.this,R.anim.scale_stopbtn_besmall);
                    animation_stopbtn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            linearLayout_stopbtn.setVisibility(View.GONE);
                            linearLayout_overbtn.setVisibility(View.VISIBLE);
                            linearLayout_comeonbtn.setVisibility(View.VISIBLE);
                            mAnimationSet=initAnimationSet(R.anim.left_button_translate1,R.anim.right_button_translate1);
                            mAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                            mAnimationSet.setAnimationListener(new myanimatinListener());
                            mAnimationSet.start();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.startAnimation(animation_stopbtn);
                    break;
                case R.id.btn_getlocal:
                    if(mll!=null&&mlocation!=null) locateAndZoom(mlocation, mll);
                    int num=points.size();
                    Log.i("getlocal",Integer.toString(num)+"个点");
                    break;
            }
        }
    }

    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }
    private void initView() {
        tv_kilometre=(TextView)findViewById(R.id.tv_kilometres);
        tv_num_kilometre=(TextView)findViewById(R.id.tv_num_kilometres);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText(SportType[TypeNum]+"中");
        tv_num_speeder=(TextView)findViewById(R.id.tv_num_speeder);
        tv_speeder=(TextView)findViewById(R.id.tv_speeder);
        tv_timer=(TextView)findViewById(R.id.tv_timer);
        timer=(Chronometer)findViewById(R.id.timer);

        record_relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout_record);
        Btn_stop=(ImageButton)findViewById(R.id.btn_stop);
        Btn_comeon=(ImageButton)findViewById(R.id.btn_comeon);
        Btn_gps=(ImageButton)findViewById(R.id.btn_gps);

        linearLayout_comeonbtn=(LinearLayout)findViewById(R.id.linearLayout_comeonbutton);
        linearLayout_overbtn=(LinearLayout)findViewById(R.id.linearLayout_overbutton);
        linearLayout_stopbtn=(LinearLayout)findViewById(R.id.linearLayout_stopbutton);

        Btn_gps.setOnClickListener(myClickListener);
        Btn_stop.setOnClickListener(myClickListener);
        Btn_comeon.setOnClickListener(myClickListener);

        mTasksView = (TasksCompletedView) findViewById(R.id.btn_over);

        mTasksView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {// 返回true的话，单击事件、长按事件不可以被触发
                    case MotionEvent.ACTION_DOWN:
                        isClick = true;
                        mCurrentProgress = 0;
                        startplay();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isClick = false;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void startplay() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent_over=new Intent(RecordActivity.this,OriginActivty.class);
                if(mCurrentProgress>=100){
                    Steps=getSteps()-Steps;
                    onResume();
                    startActivity(intent_over);
                    finish();
                }
                if (mCurrentProgress < mTotalProgress) {
                    if (isClick) {// 一直长按
                        mCurrentProgress += 5;
                        mHandler.postDelayed(this, 50);
                        mTasksView.setProgress(mCurrentProgress);
                    } else {// 中途放弃长按
                        //重置为0
                        mCurrentProgress = 0;
                        mTasksView.setProgress(mCurrentProgress);

                    }
                }
            }
        });
    }
    private AnimationSet initAnimationSet(int id1, int id2){
        AnimationSet animationSet;
        animation_comeon=AnimationUtils.loadAnimation(getApplication(),id1);
        linearLayout_comeonbtn.setAnimation(animation_comeon);
        animation_over=AnimationUtils.loadAnimation(getApplication(),id2);
        linearLayout_overbtn.setAnimation(animation_over);
        animationSet =new AnimationSet(false);
        animationSet.addAnimation(animation_comeon);
        animationSet.addAnimation(animation_over);
        animationSet.setDuration(300);
        return animationSet;
    }

    class myanimatinListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private int getSteps(){
        switch (TypeNum){
            case 0:return Integer.parseInt(PreferenceHelper.getSteps_walk(this));
            case 1:return Integer.parseInt(PreferenceHelper.getSteps_run(this));
        }
        return -1;
    }

    //百度地图
    private void initMap(){
        //baidu地图
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        baidumap_layout=(RelativeLayout) findViewById(R.id.baidumap_layout);

        mMapView = (MapView) findViewById(R.id.map);
        View child=mMapView.getChildAt(1);
        if(child!=null&&(child instanceof ImageView ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);

        tv_little_num_kilometre=(TextView)findViewById(R.id.little_kilometre);
        little_timer=(Chronometer)findViewById(R.id.little_timer);
        Btn_getlocal=(ImageButton)findViewById(R.id.btn_getlocal);
        Btn_getlocal.setOnClickListener(myClickListener);
        Btn_outoflocal=(ImageButton)findViewById(R.id.btn_outoflocal);
        Btn_outoflocal.setOnClickListener(myClickListener);

        mBaiduMap=mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

        /**
         * 添加地图缩放状态变化监听，当手动放大或缩小地图时，拿到缩放后的比例，然后获取到下次定位，
         *  给地图重新设置缩放比例，否则地图会重新回到默认的mCurrentZoom缩放比例
         */
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                mCurrentZoom = arg0.zoom;
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });

        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//只用gps定位，需要在室外定位。
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
            mBaiduMap.clear();
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
    private void initLocation(){
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
    }
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if(location==null||mMapView==null)return;

            if(isFirst) {
                //第一个点很重要，决定了轨迹的效果，gps刚开始返回的一些点精度不高，尽量选一个精度相对较高的起始点
                LatLng ll = null;

                ll = getMostAccuracyLocation(location);
                if(ll == null){
                    return;
                }
                timer.start();
                little_timer.start();
                isFirst = false;

                points.add(ll);//加入集合
                last = ll;

                //显示当前定位点，缩放地图
                locateAndZoom(location, ll);
                mll=ll;
                mlocation=location;

                //标记起点图层位置
                //MarkerOptions oStart = new MarkerOptions();// 地图标记覆盖物参数配置类
                //oStart.position(points.get(0));// 覆盖物位置点，第一个点为起点
                //oStart.icon(null);// 设置覆盖物图片
                //mBaiduMap.addOverlay(oStart); // 在地图上添加此图层

                return;//画轨迹最少得2个点，首地定位到这里就可以返回了
            }
            //从第二个点开始
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，可以设置点之间距离大于为5米才添加到集合中
            if (DistanceUtil.getDistance(last, ll) < 5&&DistanceUtil.getDistance(last,ll)>100) {
                return;
            }
            mileage+=DistanceUtil.getDistance(last,ll);
            setDistant();
            setAvgSpeed();

            points.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中

            last = ll;

            //显示当前定位点，缩放地图
            locateAndZoom(location, ll);
            mll=ll;
            mlocation=location;

            //清除上一次轨迹，避免重叠绘画
            mMapView.getMap().clear();

            //起始点图层也会被清除，重新绘画
           // MarkerOptions oStart = new MarkerOptions();
            //oStart.position(points.get(0));
            //oStart.icon(null);
            //mBaiduMap.addOverlay(oStart);

            //将points集合中的点绘制轨迹线条图层，显示在地图上
            PolylineOptions ooPolyline = new PolylineOptions().width(10)
                    .color(Color.BLUE).points(points);
            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        }
    }

    private void locateAndZoom(BDLocation location, LatLng ll) {
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        locData = new MyLocationData.Builder().accuracy(0)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        builder = new MapStatus.Builder();
        builder.target(ll).zoom(mCurrentZoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 首次定位很重要，选一个精度相对较高的起始点
     * 注意：如果一直显示gps信号弱，说明过滤的标准过高了，
     你可以将location.getRadius()>25中的过滤半径调大，比如>40，
     并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
     这里不是固定死的，你可以根据你的需求调整，如果你的轨迹刚开始效果不是很好，你可以将半径调小，两点之间距离也调小，
     gps的精度半径一般是10-50米
     */
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

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mSensorManager.unregisterListener(this);
        if (mLocationClient != null)
        {
            mLocationClient.stop();
        }
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    //获取平均速度
    private void setAvgSpeed(){
        String[] T=timer.getText().toString().split(":");
        int Seconds=Integer.parseInt(T[0])*60+Integer.parseInt(T[1]);
        if(Seconds!=0) {
            tv_speeder.setText(String.format("%.2f", mileage / Seconds));
        }
    }
    //获取公里数
    private void setDistant(){
        if(mileage>10){
            tv_num_kilometre.setText(String.format("%.2f",mileage/1000));
            tv_little_num_kilometre.setText(String.format("%.2f",mileage/1000));
        }
    }
    //获取轨迹截图
    private void getPictureofMap(){
        if(mll!=null&&mlocation!=null) locateAndZoom(mlocation, mll);
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                //处理截图相关工作
            }
        });
    }
}