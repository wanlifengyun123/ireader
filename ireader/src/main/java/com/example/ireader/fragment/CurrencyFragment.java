package com.example.ireader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ireader.R;
import com.example.ireader.activity.ChapterListActivity;
import com.example.ireader.adapter.DetailListAdapter;
import com.example.ireader.adapter.DetailTypeAdapter;
import com.example.ireader.bean.DetailModule;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.dialog.DialogMaker;
import com.example.ireader.service.CommonService;
import com.example.ireader.view.MyGridView;
import com.example.ireader.view.pullview.PullToRefreshView;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/10/27.
 *
 */
public class CurrencyFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterLoadListener{


    @Bind(R.id.mPullRefreshView)
    PullToRefreshView mPullRefreshView;
    @Bind(R.id.list_view)
    ListView listView;

    private DetailListAdapter adapter;
    private DetailTypeAdapter typeAdapter;
    private int page = 1;
    private int mCurrentIndex;

    private boolean isGridRefresh = false;

    public static CurrencyFragment getInstance(int position){
        CurrencyFragment f = new CurrencyFragment();
        Bundle b = new Bundle();
        b.putInt("CURRENT_INDEX",position);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_currency;
    }

    @Override
    protected void initView(View view) {
        mCurrentIndex = getArguments().getInt("CURRENT_INDEX");
        // 设置监听器
        mPullRefreshView.setOnHeaderRefreshListener(this);
        mPullRefreshView.setOnFooterLoadListener(this);

        View headerView = getActivity().getLayoutInflater().inflate(R.layout.item_currency_header,null,false);
        MyGridView gridView = (MyGridView) headerView.findViewById(R.id.gridView);
        typeAdapter = new DetailTypeAdapter(getActivity());
        gridView.setAdapter(typeAdapter);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        listView.addHeaderView(headerView);

        adapter = new DetailListAdapter(getActivity());
        listView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refreshGridData(typeAdapter.getData().get(position).typeHref);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter.getData().size() == 0){
            mPullRefreshView.headerRefreshing();
        }
    }

    private void refreshData(final int page){
        getSubscription().add(CommonService.getDetailModule(mCurrentIndex,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailModule>() {
                    @Override
                    public void onCompleted() {
                        stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh();
                        DefaultDialogFragment.newInstance("错误提示","e:"+e.getMessage(),
                                new DefaultDialogFragment.DialogCallBack());
                    }

                    @Override
                    public void onNext(DetailModule detailModule) {
                        if(detailModule.getMainDetailsList() == null || detailModule.getMainDetailsList().size() == 0){
                            return;
                        }
                        if(page == 1){
                            adapter.getData().clear();
                            typeAdapter.getData().clear();
                            typeAdapter.getData().addAll(detailModule.getDetailTypes());
                            typeAdapter.notifyDataSetChanged();
                        }
                        adapter.getData().addAll(detailModule.getMainDetailsList());
                        adapter.notifyDataSetChanged();
                    }
                }));
    }

    private void refreshGridData(String hrefStr){
        getSubscription().add(CommonService.getDetailModule2(hrefStr,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailModule>() {
                    @Override
                    public void onCompleted() {
                        stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh();
                        DefaultDialogFragment.newInstance("错误提示","e:"+e.getMessage(),
                                new DefaultDialogFragment.DialogCallBack());
                    }

                    @Override
                    public void onNext(DetailModule detailModule) {
                        if(detailModule.getMainDetailsList() == null || detailModule.getMainDetailsList().size() == 0){
                            return;
                        }
                        if(page == 1){
                            adapter.getData().clear();
                            typeAdapter.getData().clear();
                            typeAdapter.getData().addAll(detailModule.getDetailTypes());
                            typeAdapter.notifyDataSetChanged();
                        }
                        adapter.getData().addAll(detailModule.getMainDetailsList());
                        adapter.notifyDataSetChanged();
                        isGridRefresh = true;
                    }
                }));
    }

    @OnItemClick(R.id.list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainDetails mainDetails = adapter.getData().get(position - 1);
        Intent intent = new Intent(getActivity(), ChapterListActivity.class);
        intent.putExtra("MainDetails",mainDetails);
        startActivity(intent);
    }

    @Override
    public void onFooterLoad(PullToRefreshView view) {
        page++;
        refreshData(page);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 1;
        isGridRefresh = false;
        refreshData(page);
    }

    public void stopRefresh(){
        mPullRefreshView.onFooterLoadFinish();
        mPullRefreshView.onHeaderRefreshFinish();
    }
}
