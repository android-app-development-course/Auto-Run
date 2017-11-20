package com.example.al.auto_run.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.al.auto_run.R;
import com.example.al.auto_run.activity.CountdownActivity;
import com.example.al.auto_run.activity.RecordActivity;

import at.markushi.ui.CircleButton;

/**
 * Created by windy on 2017/11/11.
 */

public class RunOriFragment extends Fragment {
    Button Btn_go;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.origin_main, container, false);
        Btn_go = view.findViewById(R.id.img_button_go);
        Btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CountdownActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions compat = ActivityOptions.makeScaleUpAnimation(Btn_go,Btn_go.getWidth()/2,Btn_go.getHeight()/2,0,0);
                    ActivityCompat.startActivity(getActivity(), new Intent(getActivity(), CountdownActivity.class),
                            compat.toBundle());
                    //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                      //      (getActivity(),Btn_go,
                        //            "shareName1").toBundle());
                }
            }
        });
        return view;
    }
}
