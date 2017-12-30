package com.example.al.auto_run.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.al.auto_run.PreferenceHelper;
import com.example.al.auto_run.R;

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

        this.Change();
        return view;
    }

    public void Change(){
        //Log.i("walk",mySharedPreferences_walk.getString("steps_walk","0"));
        tv_run.setText(PreferenceHelper.getSteps_run(getContext()));
        tv_walk.setText(PreferenceHelper.getSteps_walk(getContext()));
        tv_ride.setText(PreferenceHelper.getSteps_ride(getContext()));
    }
}
