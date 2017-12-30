package com.example.al.auto_run;

/**
 * Created by Al on 2017/12/30.
 */

public class RelatedData {
    // 公里计算公式
    public static String getDistanceByStep(int steps) {
        return String.format("%.2f", steps * 0.6f / 1000);
    }

    // 千卡路里计算公式
    public static String getCalorieByStep(int steps) {
        return String.format("%.1f", steps * 0.6f * 60 * 1.036f / 1000);
    }
}
