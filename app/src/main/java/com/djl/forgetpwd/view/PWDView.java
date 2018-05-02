package com.djl.forgetpwd.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.djl.androidutils.DJLUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by DJl on 2017/8/29.
 * email:1553068330@qq.com
 */

public class PWDView extends ImageView implements IPWDView {
    //加密次数
    private static final int encryptTimes = 3;
    private String mData;
    private int startX = 2;
    private int startY = 2;
    private int endX = 380;
    private TextPaint mTextPaint;
    private DJLEncrypt djlEncrypt;
    private String mPWD;

    public PWDView(Context context) {
        super(context);
        init(null, 0);
    }

    public PWDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PWDView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        djlEncrypt = new DJLEncrypt();
        mTextPaint = new TextPaint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawData(canvas, mData);
    }

    private void drawData(Canvas canvas, String data) {

        if (TextUtils.isEmpty(data)) {
            return;
        }
//        data = UrlUtil.getURLEncoderString(data);
//        DJLUtils.log("data = " + data);
//        //初始化默认值
//        endX = 200;
//        startX = 100;
//        startY = 100;
        //转化数据
//        byte[] bytes = data.getBytes(Charset.forName("Utf-8"));
        byte[] bytes = djlEncrypt.encrypt(data, mPWD, encryptTimes);
        int length = bytes.length;
        // 把bytes数组按照3对期一下
        int colorCounts = length / 3 + 1;
        bytes = Arrays.copyOf(bytes, colorCounts * 3);
        int currentXPoint = startX;
        int currentYPoint = startY;
        int nextbyteIndex = 0;
        int gap = 1;
        //绘制数据的长度数值
        int color = (0xFF << 24) + length;
        DJLUtils.log("length = " + length);
        DJLUtils.log("lengthColor = " + color);
        mTextPaint.setColor(color);
        canvas.drawPoint(currentXPoint, currentYPoint, mTextPaint);
        currentXPoint += gap;

        for (int nextColorIndex = 0; nextColorIndex < colorCounts; nextColorIndex++) {
            int r = bytes[nextbyteIndex++];
            int g = bytes[nextbyteIndex++];
            int b = bytes[nextbyteIndex++];
            mTextPaint.setARGB(0xff, r, g, b);
            canvas.drawPoint(currentXPoint, currentYPoint, mTextPaint);
            currentXPoint += gap;
            if (currentXPoint >= endX) {
                currentXPoint = startX;
                currentYPoint += gap;
            }

        }

    }

    @Override
    public void setContent(String content, String pwd) {
        mData = content;
        mPWD = pwd;
        DJLUtils.log("setContent : content = " + content + "   pwd = " + pwd);
        invalidate();
    }

    @Override
    public String getContent(String pwd) throws Exception {
        DJLUtils.log("setContent : " + "   pwd = " + pwd);
        mPWD = pwd;
        Bitmap bitmap = getBitmapFromView(this);
        return getContentFromBitmap(bitmap);
    }

    private String getContentFromBitmap(Bitmap bitmap) throws Exception {
        String content = null;
        if (null != bitmap) {
            int currentXPoint = startX;
            int currentYPoint = startY;
            int nextbyteIndex = 0;
            int gap = 1;
            // 取出第一位的长度位
            int pixel1 = bitmap.getPixel(startX, startY);

            DJLUtils.log("pixel1 = " + pixel1);
            int r = Color.red(pixel1);
            int g = Color.green(pixel1);
            int b = Color.blue(pixel1);
            DJLUtils.log("RGB = " + r + " " + g + " " + b);
            int length = pixel1 - (0xFF << 24);
            int width = endX - startX;
            int height = bitmap.getHeight() - startY;
            if (length > width * height) {
                throw new IllegalStateException("图片长度有误,非有效图片!");
            }
            DJLUtils.log("length = " + length);
            currentXPoint += gap;
            // 创建数据数组
            int colorCounts = length / 3 + 1;
            byte bytes[] = new byte[colorCounts * 3];
            // 遍历读取数据
            for (int nextColorIndex = 0; nextColorIndex < colorCounts; nextColorIndex++) {
                int pixel = bitmap.getPixel(currentXPoint, currentYPoint);
                bytes[nextbyteIndex++] = (byte) Color.red(pixel);
                bytes[nextbyteIndex++] = (byte) Color.green(pixel);
                bytes[nextbyteIndex++] = (byte) Color.blue(pixel);
                currentXPoint += gap;
                if (currentXPoint >= endX) {
                    currentXPoint = startX;
                    currentYPoint += gap;
                }

            }
            content = djlEncrypt.decryptString(Arrays.copyOf(bytes, length), mPWD, encryptTimes);
            bitmap.recycle();
        }

        return content;
    }

    @Override
    public void loadImage(String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        setImageBitmap(bm);
//        getContentFromBitmap(bm);
    }

    @Override
    public void saveImage(String path) {
        Bitmap bitmap = getBitmapFromView(this);
        try {
            File file = new File(path);
            if (file.exists()) {
                file.createNewFile();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取view的bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap drawingCache = v.getDrawingCache();
        Bitmap b = Bitmap.createBitmap(drawingCache);
        v.setDrawingCacheEnabled(false);
        return b;
    }
//    /**
//     * 获取view的bitmap
//     *
//     * @param v
//     * @return
//     */
//    public static Bitmap getBitmapFromView(View v) {
//        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
//        // Draw background
//        Drawable bgDrawable = v.getBackground();
//        if (bgDrawable != null) {
//            bgDrawable.draw(c);
//        } else {
//            c.drawColor(Color.WHITE);
//        }
//        // Draw view to canvas
//        v.draw(c);
//        return b;
//    }
}
