package com.example.al.auto_run.Cloud;

import android.util.Log;
import android.widget.Toast;

import com.example.al.auto_run.adapters.HistoryData;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static cn.bmob.v3.BmobRealTimeData.TAG;


/**
 * Created by yyj on 2017/12/31.
 */

public class saveCloudData {

    private HistoryData historyData;

    public void getHistoryData(HistoryData historyData)
    {
        this.historyData=historyData;
    }

    public void saveSimpleRecord()
    {

        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        SimpleRecord simpleRecord=new SimpleRecord();



        simpleRecord.setAthleticsDate(historyData.getDate());
        simpleRecord.setAthleticsMonth(historyData.getMonth());
        simpleRecord.setAthleticsLength(historyData.getDistance());
        simpleRecord.setAthleticsTime(historyData.getTime());
        simpleRecord.setAthleticsType(historyData.getType());

        simpleRecord.setusername(user);


        simpleRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){

                    final DetailRecord detailRecord=new DetailRecord();
                    detailRecord.setRecordID(s);

                    detailRecord.setAthleticsType(historyData.getType());
                    detailRecord.setAthleticsDetail(historyData.getAthleticsDetail());
                    detailRecord.setAthleticsLength(historyData.getDistance());
                    detailRecord.setAthleticsTime(historyData.getTime());
                    detailRecord.addAll("trackViewX",historyData.gettrackViewX());
                    detailRecord.addAll("trackViewY",historyData.gettrackViewY());

                    detailRecord.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){

                                Toast.makeText(getApplicationContext(),"保存成功！"+e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d(TAG,  e.getMessage());

                            }
                        }
                    });



                }else{
                    Toast.makeText(getApplicationContext(),"保存失败："+e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
