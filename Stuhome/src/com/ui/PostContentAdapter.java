package com.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ui.R;
import com.dao.ItemsDataHelper;
import com.model.BasePost;
import com.model.Post;

/**
 * Created by liurongchan on 14-5-2.
 */
public class PostContentAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;

    private int type = 0; // 1 - pic ; 0 - nopic

    public PostContentAdapter(Context context) {
        super(context, null, false);
        mLayoutInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public BasePost getItem(int position) {
        mCursor.moveToPosition(position);
        BasePost post = BasePost.fromCursor(mCursor, BasePost.ITEM);
        if (post.haveimg == 0) {
            type = 0;
        } else {
            type = 1;
        }
        return post;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.post_content, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.HAVEIMG)) == 0) {
            type = 0;
        } else {
            type = 1;
        }
        BasePost.bindView(view, context, cursor, type);
    }
}
