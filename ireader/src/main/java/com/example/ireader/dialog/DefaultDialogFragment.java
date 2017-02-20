package com.example.ireader.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ireader.R;

import java.io.Serializable;

/**
 * Created by yajun on 2016/9/22.
 *
 */
public class DefaultDialogFragment extends DialogFragment {

    public String mTitle;
    public String mMsg;
    public boolean isShowEditText;
    public String mConfigStr;
    public String mCancelStr;
    public boolean isCancelable;
    static DialogCallBack mDialogCallBack;

    public static DefaultDialogFragment newInstance (
            String mTitle,
            String mMsg,
            boolean isShowEditText,
            String mConfigStr,
            String mCancelStr,
            boolean isCancelable,
            DialogCallBack dialogCallBack){
        return setBundle(mTitle,mMsg,isShowEditText,mConfigStr,mCancelStr,isCancelable,dialogCallBack);
    }

    public static DefaultDialogFragment newInstance (
            String mTitle,
            String mMsg,
            boolean isShowEditText,
            DialogCallBack dialogCallBack){
        return setBundle(mTitle,mMsg,isShowEditText,"确认","取消",false,dialogCallBack);
    }

    public static DefaultDialogFragment newInstance (
            String mTitle,
            String mMsg,
            String mConfigStr,
            boolean isShowEditText,
            DialogCallBack dialogCallBack){
        return setBundle(mTitle,mMsg,isShowEditText,mConfigStr,"取消",false,dialogCallBack);
    }

    public static DefaultDialogFragment newInstance (
            String mTitle,
            String mMsg,
            DialogCallBack dialogCallBack){
        return setBundle(mTitle,mMsg,false,"确认","",false,dialogCallBack);
    }



    public static DefaultDialogFragment setBundle(String mTitle,
                                                  String mMsg,
                                                  boolean isShowEditText,
                                                  String mConfigStr,
                                                  String mCancelStr,
                                                  boolean isCancelable,
                                                  DialogCallBack dialogCallBack){

        mDialogCallBack = dialogCallBack == null ? new DialogCallBack() : dialogCallBack;

        DefaultDialogFragment fragment = new DefaultDialogFragment();
        Bundle args = new Bundle();
        args.putString("mTitle", mTitle);
        args.putString("mMsg", mMsg);
        args.putString("mConfigStr", mConfigStr);
        args.putString("mCancelStr", mCancelStr);
        args.putBoolean("isShowEditText", isShowEditText);
        args.putBoolean("isCancelable", isCancelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("mTitle");
        mMsg = getArguments().getString("mMsg");
        mConfigStr = getArguments().getString("mConfigStr");
        mCancelStr = getArguments().getString("mCancelStr");
        isShowEditText = getArguments().getBoolean("isShowEditText");
        isCancelable = getArguments().getBoolean("isCancelable");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.common_dialog, null, false);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.title);
        TextView tvMsg = (TextView) dialogView.findViewById(R.id.message);
        final EditText tvMsgEdit = (EditText) dialogView.findViewById(R.id.message_edit);

        View lineTopView = dialogView.findViewById(R.id.line_top);
        View lineBottomView = dialogView.findViewById(R.id.line_bottom);
        View lineView = dialogView.findViewById(R.id.line_sp);

        TextView btnConfirm = (TextView) dialogView.findViewById(R.id.confirm_btn);
        TextView btnCancel = (TextView) dialogView.findViewById(R.id.cancel_btn);

        if(!TextUtils.isEmpty(mTitle)){
            tvTitle.setText(mTitle);
        }else {
            lineTopView.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(mMsg)){
            tvMsg.setVisibility(View.GONE);
        }else {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(mMsg);
        }
        if(isShowEditText){
            tvMsgEdit.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(mCancelStr)){
            lineView.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }else {
            lineView.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(mCancelStr);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(mDialogCallBack != null){
                        mDialogCallBack.onCancel();
                    }
                }
            });
        }
        if(TextUtils.isEmpty(mConfigStr)){
            lineBottomView.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        }else {
            lineBottomView.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setText(mConfigStr);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(mDialogCallBack != null){
                        String toString = tvMsgEdit.getText().toString();
                        mDialogCallBack.onConfirm(toString);
                    }
                }
            });
        }

        updateContentView(dialogView);

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

    protected void updateContentView(View dialogView) {

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

    public static class DialogCallBack implements Serializable {

        public void onConfirm(String editMsg){}

        public void onCancel(){}
    }
}
