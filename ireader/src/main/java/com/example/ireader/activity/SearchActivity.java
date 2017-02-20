package com.example.ireader.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ireader.R;
import com.example.ireader.adapter.DetailListAdapter;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.dialog.DialogMaker;
import com.example.ireader.service.CommonService;

import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/12/26.
 *
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.home_list_view)
    ListView listView;

    private DetailListAdapter adapter;

    private String mQueryText;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initToolBar(Toolbar toolbar, boolean isBack) {
        toolbar.setTitle("搜索结果");
        toolbar.setVisibility(View.VISIBLE);
        super.initToolBar(toolbar, isBack);
    }

    @Override
    protected void initView() {
        initToolBar(toolbar,true);
        mQueryText = getIntent().getStringExtra("QUERY_TEXT");
        adapter = new DetailListAdapter(this);
        listView.setAdapter(adapter);
        loadData();
    }

    private void loadData(){
        DialogMaker.showProgressDialog(getSupportFragmentManager(),null);
        getSubscription().add(CommonService.getSearchMainInfo(mQueryText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MainDetails>>() {
                    @Override
                    public void onCompleted() {
                        DialogMaker.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogMaker.dismissProgressDialog();
                        DefaultDialogFragment.newInstance("错误提示","e:"+e.getMessage(),
                                new DefaultDialogFragment.DialogCallBack());
                    }

                    @Override
                    public void onNext(List<MainDetails> mainDetailses) {
                        adapter.getData().clear();
                        adapter.getData().addAll(mainDetailses);
                        adapter.notifyDataSetChanged();
                        if(adapter.getData().size() == 0){
                            Intent intent = new Intent(SearchActivity.this, ChapterListActivity.class);
                            intent.putExtra("mQueryText",mQueryText);
                            startActivity(intent);
                            finish();
                        }
                    }
                }));
    }

    @OnItemClick(R.id.home_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(adapter.isEnabled(position)){
            MainDetails mainDetails = (MainDetails) adapter.getItem(position);
            Intent intent = new Intent(this, ChapterListActivity.class);
            intent.putExtra("MainDetails",mainDetails);
            startActivity(intent);
        }
    }
}
