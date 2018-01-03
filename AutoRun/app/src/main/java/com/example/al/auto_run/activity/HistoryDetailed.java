package com.example.al.auto_run.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.al.auto_run.Cloud.DetailRecord;
import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HistoryDetailed extends AppCompatActivity {
    private MapView mMapView;
    public BaiduMap mBaiduMap;
    RelativeLayout baidumap_layout;
    private List<String> doubleLatitude=new ArrayList<String>();
    private List<String> doubleLongitude=new ArrayList<String>();
    List<LatLng> points = new ArrayList<LatLng>();//位置点集合
    BitmapDescriptor startBD = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_me_history_startpoint);
    BitmapDescriptor finishBD = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_me_history_finishpoint);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_history_detailed);
        ImageButton history_detailed_reback = (ImageButton) findViewById(R.id.history_detailed_reback);
        final TextView history_detailed_distance = findViewById(R.id.history_detailed_distance);
        final TextView history_detailed_stepCount = findViewById(R.id.history_detailed_stepCount);
        final TextView history_detailed_time = findViewById(R.id.history_detailed_time);
        final TextView history_detailed_dateAndType = findViewById(R.id.history_detailed_dateAndType);


        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");

    /* ********************************************************************************/


        BmobQuery<DetailRecord> query = new BmobQuery<DetailRecord>();

        query.addWhereEqualTo("RecordID", ID);

        query.findObjects(new FindListener<DetailRecord>() {
            @Override
            public void done(List<DetailRecord> list, BmobException e) {
                if (e == null) {
                    history_detailed_distance.setText(Float.toString(list.get(0).getAthleticsLength()));
                    history_detailed_stepCount.setText(list.get(0).getAthleticsDetail());
                    history_detailed_dateAndType.setText(list.get(0).getAthleticsType());
                    history_detailed_time.setText(list.get(0).getAthleticsTime());
                    doubleLatitude=list.get(0).gettrackViewX();
                    doubleLongitude=list.get(0).gettrackViewY();
                    initMap();
                    initPoints();
                    initLocation();
                    PaintTrack();
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });


        /* *********************************************************************************/


        history_detailed_reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDetailed.this.finish();
            }
        });
    }
    private void initMap(){
        //baidu地图
        mMapView = (MapView) findViewById(R.id.Staticmap);
        View child=mMapView.getChildAt(1);
        if(child!=null&&(child instanceof ImageView ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);

        mBaiduMap=mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));
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

            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    private void initPoints(){
        int length=doubleLatitude.size();
        for(int i=0;i<length;i++){
            double Latitude=Double.parseDouble(doubleLatitude.get(i));
            double Longitude=Double.parseDouble(doubleLongitude.get(i));
            LatLng ll=new LatLng(Latitude,Longitude);
            points.add(ll);
        }
    }
    private void initLocation(){
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(points.get(0)).zoom(18f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    private void PaintTrack(){
        MarkerOptions Marker = new MarkerOptions();
        Marker.position(points.get(0));
        Marker.icon(startBD);
        mBaiduMap.addOverlay(Marker);
        Marker.position(points.get(points.size()-1));
        Marker.icon(finishBD);
        mBaiduMap.addOverlay(Marker);

        PolylineOptions ooPolyline = new PolylineOptions().width(10)
                .color(Color.BLUE).points(points);
        Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }
}

