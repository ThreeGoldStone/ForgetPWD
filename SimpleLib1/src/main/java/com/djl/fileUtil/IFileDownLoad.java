package com.djl.fileUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.djl.androidutils.DJLUtils;
import com.djl.fileUtil.bitmaputil.BitmapLoader;
import com.djl.fileUtil.bitmaputil.Operate.BitmapCreate;
import com.djl.fileUtil.database.MyFileDownLoadDataBase;
import com.djl.javaUtils.CipherUtils;
import com.djl.javaUtils.StringUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DJl on 2016/6/1.
 * email:1554068430@qq.com
 */
public interface IFileDownLoad {
    static String TAG = "IFileDownLoad";

    /**
     * 开始下载
     *
     * @param fileDownLoadBean
     * @param params
     */
    void downLoad(IFileDownLoadBean fileDownLoadBean, Map<String, String> params);

    /**
     * 停止下载
     *
     * @param fileDownLoadBean
     */
    void stop(IFileDownLoadBean fileDownLoadBean);

    void stopByUrl(String url);

    /**
     * 停止下载
     *
     * @param key
     */
    void stop(String key);


    /**
     * @param key
     */
    IFileDownLoadBean getDownLoadByTag(String key);

    /**
     * @param url
     */
    IFileDownLoadBean getDownLoadByUrl(String url);


    /**
     * 文件下载的回调
     */
    public interface CallBack {
        int WHAT_CREATE = 0xeecd1;
        int WHAT_START = 0xeecd2;
        int WHAT_DOWNLOAD = 0xeecd3;
        int WHAT_STOP = 0xeecd4;
        int WHAT_COMPLETE = 0xeecd5;
        int WHAT_ERROR = 0xeecd6;

        /**
         * 下载中的进度
         */
        void onDownLoad(IFileDownLoadBean tag);

        /**
         * 下载停止
         */
        void onStop(IFileDownLoadBean tag);

        /**
         * 下载完成
         *
         * @param tag
         */
        void onComplete(IFileDownLoadBean tag);

        void onStart(IFileDownLoadBean tag);

        void onCreate(IFileDownLoadBean tag);

        void onError(IFileDownLoadBean tag, Exception e);
    }

    /**
     * 文件下载的信息传递bean
     */
    interface IFileDownLoadBean {
        Object netConnections();

        IFileDownLoadBean netConnections(Object c);

        /**
         * 开始下载前与数据库比对
         */
        void dbMatching();

        /**
         * 是否用本地文件缓存
         *
         * @return
         */
        boolean isFileCache();

        /**
         * 文件下载地址
         *
         * @return
         */
        String url();

        /**
         * 文件缓存路径
         *
         * @return
         */
        String cachePath();

        /**
         * 请求方式 "get"   "post"
         *
         * @return
         */
        String requestWay();

        /**
         * 请求方式 "get"   "post"
         *
         * @return
         */
        IFileDownLoadBean requestWay(String requestWay);

        /**
         * 文件唯一标识
         *
         * @return
         */
        String key();

        /**
         * 文件总大小
         *
         * @return
         */
        long totalSize();

        /**
         * 设置文件已下载的大小
         *
         * @return
         */
        IFileDownLoadBean totalSize(long downSize);


        /**
         * 文件已下载的大小
         *
         * @return
         */
        long downLoadSize();

        /**
         * @return
         */
        CallBack callBack();

        /**
         * 设置文件已下载的大小
         *
         * @return
         */
        IFileDownLoadBean downLoadSize(long downSize);

        /**
         * @return
         */
        boolean stop();

        /**
         * 是否完成
         *
         * @return
         */
        boolean complete();

        /**
         * 是否完成
         */
        void complete(boolean complete);

        IFileDownLoadBean stop(boolean isStop);

        /**
         * 插入这条文件下载记录
         */
        void insert();

        /**
         * 更新这条文件下载记录
         */
        void update();

        Object others();

        void others(Object o);

        void close();
    }

    class DefaultFileDownLoadBean implements IFileDownLoadBean {
        private String url;
        private String cachePath;
        private String key;
        private String requestWay = "get";
        private long totalSize;
        private long downLoadSize;
        private boolean stop;
        private boolean complete;
        protected CallBack callBack;
        private Object others;
        private WeakReference<Object> netConnections;
        static String sqlGetWhere = " single_key='{single_key}'";
        private boolean fileCache;

        /**
         * @param url       文件下载的地址
         * @param cachePath 文件缓存地址
         * @param handler   文件下载状态的回掉handler
         */
        public DefaultFileDownLoadBean(String url, String cachePath, Handler handler) {
            this.url = url;
            this.callBack = new DefaultCallBack(handler);
            if (!StringUtils.isEmpty(cachePath)) {
                this.cachePath = cachePath + "/" + CipherUtils.md5(url());
                DJLUtils.log("cachePath=" + this.cachePath);
            }
            fileCache = cachePath != null;
            key = formTheKey();
        }

        /**
         * 生成文件唯一标识的key的方法
         *
         * @return
         */
        @NonNull
        protected String formTheKey() {
            return CipherUtils.md5(url() + cachePath());
        }

        @Override
        public Object netConnections() {
            return netConnections.get();
        }

        @Override
        public IFileDownLoadBean netConnections(Object c) {
            netConnections = new WeakReference<>(c);
            return this;
        }

        /**
         * 开始下载前与数据库比对
         */
        @Override
        public void dbMatching() {
            if (!isFileCache()) {
                complete = BitmapLoader.getInstance().get(key()) != null;
                return;
            }
            String wh = sqlGetWhere.replace("{single_key}", key());
            SQLiteDatabase wdb = MyFileDownLoadDataBase.getInstance().getWritableDatabase();
            Cursor query = wdb.query(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, null, wh, null, null, null, null);
            if (query.moveToNext()) {
                // 该文件已存在
                String down_size = query.getString(query.getColumnIndex("down_size"));
                String total_size = query.getString(query.getColumnIndex("total_size"));
                String local_path = query.getString(query.getColumnIndex("local_path"));
                String down_load_state = query.getString(query.getColumnIndex("down_load_state"));
                totalSize(StringUtils.toLong(total_size));
                long fileRealSize = FileUtils.newFile(local_path).length();
                downLoadSize = Math.min(fileRealSize, StringUtils.toLong(down_size));
                switch (down_load_state) {
                    case "complete":
                        complete = true;
                        break;
                    default:
                        complete = false;
                        break;
                }
                complete = complete && fileRealSize == StringUtils.toLong(total_size);

            }
//            else {
//                //该文件还没有
//            }
            query.close();
        }

        /**
         * 是否用本地文件缓存
         *
         * @return
         */
        @Override
        public boolean isFileCache() {
            return fileCache;

        }

        /**
         * 文件下载地址
         *
         * @return
         */
        @Override
        public String url() {
            return url;
        }

        /**
         * 文件缓存路径
         *
         * @return
         */
        @Override
        public String cachePath() {
            return cachePath;
        }

        /**
         * 文件唯一标识
         *
         * @return
         */
        @Override
        public String key() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            DefaultFileDownLoadBean d = (DefaultFileDownLoadBean) o;
            return d != null && d.key() != null && d.key().equals(key());
        }

        /**
         * 文件总大小
         *
         * @return
         */
        @Override
        public long totalSize() {
            return totalSize;
        }

        /**
         * 设置文件总大小
         *
         * @param totalSize
         * @return
         */
        @Override
        public IFileDownLoadBean totalSize(long totalSize) {
            this.totalSize = totalSize;
            return this;
        }

        /**
         * 文件已下载的大小
         *
         * @return
         */
        @Override
        public long downLoadSize() {
            return downLoadSize;
        }

        @Override
        public CallBack callBack() {
            return callBack;
        }

        /**
         * 设置文件已下载的大小
         *
         * @param downSize
         * @return
         */
        @Override
        public IFileDownLoadBean downLoadSize(long downSize) {
            this.downLoadSize = downSize;
            return this;
        }

        /**
         * 请求方式 "get"   "post"
         *
         * @return
         */
        @Override
        public String requestWay() {
            return requestWay;
        }

        /**
         * 请求方式 "get"   "post"
         *
         * @param requestWay
         * @return
         */
        @Override
        public IFileDownLoadBean requestWay(String requestWay) {
            this.requestWay = requestWay;
            return this;
        }

        /**
         * @return
         */
        @Override
        public boolean stop() {
            return stop;
        }

        /**
         * 是否完成
         *
         * @return
         */
        @Override
        public boolean complete() {
            return complete;
        }

        /**
         * 是否完成
         *
         * @param complete
         */
        @Override
        public void complete(boolean complete) {
            this.complete = complete;
        }

        @Override
        public IFileDownLoadBean stop(boolean isStop) {
            stop = isStop;
            return this;
        }

        /**
         * 插入这条文件下载记录
         */
        @Override
        public void insert() {
            if (!isFileCache()) return;
            String wh = sqlGetWhere.replace("{single_key}", key());
            SQLiteDatabase wdb = MyFileDownLoadDataBase.getInstance().getWritableDatabase();
            Cursor query = wdb.query(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, null, wh, null, null, null, null);
            if (query.moveToNext()) {
                String total_size = query.getString(query.getColumnIndex("total_size"));
                totalSize(StringUtils.toLong(total_size));
            } else {
                //该文件还没有
                ContentValues values = new ContentValues();
                values.put("url", url());
                values.put("local_path", cachePath());
                values.put("single_key", key());
                values.put("total_size", totalSize());
                values.put("down_size", downLoadSize());
                values.put("create_time", System.currentTimeMillis());
                wdb.insert(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, null, values);
            }
            query.close();
        }

        /**
         * 更新这条文件下载记录
         */
        @Override
        public void update() {
            if (!isFileCache()) return;
            SQLiteDatabase wdb = MyFileDownLoadDataBase.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("down_size", downLoadSize());
            values.put("update_time", System.currentTimeMillis());
            if (complete()) {
                values.put("down_load_state", "complete");
            }
            wdb.update(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, values, sqlGetWhere.replace("{single_key}", key()), null);
        }


        @Override
        public Object others() {
            return others;
        }

        @Override
        public void others(Object o) {
            others = o;
        }

        @Override
        public void close() {
            callBack = null;
        }
    }

    class DefaultCallBack implements CallBack {
        Handler mHandler;

        public DefaultCallBack(Handler mHandler) {
            this.mHandler = mHandler;
        }

        /**
         * 下载中的进度
         *
         * @param tag
         */
        @Override
        public void onDownLoad(IFileDownLoadBean tag) {
            // 回掉下载进度的方法
            log("onDownLoad: " + tag.url() + "    progress=" + tag.downLoadSize() + "/" + tag.totalSize());
            mHandler.obtainMessage(WHAT_DOWNLOAD, tag).sendToTarget();
        }

        void log(String message) {
            if (DJLUtils.DEBUG) {
                Log.i(TAG, message);
            }
        }

        /**
         * 下载停止
         *
         * @param tag
         */
        @Override
        public void onStop(IFileDownLoadBean tag) {
            //  刷新数据库数据，并且回掉下载完成的方法
            log("onStop: " + tag.url());
            tag.update();
            mHandler.obtainMessage(WHAT_STOP, tag).sendToTarget();
        }

        /**
         * 下载完成
         *
         * @param tag
         */
        @Override
        public void onComplete(IFileDownLoadBean tag) {
            //  刷新数据库数据，并且回掉下载完成的方法
            log("onComplete: " + tag.url());

            tag.update();
            mHandler.obtainMessage(WHAT_COMPLETE, tag).sendToTarget();
        }

        @Override
        public void onStart(IFileDownLoadBean tag) {
            // 从数据库获取，没有，往数据库保存
            log("onStart: " + tag.url());
            tag.insert();
            mHandler.obtainMessage(WHAT_START, tag).sendToTarget();

        }

        @Override
        public void onCreate(IFileDownLoadBean tag) {
            log("onCreate: " + tag.url());
            tag.dbMatching();
            mHandler.obtainMessage(WHAT_CREATE, tag).sendToTarget();
        }

        @Override
        public void onError(IFileDownLoadBean tag, Exception e) {
            log("onError: " + tag.url());
            //TODO 显示错误原因
            tag.others(e.getMessage());
            mHandler.obtainMessage(WHAT_ERROR, tag).sendToTarget();
            onStop(tag);
        }
    }

    class DefaultImageDownLoadBean extends DefaultFileDownLoadBean {
        protected Handler mainHandler;

        public ArrayList<IShowBitmap> getShowBitmaps() {
            return showBitmaps;
        }

        protected ArrayList<IShowBitmap> showBitmaps = new ArrayList<>();

        /**
         * @param url         图片下载的地址
         * @param cachePath   图片缓存的位置 为空时该图片将只进行内存缓存
         * @param showBitmaps 显示图片的封装bean
         */
        public DefaultImageDownLoadBean(String url, String cachePath, IShowBitmap... showBitmaps) {
            this(url, cachePath, null, showBitmaps);
        }

        public DefaultImageDownLoadBean(String url, String cachePath, MainHandler mainHandler, IShowBitmap... showBitmaps) {
            super(url, cachePath, mainHandler);
            for (IShowBitmap showBitmap : showBitmaps) {
                add(showBitmap);
            }
            if (mainHandler == null) {
                mainHandler = new MainHandler();
                callBack = new DefaultCallBack(mainHandler);
            }
        }

        public DefaultFileDownLoadBean add(IShowBitmap showBitmap) {
            if (!showBitmaps.contains(showBitmaps)) {
                showBitmaps.add(showBitmap);
            }
            return this;
        }

        public static class MainHandler extends Handler {

            public MainHandler() {
                super(Looper.getMainLooper());
            }

            @Override
            public void handleMessage(Message msg) {
                DefaultImageDownLoadBean idlb = (DefaultImageDownLoadBean) msg.obj;
                if (idlb == null) {
                    return;
                }
                for (IShowBitmap showBitmap : idlb.getShowBitmaps()) {
                    switch (msg.what) {
                        case CallBack.WHAT_CREATE:
                            showBitmap.showDefault();
                            break;
                        case CallBack.WHAT_START:
                            break;
                        case CallBack.WHAT_DOWNLOAD:
                            break;
                        case CallBack.WHAT_STOP:
                            break;
                        case CallBack.WHAT_COMPLETE:
                            // 从下载完成的图片中读取图片
                            Bitmap bitmap = null;
                            if (idlb.cachePath() != null) {
                                //读取文件缓存的图片
                                String key = idlb.cachePath() + "w" + showBitmap.size()[0] + "h" + showBitmap.size()[1];
                                bitmap = BitmapLoader.getInstance().get(key);
                                if (bitmap == null) {
                                    Bitmap bitmapFromFile = BitmapCreate.bitmapFromFile(idlb.cachePath(), showBitmap.size()[0],
                                            showBitmap.size()[1]);
                                    BitmapLoader.getInstance().put(key, bitmapFromFile);
                                    bitmap = BitmapLoader.getInstance().get(key);
                                }
                            } else {
                                //读取非文件缓存的图片
                                bitmap = BitmapLoader.getInstance().get(idlb.key());
                            }

                            showBitmap.show(bitmap);
                            break;
                        case CallBack.WHAT_ERROR:
                            showBitmap.showError();
                            break;
                    }
                }

            }
        }
    }


    interface IShowBitmap {
        /**
         * 需要显示该图片view
         *
         * @return
         */
        View view();

        /**
         * 显示的尺寸{宽，高}
         *
         * @return
         */
        int[] size();

        /**
         * 显示下载完成的图片
         *
         * @param bitmap
         */
        void show(Bitmap bitmap);

        /**
         * 显示默认图
         */
        void showDefault();

        /**
         * 显示图片加载错误的情况
         */
        void showError();
    }

    /**
     * 普通的view加载图片
     */
    class DefaultShowBitmap implements IShowBitmap {
        private View view;
        private int[] size;
        private int defaultImageID;
        private int errorImageID;

        public DefaultShowBitmap(View view, int[] size, int defaultImageID, int errorImageID) {
            this.view = view;
            this.size = size;
            this.defaultImageID = defaultImageID;
            this.errorImageID = errorImageID;
            showDefault();
        }

        /**
         * 需要显示该图片view
         *
         * @return
         */
        @Override
        public View view() {
            return view;
        }

        @Override
        public boolean equals(Object o) {
            DefaultShowBitmap s = (DefaultShowBitmap) o;
            return s != null && s.view().equals(view());
        }

        /**
         * 显示的尺寸{宽，高}
         *
         * @return
         */
        @Override
        public int[] size() {
            return size;
        }

        /**
         * 显示下载完成的图片
         *
         * @param bitmap
         */
        @Override
        public void show(Bitmap bitmap) {
            if (bitmap == null) {
                showError();
            } else {
                View v = view();
                if (v == null || bitmap == null)
                    return;
                if (v instanceof ImageView) {
                    ((ImageView) v).setImageBitmap(bitmap);
                } else {
                    v.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }
            }

        }

        /**
         * 显示默认图
         */
        @Override
        public void showDefault() {
            View v = view();
            if (v == null)
                return;
            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(defaultImageID);
            } else {
                v.setBackgroundResource(defaultImageID);
            }
        }

        /**
         * 显示图片加载错误的情况
         */
        @Override
        public void showError() {
            View v = view();
            if (v == null)
                return;
            if (v instanceof ImageView) {
                ((ImageView) v).setImageResource(errorImageID);
            } else {
                v.setBackgroundResource(errorImageID);
            }
        }
    }

    /**
     * 适应于adapterView的item，就是一个view复用可能会被多个图片回掉到，判断条件   if (tag.equals(view().getTag()))
     */
    class DefaultShowBitmapByTag extends DefaultShowBitmap {
        private String tag;

        public DefaultShowBitmapByTag(View view, int[] size, int defaultImageID, int errorImageID, String tag) {
            super(view, size, defaultImageID, errorImageID);
            this.tag = tag;
        }

        @Override
        public void show(Bitmap bitmap) {
            if (tag.equals(view().getTag()))
                super.show(bitmap);
        }


        @Override
        public void showDefault() {
            if (tag.equals(view().getTag()))
                super.showDefault();
        }

        @Override
        public void showError() {
            if (tag.equals(view().getTag()))
                super.showError();
        }
    }


    interface IBitmapMemoryCache {
        /**
         * 缓存图片在内存
         *
         * @param key 缓存的key
         * @param b   图片
         */
        void put(String key, Bitmap b);

        /**
         * 获取缓存在内存的图片
         *
         * @param key 缓存key
         * @return 图片
         */
        Bitmap get(String key);


    }


}
