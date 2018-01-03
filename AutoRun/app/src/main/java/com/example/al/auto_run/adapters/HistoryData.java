package com.example.al.auto_run.adapters;

import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2017/11/10.
 */

public class HistoryData {
    private String date;
    private float distance;
    private List<String> trackViewX;
    private List<String> trackViewY;
    private int clockimageId;
    private String time;
    private String Type;
    private String AthleticsDetail;
    private String Month;
    private SimpleRecord RecordID;
    private String PicPath;



    public HistoryData(String date,String Month, float distance, String time,String type,String AthleticsDetail,
                       List<String> trackViewX,List<String> trackViewY) {
        this.Month=Month;
        this.date=date;
        this.distance=distance;
        this.time=time;
        this.Type=type;
        this.AthleticsDetail=AthleticsDetail;
        this.trackViewX=trackViewX;
        this.trackViewY=trackViewY;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public List<String> gettrackViewX() {
        return trackViewX;
    }

    public void settrackViewX(List<String> trackViewX) {
        this.trackViewX = trackViewX;
    }

    public List<String> gettrackViewY() {
        return trackViewY;
    }

    public void settrackViewY(List<String> trackViewY) {
        this.trackViewY = trackViewY;
    }
   /* public List<Double> gettrackViewX() {
        return trackViewX;
    }

    public void settrackViewX(List<Double> trackViewX) {
        this.trackViewX = trackViewX;
    }

    public List<Double> gettrackViewY() {
        return trackViewY;
    }

    public void settrackViewY(List<Double> trackViewY) {
        this.trackViewY = trackViewY;
    }*/

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}

    public String getType(){return Type;}

    public void setType(String Type){this.Type=Type;}

    public String getAthleticsDetail(){return AthleticsDetail;}

    public void setAthleticsDetail(String AthleticsDetail){this.AthleticsDetail=AthleticsDetail;}

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String PicPath) {
        this.PicPath = PicPath;
    }
}
