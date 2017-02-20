package com.example.ireader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.bean.MainInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */
public class MultiItemAdapter extends BaseAdapter {


    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private List<MainInfo> mListData = new ArrayList<>();

    public List<MainInfo> getData() {
        return mListData;
    }

    public MultiItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;

        if (null != mListData) {
            //  所有分类中item的总和是ListVIew  Item的总个数
            for (MainInfo mainInfo : mListData) {
                count += mainInfo.getItemCount();
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        // 异常情况处理
        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }
        // 同一分类内，第一个元素的索引值
        int categroyFirstIndex = 0;
        for (MainInfo mainInfo : mListData) {
            int size = mainInfo.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            // item在当前分类内
            if (categoryIndex < size) {
                return  mainInfo.getItem( categoryIndex );
            }
            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            categroyFirstIndex += size;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        // 异常情况处理
        if (null == mListData || position <  0|| position > getCount()) {
            return TYPE_ITEM;
        }
        int categroyFirstIndex = 0;
        for (MainInfo mainInfo : mListData) {
            int size = mainInfo.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_CATEGORY_ITEM;
            }
            categroyFirstIndex += size;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_CATEGORY_ITEM:
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.item_list_title, null);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.listView_title_name);
                String  itemValue = (String) getItem(position);
                textView.setText( itemValue );
                break;

            case TYPE_ITEM:
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
                break;
        }

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_CATEGORY_ITEM;
    }


    private class ViewHolder {
        TextView tvTitle;
        TextView tvType;
        TextView tvAuthor;
    }
}
