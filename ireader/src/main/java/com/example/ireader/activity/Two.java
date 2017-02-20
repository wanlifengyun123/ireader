package com.example.ireader.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ireader.R;
import com.example.ireader.adapter.DetailListAdapter;
import com.example.ireader.adapter.DetailTypeAdapter;
import com.example.ireader.bean.DetailModule;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.service.CommonService;
import com.example.ireader.utils.ToastUtil;
import com.example.ireader.view.pullview.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/12/21.
 *
 */

public class Two extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private PagerAdapter pagerAdapter;
    private List<View> views = new ArrayList<View>();
    private List<String> mDatas;

    private ListView listView;

    @Override
    protected int getContentViewId() {
        return R.layout.two;
    }

    @Override
    public void initToolBar(Toolbar toolbar, boolean isBack) {
        toolbar.setTitle("测试页面");
        super.initToolBar(toolbar, isBack);

    }

    @Override
    protected void initView() {

        initToolBar(toolbar,true);

        mDatas = new ArrayList<String>();
        for(int i = 1;i<=10;i++) {
            mDatas.add("AndroidGet"+i);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < 4; i++) {
            View inflate = layoutInflater.inflate(R.layout.two_list1, null);
            listView = (ListView) inflate.findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDatas));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastUtil.show(mDatas.get(position));
                }
            });
            views.add(inflate);
        }

        pagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view= views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };
        viewPager.setAdapter(pagerAdapter);

    }


}
