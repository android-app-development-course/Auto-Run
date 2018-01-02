package com.example.al.auto_run.Cloud;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yyj on 2017/12/31.
 */

public class DetailRecord extends BmobObject{
    private String RecordID;
    private String AthleticsDetail;
    private String AthleticsType;
    private BmobFile AthleticsPic;
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

    public BmobFile getAthleticsPic() {
        return this.AthleticsPic;
    }

    public void setAthleticsPic(BmobFile AthleticsPic) {
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
}
