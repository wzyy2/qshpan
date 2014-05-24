package com.model;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Data.ApplicationTrans;
import com.dao.ImagesDataHelper;
import com.dao.ItemsDataHelper;
import com.dao.PostsDataHelper;
import com.ui.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by liurongchan on 14-4-23.
 */
public class BasePost {
    public static final HashMap<Integer, BasePost> CACHE_POST = new HashMap<Integer, BasePost>();

    public static final HashMap<Integer, BasePost> CACHE_ITEM = new HashMap<Integer, BasePost>();
    public static final int NOIMG = 0;
    public static final int YESIMG = 1;

    public static final String POST = "post";

    public static final String ITEM = "item";

    private static ImagesDataHelper miDataHelper = new ImagesDataHelper(ApplicationTrans.getContext());

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }

    public BasePost getItem(int position) {
        return null;
    }

    public static void bindView(View view, Context context, Cursor cursor, int type) {
        if (type == 0) {
            Post.bindView(view, context, cursor);
        } else {
            PostWithPic.bindView(view, context, cursor);
        }
    }

    public static void addToCache(BasePost post, String type) {
        if (type.equals(POST)) {
            CACHE_POST.put(post.tid, post);
        } else {
            CACHE_ITEM.put(post.pid, post);
        }
    }

    public static BasePost getFromCache(int id, String type) {
        if (type.equals(ITEM)) {
            return CACHE_ITEM.get(id);
        } else {
            return CACHE_POST.get(id);
        }
    }

    public static BasePost fromCursor(Cursor cursor, String type) {
        BasePost post = null ;
        ArrayList<Image> images = null;
        if (type.equals(POST)) {
            int tid = cursor.getInt(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.TID));
            post = getFromCache(tid, type);
            if (post != null) {
                return post;
            }

            int fid = cursor.getInt(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.FID));
            int pid = cursor.getInt(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.PID));
            String title = cursor.getString(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.TITLE));
            String content = cursor.getString(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.TIME));
            int haveimg = cursor.getInt(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.HAVEIMG));
            int comment_count = cursor.getInt(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.COMMENT_COUNT));
            String author = cursor.getString(cursor.getColumnIndex(PostsDataHelper.PostsDBInfo.AUTHOR));

                post = new Post(fid, tid, pid, title, content, time, haveimg, comment_count, author, images);
            addToCache(post, type);
        } else {
            int pid = cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.PID));
            System.out.println(pid);
            post = getFromCache(pid, type);
            if (post != null) {
                return post;
            }

            int fid = cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.FID));
            int tid = cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.TID));
            String title = cursor.getString(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.TITLE));
            String content = cursor.getString(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.TIME));
            int haveimg = cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.HAVEIMG));
            System.out.println(haveimg);
            int comment_count = cursor.getInt(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.COMMENT_COUNT));
            String author = cursor.getString(cursor.getColumnIndex(ItemsDataHelper.ItemsDBInfo.AUTHOR));


            if (haveimg == NOIMG) {
                post = new Post(fid, tid, pid, title, content, time, haveimg, comment_count, author, images);
            } else {

                images = miDataHelper.queryImages(pid);
                post = new PostWithPic(fid, tid, pid, title, content, time, haveimg, comment_count, author, images);
            }
            addToCache(post, type);
        }
        return post;
    }

    public BasePost(int fid, int tid, int pid, String title, String content, String time, int haveimg, int comment_count, String author, ArrayList<Image> images) {
        this.fid = fid;
        this.tid = tid;
        this.pid = pid;
        this.title = title;
        this.content = content;
        this.time = time;
        this.haveimg = haveimg;
        this.comment_count = comment_count;
        this.author = author;
        this.images = images;
    }

    public int fid;
    public int tid;
    public int pid;
    public String title;
    public String content;
    public String time;
    public int haveimg;
    public int comment_count;
    public String author;
    public ArrayList<Image> images;


    public static Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }  else {
            holder.layout.removeAllViews();
        }
        return holder;
    }


    static class Holder {

        TextView author;
        TextView time;
        TextView content;
        LinearLayout layout;

        public Holder(View view) {
        	author = (TextView) view.findViewById(R.id.author);
        	time = (TextView) view.findViewById(R.id.time);
        	content = (TextView) view.findViewById(R.id.content);
        	layout = (LinearLayout) view.findViewById(R.id.img_area);
        	
    		
        }
    }
}
