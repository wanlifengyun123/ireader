package com.example.ireader.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by yajun on 2016/10/17.
 *
 */
public class DBContentProvider extends ContentProvider {

    private static final int USER = 1;
    private static final int BOOK = 2;
    public static String USER_TABLE_NAME;
    public static String BOOK_TABLE_NAME;

    private static final UriMatcher sURLMatcher;
    private DBOpenHelper mOpenHelper;

    private SQLiteDatabase db;

    static {
        USER_TABLE_NAME = "user";
        BOOK_TABLE_NAME = "book";
        sURLMatcher = new UriMatcher(-1);
        sURLMatcher.addURI("com.example.ireader", USER_TABLE_NAME, USER);
        sURLMatcher.addURI("com.example.ireader", BOOK_TABLE_NAME, BOOK);
    }

    @Override
    public boolean onCreate() {
        this.mOpenHelper = DBOpenHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sURLMatcher.match(uri);
        db = mOpenHelper.getReadableDatabase();
        switch (match){
            case 1:
                return db.query(USER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            case 2:
                return db.query(BOOK_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sURLMatcher.match(uri);
        db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        long count = 0;
        switch (match){
            case 1:
                count = db.insert(USER_TABLE_NAME, null, values);
              break;
            case 2:
                count = db.insert(BOOK_TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        db.endTransaction();
        Uri localUri = Uri.withAppendedPath(uri, String.valueOf(count));
        getContext().getContentResolver().notifyChange(localUri, null);
        return localUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sURLMatcher.match(uri);
        db = mOpenHelper.getWritableDatabase();
        int count = 0;
        switch (match){
            case 1:
                count = db.delete(USER_TABLE_NAME, selection, selectionArgs);
                break;
            case 2:
                count = db.delete(BOOK_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        Uri localUri = Uri.withAppendedPath(uri, String.valueOf(count));
        getContext().getContentResolver().notifyChange(localUri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = sURLMatcher.match(uri);
        db = mOpenHelper.getWritableDatabase();
        int count = 0;
        switch (match){
            case 1:
                count = db.update(USER_TABLE_NAME, values, selection, selectionArgs);
                break;
            case 2:
                count = db.update(BOOK_TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        Uri localUri = Uri.withAppendedPath(uri, String.valueOf(count));
        getContext().getContentResolver().notifyChange(localUri, null);
        return count;
    }
}
