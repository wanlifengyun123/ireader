package com.example.ireader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.ireader.common.TestEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yajun on 2016/12/14.
 *
 */
public class CalculateService extends Service {

    private static final String TAG = "CalculateService";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        logE("onBind()");
        return mBinder;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        logE("onCreate()");
        super.onCreate();

        TestEvent testEvent = new TestEvent();
        testEvent.setMsg("Service EventBus Test!");
        EventBus.getDefault().post(testEvent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        logE("onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        logE("onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        logE("onDestroy()");
        super.onDestroy();
    }

    private static void logE(String str) {
        Log.e(TAG, "--------" + str + "--------");
    }

    private final ICalculateService.Stub mBinder = new ICalculateService.Stub() {

        @Override
        public double doCalculate(double a, double b) throws RemoteException {
            // TODO Auto-generated method stub
            Log.e("Calculate", "远程计算中");
            double answer = a + b;
            Log.e("Calculate", "远程计算结果：" + answer);
            return answer;
        }
    };

}
