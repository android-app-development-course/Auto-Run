package com.example.al.auto_run.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al.auto_run.R;

import java.util.List;

/**
 * Created by yyj on 2017/11/10.
 */

public class LvAdapter extends ArrayAdapter<HistoryData> {
    public LvAdapter(@NonNull Context context, int resource,List<HistoryData> objects) {
        super(context, resource,objects);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取用户的数据
        HistoryData historyData = getItem(position);



        // 创建布局
        View oneHistoryRecord = LayoutInflater.from(getContext()).inflate(R.layout.listview_item,parent, false);

        // 获取ImageView和TextView
        ImageView athletics_img = (ImageView) oneHistoryRecord.findViewById(R.id.athletics_img);
        TextView date_tv = (TextView) oneHistoryRecord.findViewById(R.id.date_tv);
        TextView distance_tv = (TextView) oneHistoryRecord.findViewById(R.id.distance_tv);
        TextView time_tv = (TextView) oneHistoryRecord.findViewById(R.id.time_tv);

        // 根据用户数据设置ImageView和TextView的展现
        athletics_img.setImageResource(historyData.getAthImageId());
        date_tv.setText(historyData.getDate());
        distance_tv.setText(historyData.getDistance());
        time_tv.setText(historyData.getTime());

        return oneHistoryRecord;
    }
}
