package com.example.al.auto_run.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.al.auto_run.Cloud.MyUser;
import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.activity.HistoryDetailed;
import com.example.al.auto_run.adapters.HistoryData;
import com.example.al.auto_run.adapters.*;
import com.example.al.auto_run.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * Created by yyj on 2017/11/11.
 */

public class lvFragment extends Fragment {
    public static final String ARGS_MONTH="月";
    private int mPage;
    private Context context;
    private String month;
    private ArrayList<String> DetailString;
    /*List<HistoryData> historyDataList;*/

    View view;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //从bundle中获取记录
                    ArrayList<String> DetailString = msg.getData().getStringArrayList("DetailString");
                    getDetail(DetailString);
                    break;
                default:
                    break;
            }

        }
    };
    /*public static lvFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_MONTH,page);
        lvFragment fragment = new lvFragment();
        fragment.setArguments(args);
        return fragment;
    }*/
    public void input(Context context,String month)
    {
        this.context=context;
        this.month=month;
    }
    public void getDetail(ArrayList<String> DetailString)
    {
        this.DetailString=DetailString;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mPage = getArguments().getInt(ARGS_MONTH);*/


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.history_listview, container, false);
            final ListView listView = (ListView) view.findViewById(R.id.history_lv);

            /*******************************************************************************************************/
            /*historyDataList=HistoryData.getAllHistoryRecord();*/

            MyUser user= BmobUser.getCurrentUser(MyUser.class);

            BmobQuery<SimpleRecord> query=new BmobQuery<SimpleRecord>();

            query.addWhereEqualTo("username",user);

            query.addWhereEqualTo("AthleticsMonth",month);


            query.findObjects(new FindListener<SimpleRecord>() {
                @Override
                public void done(List<SimpleRecord> list, BmobException e) {
                    if(e==null){
                        Toast.makeText(getApplicationContext(),"共查询到"+list.size()+"条数据", Toast.LENGTH_SHORT).show();
                        LvAdapter lvAdapter = new LvAdapter(context, R.layout.listview_item, list);
                        listView.setAdapter(lvAdapter);

                        ArrayList<String> DetailString=new ArrayList<String>();
                        for(int i=0;i<list.size();i++)
                        {
                            DetailString.add(list.get(i).getObjectId());
                        }
                        Bundle month=new Bundle();
                        Message msg = new Message();
                        msg.what=0;
                        month.putStringArrayList("DetailString",DetailString);
                        msg.setData(month);
                        mHandler.sendMessage(msg);
                    }else{
                        Toast.makeText(getApplicationContext(),"未获取到数据,请确定是否有保存运动记录", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            /*GetCloudData getCloudData=new GetCloudData();
            getCloudData.loadSimpleRecord();*/

            /*LvAdapter lvAdapter = new LvAdapter(context, R.layout.listview_item, HistoryData.getAllHistoryRecord());
            listView.setAdapter(lvAdapter);*/
            /*****************************************************************************************************/
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, HistoryDetailed.class);
                    intent.putExtra("ID",DetailString.get(position).trim());
                    startActivity(intent);

                }
            });
        }
        return view;
    }
}
