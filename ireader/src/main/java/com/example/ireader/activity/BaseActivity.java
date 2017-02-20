package com.example.ireader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.ireader.R;
import com.example.ireader.common.ActivityManager;
import com.example.ireader.common.LogoutEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yajun on 2016/9/21.
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final int BASIC_PERMISSION_REQUEST_CODE = 1100;

    private CompositeSubscription mSubscription = new CompositeSubscription();


    public CompositeSubscription getSubscription() {
        return mSubscription;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initBundle(getIntent(),savedInstanceState);
        super.onCreate(savedInstanceState);
        if(getContentViewId() == 0){
            return;
        }
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        ActivityManager.getInstance().addActivity(this);
        EventBus.getDefault().register(this);

        initView();
    }

    //布局文件ID
    protected abstract int getContentViewId();

    protected void initBundle(Intent intent, Bundle savedInstanceState){}

    protected abstract void initView();

    public void initToolBar(Toolbar toolbar, boolean isBack){
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        if(isBack){
            hideSoftKeyboard();
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * hide inputMethod
     */
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    protected boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEvent event){

    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        EventBus.getDefault().unregister(this);
        mSubscription.unsubscribe();
        super.onDestroy();
    }
}
