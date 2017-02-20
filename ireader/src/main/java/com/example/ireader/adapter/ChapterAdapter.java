package com.example.ireader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.bean.DetailType;
import com.example.ireader.bean.NovelChapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class ChapterAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<NovelChapter> data = new ArrayList<>();

    public List<NovelChapter> getData() {
        return data;
    }

    public ChapterAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_chapter_list, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.chapter_title);
        NovelChapter itemValue = data.get(position);
        textView.setText( itemValue.getBookTitle() );
        return convertView;
    }
}
