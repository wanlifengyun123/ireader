package com.example.ireader.bean;

import java.io.Serializable;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class MainDetails implements Serializable {


    private String titleName;
    private String typeName;
    private String author;
    private String hrefStr;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHrefStr() {
        return hrefStr;
    }

    public void setHrefStr(String hrefStr) {
        this.hrefStr = hrefStr;
    }
}
