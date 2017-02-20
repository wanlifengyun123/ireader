package com.example.ireader.net;

/**
 * Created by yajun on 2016/9/8.
 *
 */
public class BaseBusEvent  {

    public final static int CODE_OK = 0x000;
    public final static int CODE_ERROR = 0x001;
    public final static int CODE_ERROR_RESPONSE = 0x002;

    public int resultCode;

    public String msg;

    public Object object;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
