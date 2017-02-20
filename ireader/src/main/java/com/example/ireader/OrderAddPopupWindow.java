package com.example.ireader;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2015/12/28.
 *
 */
public class OrderAddPopupWindow extends PopupWindow {

    public static final String TAG = OrderAddPopupWindow.class.toString();

    public static final String[] arrayList = {"添加未分销产品","添加赠品"};

    private Activity activity;

    public OrderAddPopupWindow(Activity activity, int leftMargin) {
        this.activity = activity;
        initView();
        initData();
    }

    private void initView() {
        // 设置SelectPicPopupWindow的View
        this.setContentView(initPopView());
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.transparent3Quarters));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismissListener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.update();
        this.setAnimationStyle(android.R.style.Animation_Dialog);

    }

    private View initPopView(){
        // 生成view
        final LinearLayout layout = new LinearLayout(activity);
        layout.setGravity(Gravity.LEFT);
        layout.setOrientation(LinearLayout.VERTICAL);
//        int actionBarHeight = (int) activity.getResources().getDimension(R.dimen.activity_action_bar);
//        int searchHeight = (int) activity.getResources().getDimension(R.dimen.common_search_height);
//        layout.setPadding(0,actionBarHeight + searchHeight,0,0);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        View popupView = activity.getLayoutInflater().inflate(R.layout.common_popu, null, true);

        LinearLayout popLayout = (LinearLayout) popupView.findViewById(R.id.order_filter_pop_ll);
        popLayout.setGravity(Gravity.RIGHT);

        layout.addView(popupView,linearParams);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAddPopupWindow.this.dismiss();
            }
        });
        return layout;
    }

    private void initData(){

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
//            this.showAtLocation(parent, Gravity.TOP,0,0);
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    private onPopClickListener popListener;

    public void setPopListener(onPopClickListener popListener) {
        this.popListener = popListener;
    }

    public interface onPopClickListener{
        void onPopItemClick(int orderSelectType, String orderTypeName);
    }
}
