package com.example.ireader.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.bean.ChapterDetail;
import com.example.ireader.bean.NovelChapter;
import com.example.ireader.dialog.DefaultDialogFragment;
import com.example.ireader.dialog.DialogMaker;
import com.example.ireader.service.CommonService;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yajun on 2016/10/31.
 *
 */
public class ChapterDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tvChapterTitle)
    TextView tvTitle;
    @Bind(R.id.tvChapterContent)
    TextView tvContent;

    ChapterDetail mChapterDetail;
    String bookUrl;

    String mCurrentUrl = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chapter_detail;
    }

    @Override
    protected void initView() {
        if(getIntent() == null){
            finish();
            return;
        }
        bookUrl = getIntent().getStringExtra("book_url");
        String bookName = getIntent().getStringExtra("book_name");
        NovelChapter novelChapter = (NovelChapter) getIntent().getSerializableExtra("book_end");
        toolbar.setTitle(bookName);
        initToolBar(toolbar,true);

        mCurrentUrl = bookUrl + novelChapter.getBookHref();

        loadData(mCurrentUrl);
    }

    public void loadData(String url){
        DialogMaker.showProgressDialog(getSupportFragmentManager(),null);
        CommonService.getChapterDetail(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChapterDetail>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogMaker.dismissProgressDialog();
                        DefaultDialogFragment.newInstance("错误提示","e:"+e.getMessage(),false,
                                "确定","重试",false,
                                new DefaultDialogFragment.DialogCallBack(){
                                    @Override
                                    public void onConfirm(String editMsg) {
                                        finish();
                                    }

                                    @Override
                                    public void onCancel() {
                                        loadData(mCurrentUrl);
                                    }
                                })
                                .showDialog(getSupportFragmentManager());
                    }

                    @Override
                    public void onNext(ChapterDetail chapterDetail) {
                        mChapterDetail = chapterDetail;
                        tvTitle.setText(mChapterDetail.mTitle);
                        tvContent.setText(mChapterDetail.mContent);
                        scrollView.scrollTo(0,0);
                        DialogMaker.dismissProgressDialog();
                    }
                });
    }

    @OnClick({R.id.tvChapter_0,R.id.tvChapter_1})
    public void onClickChapter(){
        finish();
    }

    @OnClick({R.id.tvChapterUp_0,R.id.tvChapterUp_1})
    public void onClickChapterUp(){
        if(mChapterDetail.chapterUp.equals("index.html")){
            finish();
        }else {
            mCurrentUrl = bookUrl + mChapterDetail.chapterUp;
            loadData(mCurrentUrl);
        }
    }

    @OnClick({R.id.tvChapterDown_0,R.id.tvChapterDown_1})
    public void onClickChapterDown(){
        if(mChapterDetail.chapterDown.equals("index.html")){
            finish();
        }else {
            mCurrentUrl = bookUrl + mChapterDetail.chapterDown;
            loadData(mCurrentUrl);
        }
    }
}
