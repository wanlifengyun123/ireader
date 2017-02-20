package com.example.ireader.view.weight;

/**
 * Created by yajun on 2016/12/6.
 */

public class CustomerEnum {

    public static enum PAGE_VIEW_MODE {
        VISIT_NORMAL(0), VISIT_MULTI(1), STORE(2);
        private int mode = 0;

        private PAGE_VIEW_MODE(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

    }
}
