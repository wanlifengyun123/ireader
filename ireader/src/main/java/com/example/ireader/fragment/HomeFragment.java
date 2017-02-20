package com.example.ireader.fragment;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ireader.R;
import com.example.ireader.activity.ChapterListActivity;
import com.example.ireader.activity.SearchActivity;
import com.example.ireader.adapter.MultiItemAdapter;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.bean.MainInfo;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.dialog.DialogMaker;
import com.example.ireader.service.CommonService;
import com.example.ireader.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.searchView)
    SearchView sv;
    @Bind(R.id.home_list_view)
    ListView listView;

    private MultiItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {

        sv.setVisibility(View.VISIBLE);
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("请输入小说名或作者...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String str) {
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String str) {
                if(TextUtils.isEmpty(str)){
                    ToastUtil.show("请输入小说名或小说作者！");
                    return true;
                }
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("QUERY_TEXT",str);
                startActivity(intent);
                return false;
            }
        });

        adapter = new MultiItemAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter.getData().size() == 0){
            loadData();
        }
    }

    @OnItemClick(R.id.home_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(adapter.isEnabled(position)){
            MainDetails mainDetails = (MainDetails) adapter.getItem(position);
            Intent intent = new Intent(getActivity(), ChapterListActivity.class);
            intent.putExtra("MainDetails",mainDetails);
            startActivity(intent);
        }
    }

    private void loadData(){
        DialogMaker.showProgressDialog(getChildFragmentManager(),null);
        getSubscription().add(CommonService.getMainInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MainInfo>>() {
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
                    public void onNext(List<MainInfo> mainInfos) {
                        adapter.getData().clear();
                        adapter.getData().addAll(mainInfos);
                        adapter.notifyDataSetChanged();
                    }
                }));
    }
}
