package com.djl.fileUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.djl.androidutils.DJLUtils;
import com.djl.fileUtil.bitmaputil.BitmapLoader;
import com.djl.fileUtil.bitmaputil.Operate.BitmapCreate;
import com.djl.fileUtil.database.MyFileDownLoadDataBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by DJl on 2016/6/1.
 * email:1554068430@qq.com
 */
public class OKHttpFileDownLoader implements IFileDownLoad, Callback {
    public static final MediaType NOMAL_MEDIATYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private OkHttpClient mClient;
    private static OKHttpFileDownLoader instance;
    private static final int BUFFER_SIZE = 1024 * 50;

    private ArrayList<IFileDownLoadBean> fileDownLoadBeans = new ArrayList<>();

    private OKHttpFileDownLoader() {
        if (mClient == null)
            mClient = new OkHttpClient();
    }

    public static OKHttpFileDownLoader getInstance() {
        if (instance == null) {
            instance = new OKHttpFileDownLoader();
        }
        return instance;
    }

    public void close() {
        //取消所有链接和回掉
        for (IFileDownLoadBean fileDownLoadBean : fileDownLoadBeans) {
            fileDownLoadBean.close();
        }
        fileDownLoadBeans.clear();
        //取消静态引用
        instance = null;
    }

    @Override
    public void downLoad(IFileDownLoadBean fileDownLoadBean, Map<String, String> params) {
        DJLUtils.log("downLoad=" + fileDownLoadBean.url());
        Request request = null;
        if (!fileDownLoadBeans.contains(fileDownLoadBean)) {
            if (fileDownLoadBean.callBack() != null)
                fileDownLoadBean.callBack().onCreate(fileDownLoadBean);
            if (!fileDownLoadBean.complete()) {
                try {
                    switch (fileDownLoadBean.requestWay().toLowerCase()) {
                        case "get":
                            request = new Request.Builder()
                                    .addHeader("Range", "bytes=" + fileDownLoadBean.downLoadSize() + "-")
                                    .url(fileDownLoadBean.url()).tag(fileDownLoadBean)
                                    .get().build();
                            break;
                        case "post":
                            if (params != null) {
                                RequestBody body = RequestBody.create(NOMAL_MEDIATYPE, PostParams(params, "utf-8"));
                                request = new Request.Builder()
                                        .addHeader("Range", "bytes=" + fileDownLoadBean.downLoadSize() + "-")
                                        .url(fileDownLoadBean.url()).tag(fileDownLoadBean)
                                        .post(body).build();
                            }
                            break;
                    }
                    Call call = mClient.newCall(request);
                    fileDownLoadBean.netConnections(call);
                    fileDownLoadBeans.add(fileDownLoadBean);
                    call.enqueue(this);
                } catch (Exception e) {
                    e.printStackTrace();
                    fileDownLoadBean.callBack().onError(fileDownLoadBean, e);
                }
            } else {
                if (fileDownLoadBean.callBack() != null)
                    fileDownLoadBean.callBack().onComplete(fileDownLoadBean);
            }
        }

    }

    /**
     * 删除缓存的文件
     *
     * @param url 缓存文件的下载地址
     */
    public void deleteCacheFile(String url) {
        SQLiteDatabase wdb = MyFileDownLoadDataBase.getInstance().getWritableDatabase();
        String whereF = "url='?'".replace("?", url);
        Cursor query = wdb.query(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, null, whereF, null, null, null, null);
        if (query.moveToNext()) {
            String local_path = query.getString(query.getColumnIndex("local_path"));
            wdb.delete(MyFileDownLoadDataBase.TABLE_NAME_FILE_CACHE, whereF, null);
            File file = FileUtils.newFile(local_path);
            if (file != null) {
                file.delete();
            }
        }
        query.close();
    }

    @Override
    public void stop(IFileDownLoadBean fileDownLoadBean) {
        if (fileDownLoadBean != null) {
            fileDownLoadBean.stop(true);
        }

    }

    @Override
    public void stopByUrl(String url) {
        stop(getDownLoadByUrl(url));
    }

    /**
     * 停止下载
     *
     * @param key IFileDownLoadBean.key();
     */
    @Override
    public void stop(String key) {
        stop(getDownLoadByTag(key));
    }

    /**
     * 停止下载
     *
     * @param key IFileDownLoadBean.key();
     */
    @Override
    public IFileDownLoadBean getDownLoadByTag(String key) {
        for (IFileDownLoadBean fileDownLoadBean : fileDownLoadBeans) {
            if (fileDownLoadBean.key().equals(key)) {
                return fileDownLoadBean;
            }
        }
        return null;
    }

    public boolean contains(IFileDownLoadBean fileDownLoadBean) {
        return fileDownLoadBeans.contains(fileDownLoadBean);
    }

    /**
     * @param url IFileDownLoadBean.url();
     */
    @Override
    public IFileDownLoadBean getDownLoadByUrl(String url) {
        for (IFileDownLoadBean fileDownLoadBean : fileDownLoadBeans) {
            if (fileDownLoadBean.url().equals(url)) {
                return fileDownLoadBean;
            }
        }
        return null;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        IFileDownLoadBean tag = (IFileDownLoadBean) call.request().tag();
        CallBack callBack = tag.callBack();
        fileDownLoadBeans.remove(tag);
        if (callBack != null) callBack.onError(tag, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        IFileDownLoadBean tag = null;
        CallBack callBack = null;
        RandomAccessFile randomFile = null;
        InputStream is = null;
        try {
            tag = (IFileDownLoadBean) call.request().tag();
            callBack = tag.callBack();
            //回掉为空直接抛出
            if (callBack == null) throw new IllegalStateException("callBack == null");
            ResponseBody body = response.body();
            is = body.byteStream();
            long totalLength = body.contentLength();
            tag.totalSize(totalLength);
            callBack.onStart(tag);
            if (tag.cachePath() != null) {
                //文件地址部位空，就是文件缓存
                randomFile = new RandomAccessFile(FileUtils.newFile(tag.cachePath()), "rwd");
                randomFile.seek(tag.downLoadSize());
                byte[] buffer = new byte[BUFFER_SIZE];
                int offset = 0;
                while ((!tag.stop()) && (offset = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    randomFile.write(buffer, 0, offset);
                    tag.downLoadSize(tag.downLoadSize() + offset);
                    callBack.onDownLoad(tag);
                    if (tag.downLoadSize() >= tag.totalSize()) {
                        tag.complete(true);
                        callBack.onComplete(tag);
                    }
                }
            } else {
                //文件地址为空时进行内存缓存，（目前仅支持图片）
                Bitmap bitmap = BitmapCreate.bitmapFromStream(is, 0, 0);
                BitmapLoader.getInstance().put(tag.key(), bitmap);
                callBack.onComplete(tag);
            }
            callBack.onStop(tag);
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) callBack.onError(tag, e);
        } finally {
            if (tag != null) fileDownLoadBeans.remove(tag);
            if (randomFile != null) randomFile.close();
            if (is != null) is.close();
        }

    }

    protected static String PostParams(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}
