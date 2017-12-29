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

import com.example.al.auto_run.R;

/**
 * Created by windy on 2017/11/11.
 */

public class AutoOriFragment extends Fragment {
    public TextView tv_run;
    public TextView tv_walk;
    public TextView tv_ride;

    private SharedPreferences mySharedPreferences_run;
    private SharedPreferences mySharedPreferences_walk;
    private SharedPreferences mySharedPreferences_ride;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.origin_auto, container, false);
        tv_ride=view.findViewById(R.id.txt_view_count_ride);
        tv_run=view.findViewById(R.id.txt_view_count_run);
        tv_walk=view.findViewById(R.id.txt_view_count_walk);
        mySharedPreferences_run = this.getActivity().getSharedPreferences("relevant_data_run", Activity.MODE_PRIVATE);
        mySharedPreferences_walk =this.getActivity().getSharedPreferences("relevant_data_walk",Activity.MODE_PRIVATE);
        mySharedPreferences_ride=this.getActivity().getSharedPreferences("relevant_data_ride",Activity.MODE_PRIVATE);

        this.Change();
        return view;
    }

    public void Change(){
        //Log.i("walk",mySharedPreferences_walk.getString("steps_walk","0"));
        tv_run.setText(mySharedPreferences_run.getString("steps_run","0"));
        tv_walk.setText(mySharedPreferences_walk.getString("steps_walk","0"));
        tv_ride.setText(mySharedPreferences_ride.getString("steps_ride","0"));
    }
}
