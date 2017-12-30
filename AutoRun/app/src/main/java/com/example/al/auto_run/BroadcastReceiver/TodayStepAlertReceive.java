package com.example.al.auto_run.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.al.auto_run.CalendarCount;
import com.example.al.auto_run.StepAlertManagerUtils;

/**
 * 0点启动app处理步数
 * Created by jiahongfei on 2017/6/18.
 */

public class TodayStepAlertReceive extends BroadcastReceiver {

    public static final String ACTION_STEP_ALERT = "action_step_alert";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_STEP_ALERT.equals(intent.getAction())) {
            CalendarCount.initData(context);

            StepAlertManagerUtils.set0SeparateAlertManager(context.getApplicationContext());

        }

    }
}
