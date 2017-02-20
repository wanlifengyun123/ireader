package com.example.ireader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.adapter.ChapterAdapter;
import com.example.ireader.bean.MainDetails;
import com.example.ireader.bean.NovelChapter;
import com.example.ireader.bean.NovelModule;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.dialog.DialogMaker;
import com.example.ireader.service.CommonService;
import com.example.ireader.view.MyListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/10/28.
 *
 */
public class ChapterListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tvNovelTitle)
    TextView tvNovelTitle;
    @Bind(R.id.tvNovelAuthor)
    TextView tvNovelAuthor;
    @Bind(R.id.tvChapter)
    TextView tvChapter;

    @Bind(R.id.tvChapterState)
    TextView tvChapterState;
    @Bind(R.id.tvChapterNumber)
    TextView tvChapterNumber;
    @Bind(R.id.tvChapterDate)
    TextView tvChapterDate;
    @Bind(R.id.tvChapterContent)
    TextView tvChapterContent;

    @Bind(R.id.list_view)
    MyListView listView;

    private MainDetails details;

    private ChapterAdapter adapter;

    List<NovelChapter> chapterList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chapter;
    }

    @Override
    protected void initBundle(Intent intent, Bundle savedInstanceState) {
        super.initBundle(intent, savedInstanceState);
        details = (MainDetails) intent.getSerializableExtra("MainDetails");
    }

    @Override
    protected void initView() {
        initToolBar(toolbar,true);

        adapter = new ChapterAdapter(this);
        listView.setAdapter(adapter);

        loadData();

    }

    @Override
    public void initToolBar(Toolbar toolbar, boolean isBack) {
        toolbar.setTitle(details.getTitleName());
        super.initToolBar(toolbar, isBack);
    }

    public void loadData(){
        DialogMaker.showProgressDialog(getSupportFragmentManager(),null);
        CommonService.getNovelModule(details.getHrefStr())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NovelModule>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogMaker.dismissProgressDialog();
                        DefaultDialogFragment.newInstance("错误提示","e:"+e.getMessage(),
                                new DefaultDialogFragment.DialogCallBack(){
                                    @Override
                                    public void onConfirm(String editMsg) {
                                        finish();
                                    }
                                })
                                .showDialog(getSupportFragmentManager());
                    }

                    @Override
                    public void onNext(NovelModule novelModule) {
                        tvNovelTitle.setText(novelModule.getNovelName());
                        tvNovelAuthor.setText(novelModule.getAuthor());
                        tvChapter.setText(novelModule.getLatestchapter());
                        tvChapterState.setText(novelModule.getNovelState());
                        tvChapterNumber.setText(novelModule.getWordNumber());
                        tvChapterDate.setText(novelModule.getUpdateDate());
                        tvChapterContent.setText(novelModule.getContentDetail());

                        chapterList = novelModule.getChapterList();

                        adapter.getData().clear();
                        adapter.getData().addAll(novelModule.getChapterList());
                        adapter.notifyDataSetChanged();
                        scrollView.scrollTo(0,0);
                        DialogMaker.dismissProgressDialog();
                    }
                });
    }

    @OnItemClick(R.id.list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NovelChapter chapter = adapter.getData().get(position);
        Intent intent = new Intent(ChapterListActivity.this,ChapterDetailActivity.class);
        intent.putExtra("book_url",details.getHrefStr());
        intent.putExtra("book_end",chapter);
        intent.putExtra("book_name",tvNovelTitle.getText().toString().trim());
        startActivity(intent);
    }
}
