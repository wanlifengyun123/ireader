package com.example.ireader.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.example.ireader.ICalculateService;
import com.example.ireader.OrderAddPopupWindow;
import com.example.ireader.R;
import com.example.ireader.common.LogoutEvent;
import com.example.ireader.common.TestEvent;
import com.example.ireader.utils.ToastUtil;
import com.example.ireader.view.ShadowView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by yajun on 2016/12/9.
 *
 */
public class Test extends BaseActivity {

    @Bind(R.id.textbook)
    TextView textView;
    @Bind(R.id.textbook3)
    ShadowView shadowView;

    private ICalculateService mCalculateService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCalculateService = ICalculateService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCalculateService = null;
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.test_activity;
    }

    @Override
    protected void initView() {

        Bundle args = new Bundle();
        Intent intent = new Intent("com.example.ireader.CalculateService");
        intent.putExtras(args);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);


        findViewById(R.id.textbook2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double answer =  mCalculateService.doCalculate(1, 2);
                    ToastUtil.show("计算结果：" + answer);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    ToastUtil.show("计算失误！" );
                }
            }
        });
        shadowView.setShadowClickListener(new ShadowView.ShadowClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("计算失误！" );
                OrderAddPopupWindow popupWindow = new OrderAddPopupWindow(Test.this,0);
                popupWindow.showPopupWindow(v);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTest(TestEvent event){
        if(event != null){
            ToastUtil.show(event.getMsg());
        }
    }
}
