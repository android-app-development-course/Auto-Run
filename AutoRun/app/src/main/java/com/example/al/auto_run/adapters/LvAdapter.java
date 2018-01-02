package com.example.al.auto_run.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al.auto_run.Cloud.SimpleRecord;
import com.example.al.auto_run.R;

import java.util.List;

/**
 * Created by yyj on 2017/11/10.
 */

public class LvAdapter extends ArrayAdapter<SimpleRecord> {
    public LvAdapter(@NonNull Context context, int resource,List<SimpleRecord> objects) {
        super(context, resource,objects);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取用户的数据
        SimpleRecord simpleRecord = getItem(position);


        // 创建布局
        View oneHistoryRecord = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);

        // 获取ImageView和TextView
        ImageView athletics_img = (ImageView) oneHistoryRecord.findViewById(R.id.athletics_img);
        TextView date_tv = (TextView) oneHistoryRecord.findViewById(R.id.date_tv);
        TextView distance_tv = (TextView) oneHistoryRecord.findViewById(R.id.distance_tv);
        TextView time_tv = (TextView) oneHistoryRecord.findViewById(R.id.time_tv);

        String TempType = simpleRecord.getAthleticsType();

        int TempPic;
        switch (TempType) {
            case "骑行":
                TempPic = R.drawable.ic_directions_bike_black_24dp;
                break;
            case "健走":
                TempPic = R.drawable.ic_directions_walk_black_24dp;
                break;
            case "跑步":
                TempPic = R.drawable.ic_directions_run_black_24dp;
                break;
            default:
                TempPic = R.drawable.ic_directions_walk_black_24dp;
                break;
        }
        /*else
        {
            TempPic=R.drawable.ic_directions_run_black_24dp;
        }*/

        String strDateAndType = simpleRecord.getAthleticsDate() + " " + TempType;
        // 根据用户数据设置ImageView和TextView的展现
        athletics_img.setImageResource(TempPic);
        date_tv.setText(strDateAndType);
        distance_tv.setText(Float.toString(simpleRecord.getAthleticsLength()));
        time_tv.setText(simpleRecord.getAthleticsTime());
        return oneHistoryRecord;
    }
}
