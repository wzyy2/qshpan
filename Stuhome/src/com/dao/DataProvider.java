package com.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.Data.ApplicationTrans;


/**
 * Created by liurongchan on 14-4-23.
 */
public class DataProvider extends ContentProvider {

    static final String TAG = DataProvider.class.getSimpleName();

    static final Object DBLock = new Object();

    public  static final String AUTHORITY = "org.qingshuihepan.uestc.provider";

    public static final String SCHEME = "content://";

    public static final String PATH_POSTS = "/posts";

    public static final Uri POSTS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_POSTS);

    private static final int POSTS = 0;

    public static final String PATH_IMAGES = "/images";

    public static final Uri IMAGES_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_IMAGES);

    private static final int IMAGES = 1;


    public static final String PATH_ITEMS = "/items";

    public static final Uri ITEMS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_ITEMS);

    private static final int ITEMS = 2;

    /*
    * MIME type definitions
    */
    public static final String POST_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.qingshuihepan.uestc.post";

    public static final String IMAGE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.qingshuihepan.uestc.image";

    public static final String ITEM_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.qingshuihepan.uestc.item";

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "posts", POSTS);
        sUriMatcher.addURI(AUTHORITY, "images", IMAGES);
        sUriMatcher.addURI(AUTHORITY, "items", ITEMS);
    }

    private static DBHelper mDBHelper;

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(ApplicationTrans.getContext());
        }
        return mDBHelper;
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (DBLock) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            String table = matchTable(uri);
            queryBuilder.setTables(table);

            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Cursor cursor = queryBuilder.query(db, // The database to
                    // queryFromDB
                    projection, // The columns to return from the queryFromDB
                    selection, // The columns for the where clause
                    selectionArgs, // The values for the where clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    sortOrder // The sort order
            );
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case POSTS:
                return POST_CONTENT_TYPE;
            case IMAGES:
                return IMAGE_CONTENT_TYPE;
            case ITEMS:
                return  ITEM_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (DBLock) {
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                rowId = db.insert(table, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            if (rowId > 0) {
                Uri returnUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();

            int count = 0;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            int count;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.update(table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);

            return count;
        }
    }

    private String matchTable(Uri uri) {
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case POSTS:
                table = PostsDataHelper.PostsDBInfo.TABLE_NAME;
                break;
            case IMAGES:
                table = ImagesDataHelper.ImagesDBInfo.TABLE_NAME;
                break;
            case ITEMS:
                table = ItemsDataHelper.ItemsDBInfo.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return table;
    }
}
