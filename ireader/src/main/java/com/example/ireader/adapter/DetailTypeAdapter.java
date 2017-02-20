package com.example.ireader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.bean.DetailType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class DetailTypeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<DetailType> data = new ArrayList<>();

    public List<DetailType> getData() {
        return data;
    }

    public DetailTypeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_detail_type, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.detail_title);
        DetailType itemValue = data.get(position);
        textView.setText( itemValue.typeName );
        return convertView;
    }
}
