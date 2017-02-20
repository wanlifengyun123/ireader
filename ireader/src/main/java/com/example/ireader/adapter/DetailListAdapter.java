package com.example.ireader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.bean.MainDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class DetailListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MainDetails> data = new ArrayList<>();

    public List<MainDetails> getData() {
        return data;
    }

    public DetailListAdapter (Context context) {
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
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_list_content, null);
            viewHolder = new ViewHolder();
            viewHolder.tvType   = (TextView) convertView.findViewById(R.id.listView_content_type);
            viewHolder.tvTitle  = (TextView) convertView.findViewById(R.id.listView_content_name);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.listView_content_author);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 绑定数据
        MainDetails item = (MainDetails) getItem(position);
        viewHolder.tvType.setText(item.getTypeName());
        viewHolder.tvTitle.setText(item.getTitleName());
        viewHolder.tvAuthor.setText(item.getAuthor());
        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvType;
        TextView tvAuthor;
    }
}
