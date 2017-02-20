package com.example.ireader.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yajun on 2016/10/27.
 *
 */

public class MainInfo implements Serializable {

    private String title;

    private List<MainDetails> mainDetailses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MainDetails> getMainDetailses() {
        return mainDetailses;
    }

    public void setMainDetailses(List<MainDetails> mainDetailses) {
        this.mainDetailses = mainDetailses;
    }

    /**
     *  获取Item内容
     *
     * @param pPosition
     * @return
     */
    public Object getItem(int pPosition) {
        // Category排在第一位
        if (pPosition == 0) {
            return title;
        } else {
            return mainDetailses.get(pPosition - 1);
        }
    }

    /**
     * 当前类别Item总数。Category也需要占用一个Item
     * @return
     */
    public int getItemCount() {
        return mainDetailses.size() + 1;
    }

}
