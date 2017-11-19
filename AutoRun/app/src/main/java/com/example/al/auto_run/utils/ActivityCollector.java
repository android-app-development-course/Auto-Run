package com.example.al.auto_run.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/11/19.
 */

public class ActivityCollector {
    private static List<Activity> mActivities =  new ArrayList<>();

    public static void addActivity(Activity activity){
        if (!mActivities.contains(activity))
            mActivities.add(activity);
    }

    public static void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    public static void destroyAll(){
        for (int i = 0; i < mActivities.size(); i++){
            if (!mActivities.get(i).isFinishing())
                 mActivities.get(i).finish();
        }
    }
}
