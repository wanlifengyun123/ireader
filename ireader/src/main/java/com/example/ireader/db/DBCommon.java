package com.example.ireader.db;

import android.net.Uri;

/**
 * Created by yajun on 2016/12/9.
 *
 */
public class DBCommon {

    public static abstract interface BaseColumns {
        public static final String AUTHORITY = "com.example.ireader";
        public static final String _ID = "_id";
    }

    public static abstract interface BookColumns extends DBCommon.BaseColumns {
        public static final Uri CONTENT_URI_BOOK = Uri.parse("content://com.example.ireader/" + DBContentProvider.BOOK_TABLE_NAME);
        public static final String CREATE_TABLE_USER = " CREATE TABLE IF NOT EXISTS " + DBContentProvider.BOOK_TABLE_NAME + " ( "
                + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "id" + " INTEGER, "
                + "title" + " VARCHAR,"
                + "type" + " VARCHAR, "
                + "author" + " VARCHAR , "
                + "bhref" + " VARCHAR , "
                + "isCache" + " INTEGER, "
                + "updateTime" + " VARCHAR, "
                + "isDelete" + " INTEGER"
                + ")";

        public static final String B_ID = "id";
        public static final String B_TITLE = "title";
        public static final String B_TYPE = "type";
        public static final String B_AUTHOR = "author";
        public static final String B_HREF = "bhref";
        public static final String B_IS_CACHE = "isCache";
        public static final String B_UP_DATE_TIME = "updateTime";
        public static final String B_IS_DELETE = "isDelete";

        public static final String[] projects = { "id", "title", "type", "author", "bhref" ,"isCache", "updateTime", "isDelete" };
    }

}
