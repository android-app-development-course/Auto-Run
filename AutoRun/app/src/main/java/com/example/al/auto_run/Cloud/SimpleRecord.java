package com.example.al.auto_run.Cloud;

import cn.bmob.v3.BmobObject;

/**
 * Created by yyj on 2017/12/31.
 */

public class SimpleRecord extends BmobObject {
    private MyUser username;
    private String AthleticsDate;
    private String AthleticsMonth;
    private String AthleticsType;
    private String AthleticsTime;
    private float AthleticsLength;


    public MyUser getusername() {
        return username;
    }

    public void setusername(MyUser username) {
        this.username = username;
    }

    public String getAthleticsDate() {
        return AthleticsDate;
    }

    public void setAthleticsDate(String AthleticsDate) {
        this.AthleticsDate = AthleticsDate;
    }

    public String getAthleticsMonth() {
        return AthleticsMonth;
    }

    public void setAthleticsMonth(String AthleticsMonth) {
        this.AthleticsMonth = AthleticsMonth;
    }

    public String getAthleticsType() {
        return AthleticsType;
    }

    public void setAthleticsType(String AthleticsType) {
        this.AthleticsType = AthleticsType;
    }

    public String getAthleticsTime() {
        return AthleticsTime;
    }

    public void setAthleticsTime(String AthleticsTime) {
        this.AthleticsTime = AthleticsTime;
    }

    public float getAthleticsLength() {
        return AthleticsLength;
    }

    public void setAthleticsLength(float AthleticsLength) {
        this.AthleticsLength = AthleticsLength;
    }
}