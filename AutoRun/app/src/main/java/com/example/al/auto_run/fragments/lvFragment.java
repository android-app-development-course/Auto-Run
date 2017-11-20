package com.example.al.auto_run.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.al.auto_run.activity.HistoryDetailed;
import com.example.al.auto_run.adapters.HistoryData;
import com.example.al.auto_run.adapters.*;
import com.example.al.auto_run.R;


/**
 * Created by yyj on 2017/11/11.
 */

public class lvFragment extends Fragment {
    public static final String ARGS_MONTH="月";
    private int mPage;
    private Context context;


    /*public static lvFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_MONTH,page);
        lvFragment fragment = new lvFragment();
        fragment.setArguments(args);
        return fragment;
    }*/
    public void input(Context context)
    {
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mPage = getArguments().getInt(ARGS_MONTH);*/


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_listview,container,false);
        ListView listView=(ListView)view.findViewById(R.id.history_lv);
        LvAdapter lvAdapter=new LvAdapter(context,R.layout.listview_item, HistoryData.getAllHistoryRecord());
        listView.setAdapter(lvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,HistoryDetailed.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
