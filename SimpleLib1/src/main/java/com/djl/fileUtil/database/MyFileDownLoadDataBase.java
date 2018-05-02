package com.djl.fileUtil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.WeakReference;

/**
 * Created by DJl on 2016/6/1.
 * email:1554068430@qq.com
 */
public class MyFileDownLoadDataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME_FILE_CACHE = "file_cache";
    public static final String TABLE_NAME_IMAGE_CACHE = "image_cache";
    private static MyFileDownLoadDataBase instance;
    private static String DB_NAME = "abc";
    private static int VERSION = 1;
    private static WeakReference<Context> mContext;

    private MyFileDownLoadDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static MyFileDownLoadDataBase getInstance() {
        return getInstance(mContext.get());
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = new WeakReference<>(context);
    }

    public static MyFileDownLoadDataBase getInstance(Context context) {
        if (context != null) {
            if (instance == null) {
//            DatabaseErrorHandler errorHandler = new DatabaseErrorHandler() {
//                @Override
//                public void onCorruption(SQLiteDatabase dbObj) {
//                }
//            };
                instance = new MyFileDownLoadDataBase(context, DB_NAME, null, VERSION);
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create =
//                "--创建文件缓存表" +
                "CREATE TABLE if not exists [file_cache] (" +
                        "  [_id] INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  [url] char NOT NULL, " +
                        "  [local_path] char NOT NULL, " +
                        "  [single_key] char NOT NULL UNIQUE, " +
                        "  [total_size] char, " +
                        "  [down_size] CHAR, " +
                        "  [create_time] char, " +
                        "  [update_time] char, " +
                        "  [complete_time] char, " +
                        "  [down_load_state] char DEFAULT start);" +
//                "--创建缓存图片表" +
                        "CREATE TABLE if not exists [image_cache] (" +
                        "  [_id] INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  [file_cache_key] INTEGER NOT NULL REFERENCES [file_cache]([single_key]), " +
                        "  [scaled_file_path] char, " +
                        "  [image_width] INTEGER, " +
                        "  [image_height] INTEGER);";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        super.close();
        instance = null;
    }
}
