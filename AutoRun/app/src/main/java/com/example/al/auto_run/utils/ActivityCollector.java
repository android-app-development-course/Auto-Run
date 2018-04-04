package com.example.al.auto_run.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by windy on 2017/11/19.
 */

public class ActivityCollector {
    private static Stack<Activity> mActivities =  new Stack<Activity>();

    public static int getSize(){
        return mActivities.size();
    }
    public static Activity currentActivity() {
        Activity activity = mActivities.peek();
        return activity;
    }
    public static void addActivity(Activity activity){
        if (!mActivities.contains(activity))
            mActivities.push(activity);
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
