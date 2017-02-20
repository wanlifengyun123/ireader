package com.example.ireader.net;

import java.io.Serializable;

/**
 * Created by yajun on 2016/9/7.
 *
 */
public class RequestResult<T> implements Serializable {

    /**
     * status : 1
     * data : {"uid":"3911288","uname":"wyjcool123@163.com","phone":0,"sid":"fee8089c325077a98a2cd3b121415311","token":"250f2f717699bdc63e3b5e1d705e63df","nickname":"万里风云3911288","email":"wyjcool123@163.com","sex":"1","aboutme":null,"blog":null,"country_id":"0","prov_id":"0","city_id":"0","dist_id":"0","apsid":"VlODA4OWMzMjUwNzdhOThhMmNkM2IxMjE0MTUzMTEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMzkxMTI4OAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB3eWpjb29sMTIzQDE2My5jb20AAAAAAAAAAAAAAAAAADI1MGYyZjcxNzY5OWJkYzYzZTNiNWUxZDcwNWU2M2RmDLvPVwy7z1c=Zm","companyid":"0","city":"","pic":"http://img.mukewang.com/57c690540001181301000100-100-100.jpg","messnum":55}
     * errorcode : 1000
     * msg : 成功
     * time : 1.4732316283874E9
     */

    private int status;
    private T data;
    private int errorcode;
    private String msg;
    private double time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}
