package com.example.ireader.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ireader.R;

/**
 * Created by yajun on 2016/9/22.
 *
 */
public class ProgressDialogFragment extends DialogFragment {

    private String mMessage = "正在加载,请等待...";

    private boolean isCancelable = false;

    public static ProgressDialogFragment newInstance(String message){
        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mMessage",message);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mMessage = getArguments().getString("mMessage");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.common_progress_dialog, null, false);
        ProgressBar mProgressBar = (ProgressBar) dialogView.findViewById(R.id.easy_progress_bar);
        TextView tvMsg = (TextView) dialogView.findViewById(R.id.easy_progress_dialog_message);
        tvMsg.setText(mMessage);
        dialog.setContentView(dialogView);

        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
        if (!isCancelable) {
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                        return true;
                    else
                        return false;
                }
            });
        }
        return dialog;
    }

    /**
     * 该方法用于在异步回调中开启dialog(防止在某些版本中,activity销毁后 dialog调用失败)
     * @param manager
     * @param tag
     */
    public void showDialog(FragmentManager manager, String tag){
        try {
            show(manager,tag);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void showDialog(FragmentManager manager){
        showDialog(manager,this.getClass().getSimpleName());
    }
}
