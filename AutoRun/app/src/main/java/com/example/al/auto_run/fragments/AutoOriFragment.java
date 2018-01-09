package com.example.al.auto_run.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;
import com.example.al.auto_run.activity.HistoryRecordActivity;

/**
 * Created by windy on 2017/11/11.
 */

public class AutoOriFragment extends Fragment {
    public TextView tv_run;
    public TextView tv_walk;
    public TextView tv_ride;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.origin_auto, container, false);
        tv_ride=view.findViewById(R.id.txt_view_count_ride);
        tv_run=view.findViewById(R.id.txt_view_count_run);
        tv_walk=view.findViewById(R.id.txt_view_count_walk);

        Change();
        setOnClick();
        return view;
    }

    public void Change(){
        //Log.i("walk",mySharedPreferences_walk.getString("steps_walk","0"));
        tv_run.setText(PreferenceHelper.getSteps_run(getContext()));
        tv_walk.setText(PreferenceHelper.getSteps_walk(getContext()));
        String dis=PreferenceHelper.getSteps_ride(getContext());
        float a=Float.parseFloat(dis);
        a=(float)(Math.round(a*100)/100);
        tv_ride.setText(String.valueOf(a));
    }

    private void setOnClick(){
        tv_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryRecordActivity.actionStart(getContext());
            }
        });

        tv_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryRecordActivity.actionStart(getContext());
            }
        });

        tv_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryRecordActivity.actionStart(getContext());
            }
        });
    }
}
