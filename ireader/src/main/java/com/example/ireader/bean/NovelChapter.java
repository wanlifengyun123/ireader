package com.example.ireader.bean;

import java.io.Serializable;

/**
 * Created by yajun on 2016/10/28.
 *
 */
public class NovelChapter implements Serializable{

    private String bookTitle;

    private String bookHref;

    private String bookText;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookHref() {
        return bookHref;
    }

    public void setBookHref(String bookHref) {
        this.bookHref = bookHref;
    }

    public String getBookText() {
        return bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }
}
