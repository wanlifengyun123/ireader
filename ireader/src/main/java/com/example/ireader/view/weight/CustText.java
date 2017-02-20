package com.example.ireader.view.weight;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yajun on 2016/12/6.
 *
 */

public class CustText extends BaseCustomer implements ICustomerWeight  {

    private Context mContext;

    public CustText(MetaItem metaItem, boolean isEditable, Activity activity) {
        super(metaItem, isEditable, activity);
    }

    @Override
    public void setValue(Object o) {

    }

    @Override
    public View getView(Activity act, Fragment fragment, ViewGroup lineView) {
        return null;
    }
}
