package com.example.ireader.dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by yajun on 2016/9/22.
 *
 */
public class DialogMaker {

    private static ProgressDialogFragment dialogFragment;

    public static void showProgressDialog(FragmentManager manager,String message){
        if(dialogFragment != null){
            dismissProgressDialog();
        }
        if(message == null){
            dialogFragment = new ProgressDialogFragment();
        }else {
            dialogFragment = ProgressDialogFragment.newInstance(message);
        }
        dialogFragment.showDialog(manager);
    }

    public static void dismissProgressDialog() {
        if (null == dialogFragment) {
            return;
        }
        dialogFragment.dismiss();
        dialogFragment = null;
    }

}
