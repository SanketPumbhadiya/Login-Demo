package com.example.demo_login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo_login.model.ModelLatLongTime;

import java.util.ArrayList;

public class LatLongTimeAdapter extends BaseAdapter {

    ArrayList<ModelLatLongTime> original;
    LayoutInflater inflater;
    Context context;

    public LatLongTimeAdapter(Context context, ArrayList<ModelLatLongTime> model, LayoutInflater inflater) {
        this.context = context;
        this.original = model;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return original.size();
    }

    @Override
    public Object getItem(int position) {
        return original.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.customlistlatlongtime, null);
            viewHolder.tvLatitude = convertView.findViewById(R.id.tv_latitude);
            viewHolder.tvLongitude = convertView.findViewById(R.id.tv_longitude);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ModelLatLongTime model = original.get(position);

        viewHolder.tvLatitude.setText(String.valueOf(model.getLatitude()));
        viewHolder.tvLongitude.setText(String.valueOf(model.getLongitude()));
        viewHolder.tvTime.setText(model.getTime());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvLatitude, tvLongitude, tvTime;

    }
}