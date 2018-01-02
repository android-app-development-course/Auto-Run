package com.example.al.auto_run.Cloud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.al.auto_run.R;
import com.example.al.auto_run.activity.OriginActivty;
import com.example.al.auto_run.adapters.HistoryData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * Created by yyj on 2017/12/31.
 */

public class saveCloudData {

    private HistoryData historyData;

    public void getHistoryData(HistoryData historyData)
    {
        this.historyData=historyData;
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //从bundle中获取记录
                    String strRecordID = msg.getData().getString("strRecordID");
                    saveDetailRecord(strRecordID);
                    break;
                default:
                    break;
            }

        }
    };
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

                    Bundle record=new Bundle();
                    Message msg = new Message();
                    msg.what=0;
                    record.putString("strRecordID",s);
                    msg.setData(record);
                    mHandler.sendMessage(msg);
                }else{
                    Toast.makeText(getApplicationContext(),"保存失败："+e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void saveDetailRecord(String RecordID)
    {
        DetailRecord detailRecord=new DetailRecord();

        detailRecord.setAthleticsType(historyData.getType());
        detailRecord.setAthleticsDetail(historyData.getAthleticsDetail());
        detailRecord.setAthleticsLength(historyData.getDistance());
        detailRecord.setAthleticsTime(historyData.getTime());


       /* BmobFile bmobFile=new BmobFile(new File("/storage/emulated/0/BaiduNetdisk/2016-03-05(2).png"));
        detailRecord.setAthleticsPic(bmobFile);*/
        detailRecord.setRecordID(RecordID);

        detailRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"保存成功！"+e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
    }
}
