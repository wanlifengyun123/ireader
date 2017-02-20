package com.example.ireader.view.weight;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yajun on 2016/12/6.
 *
 */
public abstract class BaseCustomer {

    protected Activity activity;

    /**
     * 参数对象
     */
    protected MetaItem metaItem;

    /**
     * 是否是编辑模式
     */
    protected boolean isEditable;

    protected CustomerEnum.PAGE_VIEW_MODE pageMode;

    public BaseCustomer(MetaItem metaItem , boolean isEditable, Activity activity){
        this.pageMode = pageMode;
        this.activity = activity;
        this.isEditable = isEditable && metaItem.isVisible;
    }

    public MetaItem getMetaItem() {
        return metaItem;
    }

    /**
     * 获取控件的焦点
     */
    public boolean requestFocus(){
        return false;
    }

    public abstract View getView(Activity act, Fragment fragment, ViewGroup lineView);
}
