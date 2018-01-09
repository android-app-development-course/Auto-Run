package com.example.al.auto_run;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Al on 2017/12/30.
 */

public class PreferenceHelper {
    private static final String TAG="PreferencesHelper";

    //记录当天时间
    public static final String DataofToday="DataofToday";
    public static final String keyofToday="keyofToday";

    //记录一天的健走、跑步、骑行数据
    private static final String Steps="OneDaySteps";
    public static final String Steps_walk="steps_walk";
    public static final String Steps_run="steps_run";
    public static final String Steps_ride="steps_ride";

    //记录所有健走、跑步、骑行的里程数
    private static final String AllDistance="AllDistanceData";
    public static final String Dis_walk="dis_walk";
    public static final String Dis_run="dis_run";
    public static final String Dis_ride="dis_ride";

    //记录一周的健走、跑步、骑行数据
    public static final String DataofWeek_Walk="dataofweek_walk";
    public static final String DataofWeek_Run="dataofweek_run";
    public static final String DataofWeek_Ride="dataofweek_ride";

    public static final String Monday="2";
    public static final String Tuesday="3";
    public static final String Wednesday="4";
    public static final String Thursday="5";
    public static final String Friday="6";
    public static final String Saturday="7";
    public static final String Sunday="1";

    private static SharedPreferences getOneDaySteps(Context context){
        return context.getSharedPreferences(Steps,Context.MODE_PRIVATE);
    }

    public static void setSteps_walk(Context context,String steps){
        getOneDaySteps(context).edit().putString(Steps_walk,steps).commit();
    }
    public static void setSteps_run(Context context,String steps){
        getOneDaySteps(context).edit().putString(Steps_run,steps).commit();
    }
    public static void setSteps_ride(Context context,String steps){
        getOneDaySteps(context).edit().putString(Steps_ride,steps).commit();
    }
    public static String getSteps_walk(Context context){
        return getOneDaySteps(context).getString(Steps_walk,"0");
    }
    public static String getSteps_run(Context context){
        return getOneDaySteps(context).getString(Steps_run,"0");
    }
    public static String getSteps_ride(Context context){
        return getOneDaySteps(context).getString(Steps_ride,"0");
    }

    private static SharedPreferences getDataofWeekPreference(Context context,String name){
        return context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }
    public static void setDataofWeek(Context context,String preferencesName,String key,String value){
        getDataofWeekPreference(context,preferencesName).edit().putString(key,value).commit();
    }
    public static String getDataofWeek(Context context,String preferencesName,String key){
        return getDataofWeekPreference(context,preferencesName).getString(key,"0");
    }

    private static SharedPreferences getDataofTodayPreference(Context context){
        return context.getSharedPreferences(DataofToday,Context.MODE_PRIVATE);
    }
    public static void setDataofToday(Context context,String value){
        getDataofTodayPreference(context).edit().putString(keyofToday,value).commit();
    }
    public static String getDataofToday(Context context){
        return getDataofTodayPreference(context).getString(keyofToday,"0");
    }

    private static SharedPreferences getAllDistanceDataPreference(Context context){
        return context.getSharedPreferences(AllDistance,Context.MODE_PRIVATE);
    }
    public static void addDis_walk(Context context,float value){
        float a=getDis_walk(context)+value;
        getAllDistanceDataPreference(context).edit().putFloat(Dis_walk,a).commit();
    }
    public static void addDis_run(Context context,float value){
        float a=getDis_run(context)+value;
        getAllDistanceDataPreference(context).edit().putFloat(Dis_run,a).commit();
    }
    public static void addDis_ride(Context context,float value){
        float a=getDis_ride(context)+value;
        getAllDistanceDataPreference(context).edit().putFloat(Dis_ride,a).commit();
    }
    public static float getDis_walk(Context context){
        return getAllDistanceDataPreference(context).getFloat(Dis_walk,0);
    }
    public static float getDis_run(Context context){
        return getAllDistanceDataPreference(context).getFloat(Dis_run,0);
    }
    public static float getDis_ride(Context context){
        return getAllDistanceDataPreference(context).getFloat(Dis_ride,0);
    }
}
