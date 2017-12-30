package com.example.al.auto_run;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Al on 2017/12/30.
 */

public class CalendarCount {

    public static boolean isToday(String Todaytime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String Today = DateUtils.dateFormat(calendar.getTimeInMillis(),"yyyy-MM-dd");
        if(Today.equals(Todaytime))return true;
        else return false;
    }

    public static boolean isDelectAll(String Todaytime){
        String[] time=Todaytime.split("-");
        int year=Integer.parseInt(time[0]);
        int month=Integer.parseInt(time[1]);
        int day=Integer.parseInt(time[2]);
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        int distantofsaturday=Calendar.SATURDAY-calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE,distantofsaturday);
        Calendar Today = Calendar.getInstance();
        Today.setTimeInMillis(System.currentTimeMillis());
        if(calendar.after(Today))return false;
        else return true;
    }

    public static void initData(Context context) {
        String Todaytime=PreferenceHelper.getDataofToday(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if(Todaytime.equals("0")){
            Todaytime = DateUtils.dateFormat(calendar.getTimeInMillis(),"yyyy-MM-dd");
            PreferenceHelper.setDataofToday(context,Todaytime);
            initWeekData(context);
            return;
        }
        else {
            if(CalendarCount.isToday(Todaytime))return;
            else {
                if(CalendarCount.isDelectAll(Todaytime))initWeekData(context);//清除一周数据
                //写入数据
                String dayofweek=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
                PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Walk,
                        dayofweek,PreferenceHelper.getSteps_walk(context));
                PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Run,
                        dayofweek,PreferenceHelper.getSteps_run(context));
                PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Ride,
                        dayofweek,PreferenceHelper.getSteps_ride(context));
                setData0(context);
            }
        }
    }

    //清除一周数据
    private static void initWeekData(Context context){
        for(int i=1;i<8;i++){
            String key=String.valueOf(i);
            PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Walk,
                    key,"0");
            PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Run,
                    key,"0");
            PreferenceHelper.setDataofWeek(context,PreferenceHelper.DataofWeek_Ride,
                    key,"0");
        }
    }
    //清除一天数据
    private static void setData0(Context context){
        PreferenceHelper.setSteps_walk(context,"0");
        PreferenceHelper.setSteps_run(context,"0");
        PreferenceHelper.setSteps_ride(context,"0");
    }
}
