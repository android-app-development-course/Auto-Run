package com.example.al.auto_run.Cloud;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by yyj on 2017/12/31.
 */

public class DetailRecord extends BmobObject{
    private String RecordID;
    private String AthleticsDetail;
    private String AthleticsType;
    private String AthleticsPic;
    private List<String> trackViewX;
    private List<String> trackViewY;
    private float AthleticsLength;
    private String AthleticsTime;


    public String getRecordID() {
        return this.RecordID;
    }

    public void setRecordID(String RecordID) {
        this.RecordID = RecordID;
    }

    public String getAthleticsDetail() {
        return this.AthleticsDetail;
    }

    public void setAthleticsDetail(String AthleticsDetail) {
        this.AthleticsDetail = AthleticsDetail;
    }

    public String getAthleticsPic() {
        return this.AthleticsPic;
    }

    public void setAthleticsPic(String AthleticsPic) {
        this.AthleticsPic = AthleticsPic;
    }

    public String getAthleticsType() {
        return this.AthleticsType;
    }

    public void setAthleticsType(String AthleticsType) {
        this.AthleticsType = AthleticsType;
    }

    public float getAthleticsLength() {
        return AthleticsLength;
    }

    public void setAthleticsLength(float AthleticsLength) {
        this.AthleticsLength = AthleticsLength;
    }

    public String getAthleticsTime() {
        return this.AthleticsTime;
    }

    public void setAthleticsTime(String AthleticsTime) {
        this.AthleticsTime = AthleticsTime;
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
}
