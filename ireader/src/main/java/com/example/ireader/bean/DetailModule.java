package com.example.ireader.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class DetailModule implements Serializable{

    private List<DetailType> detailTypes;

    private List<MainDetails> mainDetailsList;

    public List<DetailType> getDetailTypes() {
        return detailTypes;
    }

    public void setDetailTypes(List<DetailType> detailTypes) {
        this.detailTypes = detailTypes;
    }

    public List<MainDetails> getMainDetailsList() {
        return mainDetailsList;
    }

    public void setMainDetailsList(List<MainDetails> mainDetailsList) {
        this.mainDetailsList = mainDetailsList;
    }

}
