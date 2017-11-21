package com.example.al.auto_run.activity;

/**
 * Created by Al on 2017/11/19.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.al.auto_run.MainActivity;
import com.example.al.auto_run.R;
import com.example.al.auto_run.customanim.CircularAnim;
import com.example.al.auto_run.customview.TasksCompletedView;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TextView tv_kilometre;
    TextView tv_title;
    TextView tv_num_kilometre;
    TextView tv_timer;
    TextView tv_num_speeder;
    TextView tv_speeder;
    Chronometer timer;

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

    private TasksCompletedView mTasksView;
    private int mTotalProgress;
    private int mCurrentProgress;
    private Handler mHandler = new Handler();
    private boolean isClick;

    private TextureMapView mMapView;
    public BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new RecordActivity.MyLocationListener();
    private boolean isFirst=true;
    private boolean isRequest=false;
    Button Btn_getlocal;
    Button Btn_outoflocal;
    RelativeLayout baidumap_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_record);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortry1main2));

        //baidu地图
        baidumap_layout=(RelativeLayout) findViewById(R.id.baidumap_layout);

        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
        View child=mMapView.getChildAt(1);
        if(child!=null&&(child instanceof ImageView ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);

        Btn_getlocal=(Button)findViewById(R.id.btn_getlocal);
        Btn_getlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRequest=true;
            }
        });
        Btn_outoflocal=(Button)findViewById(R.id.btn_outoflocal);
        Btn_outoflocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.animate().rotationBy(90);

                CircularAnim.hide(baidumap_layout).triggerView(Btn_gps).go();
                record_relativeLayout.setVisibility(View.VISIBLE);
            }
        });

        mBaiduMap=mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);  //注册监听函数
        initLocation();
        startLocation();
        mLocationClient.start();

        //记录界面
        tv_kilometre=(TextView)findViewById(R.id.tv_kilometres);
        tv_num_kilometre=(TextView)findViewById(R.id.tv_num_kilometres);
        tv_title=(TextView)findViewById(R.id.tv_title);
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

        Btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               view.animate().rotationBy(90);

               CircularAnim.show(baidumap_layout).triggerView(Btn_gps).go(
                       new CircularAnim.OnAnimationEndListener() {
                           @Override
                           public void onAnimationEnd() {
                               record_relativeLayout.setVisibility(View.INVISIBLE);
                           }
                       }
               );
            }
        });
        Btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float myalpha= (float) 0.5;
                tv_kilometre.setAlpha(myalpha);
                tv_num_kilometre.setAlpha(myalpha);
                tv_num_speeder.setAlpha(myalpha);
                tv_speeder.setAlpha(myalpha);
                tv_timer.setAlpha(myalpha);
                timer.setAlpha(myalpha);
                tv_title.setText("跑步已暂停");

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
            }
        });

        Btn_comeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                float myalpha= (float) 1;
                tv_kilometre.setAlpha(myalpha);
                tv_num_kilometre.setAlpha(myalpha);
                tv_num_speeder.setAlpha(myalpha);
                tv_speeder.setAlpha(myalpha);
                tv_timer.setAlpha(myalpha);
                timer.setAlpha(myalpha);
                tv_title.setText("跑步中");

                //mAnimationSet=initAnimationSet(R.anim.left_button_translate2,R.anim.right_button_translate2);
                //mAnimationSet.setInterpolator(new LinearInterpolator());
                //mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
                  //  @Override
                    //public void onAnimationStart(Animation animation) {

                    //}

                    //@Override
                    //public void onAnimationEnd(Animation animation) {
                        linearLayout_comeonbtn.setVisibility(View.GONE);
                        linearLayout_overbtn.setVisibility(View.GONE);
                        linearLayout_stopbtn.setVisibility(View.VISIBLE);
                        animation_stopbtn=AnimationUtils.loadAnimation(getApplication(),R.anim.scale_stopbtn_bebig);
                        animation_stopbtn.setAnimationListener(new myanimatinListener());
                        linearLayout_stopbtn.startAnimation(animation_stopbtn);
                    //}

//                    @Override
  //                  public void onAnimationRepeat(Animation animation) {

//                    }
  //              });

            }
        });

        initVariable();
        initView();
    }

    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }
    private void initView() {
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
                if(mCurrentProgress>=100)startActivity(intent_over);
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

    //百度地图
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
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());

            if(isFirst||isRequest) {
                MyLocationData locData = new MyLocationData.Builder().latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16.0f);
                mBaiduMap.animateMapStatus(u);
                isFirst=false;
                isRequest=false;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
