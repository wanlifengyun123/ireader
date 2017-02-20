package com.example.ireader.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yajun on 2016/10/28.
 *
 */
public class NovelModule implements Serializable {

    private int novelId;
    private String novelName;
    private String author;
    private int novelType;
    private String latestchapter; // 最新章节
    private String latestchapterHref;
    private String novelState;
    private String wordNumber;
    private String updateDate;
    private String contentDetail;

    private List<NovelChapter> chapterList;


    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNovelType() {
        return novelType;
    }

    public void setNovelType(int novelType) {
        this.novelType = novelType;
    }

    public String getLatestchapter() {
        return latestchapter;
    }

    public void setLatestchapter(String latestchapter) {
        this.latestchapter = latestchapter;
    }

    public String getLatestchapterHref() {
        return latestchapterHref;
    }

    public void setLatestchapterHref(String latestchapterHref) {
        this.latestchapterHref = latestchapterHref;
    }

    public String getNovelState() {
        return novelState;
    }

    public void setNovelState(String novelState) {
        this.novelState = novelState;
    }

    public String getWordNumber() {
        return wordNumber;
    }

    public void setWordNumber(String wordNumber) {
        this.wordNumber = wordNumber;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }

    public List<NovelChapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<NovelChapter> chapterList) {
        this.chapterList = chapterList;
    }
}
