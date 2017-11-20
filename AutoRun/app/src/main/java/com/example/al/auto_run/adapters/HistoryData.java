package com.example.al.auto_run.adapters;

import com.example.al.auto_run.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2017/11/10.
 */

public class HistoryData {
    private String date;
    private String distance;
    private int imgId1;
    private int clockimageId;
    private String time;

    public HistoryData(int imgId1, String date, String distance, String time) {
        this.imgId1 = imgId1;
        this.date=date;
        this.distance=distance;
        this.time=time;
    }

    public static List<HistoryData> getAllHistoryRecord(){
        List<HistoryData> historyDataList =new ArrayList<HistoryData>();
        historyDataList.add(new HistoryData(R.drawable.athimg, "17日上午 健走","15","30'"));
        return historyDataList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAthImageId() {
        return imgId1;
    }

    public void setAthImageId(int imgId1) {
        this.imgId1 = imgId1;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}
}
