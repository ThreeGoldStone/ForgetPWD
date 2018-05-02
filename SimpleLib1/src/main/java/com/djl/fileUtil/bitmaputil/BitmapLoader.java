package com.djl.fileUtil.bitmaputil;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;

import com.djl.fileUtil.IFileDownLoad;
import com.djl.fileUtil.OKHttpFileDownLoader;
import com.djl.fileUtil.bitmaputil.Operate.MemoryLruCache;

import java.util.ArrayList;

/**
 * Created by DJl on 2016/6/3.
 * email:1554068430@qq.com
 */
public class BitmapLoader implements IFileDownLoad.IBitmapMemoryCache {
    private static final int maxSize = (int) Runtime.getRuntime().maxMemory() / 6;
    public static BitmapLoader instance;
    private final MemoryLruCache<String, Bitmap> mLruCache;
    //默认图片的资源id
    public static int defaultImageSrc = 0;
    // 图片加载错误的默认资源id
    public static int errorImageSrc = 0;

    public static BitmapLoader getInstance() {
        if (instance == null) {
            instance = new BitmapLoader();
        }
        return instance;
    }

    public void close() {
        mLruCache.evictAll();
        instance = null;
    }

    private BitmapLoader() {
        mLruCache = new MemoryLruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };
    }

    /**
     * 这里默认以url为tag，adapterView专用
     *
     * @param width          需要的图片最大宽，传0时是原图
     * @param height         需要的图片最大高，传0时是原图
     * @param defaultImageID 默认图片的id，或者加载中图片的id
     * @param errorImageID   失败时图片的id
     * @param view           显示图片的view
     * @param url            图片的地址
     * @param cachePath      图片缓存的路径,传null时仅在内存缓存
     */
    public void displayByTag(int width, int height, int defaultImageID, int errorImageID, View view, String url, String cachePath, IFileDownLoad.DefaultImageDownLoadBean.MainHandler handler) {
        view.setTag(url);
        IFileDownLoad.DefaultShowBitmapByTag showBitmap = new IFileDownLoad.DefaultShowBitmapByTag
                (view, new int[]{width, height}, defaultImageID, errorImageID, url);
        display(url, cachePath, showBitmap, handler);

    }

    /**
     * @param width          需要的图片最大宽，传0时是原图
     * @param height         需要的图片最大高，传0时是原图
     * @param defaultImageID 默认图片的id，或者加载中图片的id
     * @param errorImageID   失败时图片的id
     * @param view           显示图片的view
     * @param url            图片的地址
     * @param cachePath      图片缓存的路径,传null时仅在内存缓存
     */
    public void display(int width, int height, int defaultImageID, int errorImageID, View view, String url, String cachePath, IFileDownLoad.DefaultImageDownLoadBean.MainHandler handler) {
        IFileDownLoad.DefaultShowBitmap showBitmap = new IFileDownLoad.DefaultShowBitmap
                (view, new int[]{width, height}, defaultImageID, errorImageID);
        display(url, cachePath, showBitmap, handler);

    }

    /**
     * @param url        图片下载地址
     * @param cachePath  图片缓存地址
     * @param showBitmap 显示图片的封装类
     */
    public void display(String url, String cachePath, IFileDownLoad.IShowBitmap showBitmap, IFileDownLoad.DefaultImageDownLoadBean.MainHandler handler) {
        IFileDownLoad.DefaultImageDownLoadBean downLoadByUrl = (IFileDownLoad.DefaultImageDownLoadBean)
                OKHttpFileDownLoader.getInstance().getDownLoadByUrl(url);
        if (downLoadByUrl != null) {
            //正在下载中，仅多添加图片回掉
            downLoadByUrl.add(showBitmap);
        } else {
            OKHttpFileDownLoader.getInstance().downLoad(
                    new IFileDownLoad.DefaultImageDownLoadBean(url, cachePath, handler, showBitmap), null);
        }
    }

    public void display(IFileDownLoad.DefaultImageDownLoadBean imageDownLoadBean) {
        IFileDownLoad.DefaultImageDownLoadBean downLoadByUrl = (IFileDownLoad.DefaultImageDownLoadBean)
                OKHttpFileDownLoader.getInstance().getDownLoadByUrl(imageDownLoadBean.url());
        if (downLoadByUrl != null) {
            //正在下载中，仅多添加图片回掉
            ArrayList<IFileDownLoad.IShowBitmap> showBitmaps = imageDownLoadBean.getShowBitmaps();
            showBitmaps = (ArrayList<IFileDownLoad.IShowBitmap>) showBitmaps.clone();
            for (IFileDownLoad.IShowBitmap iShowBitmap : showBitmaps) {
                downLoadByUrl.add(iShowBitmap);
            }
        } else {
            OKHttpFileDownLoader.getInstance().downLoad(imageDownLoadBean, null);
        }
    }

    @Override
    public void put(String key, Bitmap b) {
        mLruCache.put(key, b);
    }

    @Override
    public Bitmap get(String key) {
        return mLruCache.get(key);
    }


}
