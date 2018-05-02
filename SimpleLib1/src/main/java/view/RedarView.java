package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.simplelib.R;

/**
 * Created by DJl on 2016/4/20.
 * email:1554068430@qq.com
 */
public class RedarView extends View {
    private Drawable mDrawable;
    protected int fieldColor1;
    protected int fieldColor2;
    protected int webColor;
    protected int textColor;
    protected int resultColor;
    protected int level;
    protected float textSize, webWidth;
    protected Paint mPaint = new Paint();
    protected static final String gap = "-djl-";
    protected String[] texts;
    /**
     * l-b-r-t-color-drawable-drawableSize,l-b-r-t-color-drawable-drawableSize
     */
    private String datas;

    public RedarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RedarView);
        resultColor = ta.getColor(R.styleable.RedarView_result_color, 0xffff0000);
        fieldColor1 = ta.getColor(R.styleable.RedarView_field_color1, 0x00000000);
        fieldColor2 = ta.getColor(R.styleable.RedarView_field_color2, 0x00000000);
        webColor = ta.getColor(R.styleable.RedarView_web_color, 0xffff0000);
        textColor = ta.getColor(R.styleable.RedarView_text_color, 0xffff0000);
        level = ta.getInt(R.styleable.RedarView_level, 4);
        textSize = ta.getDimension(R.styleable.RedarView_text_size, 20);
        webWidth = ta.getDimension(R.styleable.RedarView_web_width, 2);
        String text = ta.getString(R.styleable.RedarView_text);
        datas = ta.getString(R.styleable.RedarView_datas);
        mDrawable = ta.getDrawable(R.styleable.RedarView_drawable);
        if (text != null) {
            texts = text.split(gap);
        }
        ta.recycle();
    }

//    /**
//     * 这里默认在布局中padding值要么不设置，要么全部设置
//     */
//    @Override
//    protected synchronized void onMeasure(int widthMeasureSpec,
//                                          int heightMeasureSpec) {
//
////        mMaxPaintWidth = Math.max(mReachedProgressBarHeight,
////                mUnReachedProgressBarHeight);
//        int expect = mRadius * 2 + getPaddingLeft()
//                + getPaddingRight();
//        int width = resolveSize(expect, widthMeasureSpec);
//        int height = resolveSize(expect, heightMeasureSpec);
//        int realWidth = Math.min(width, height);
//
//        mRadius = (realWidth - getPaddingLeft() - getPaddingRight()) / 2;
//
//        setMeasuredDimension(realWidth, realWidth);
//
//    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(webColor);
        mPaint.setAntiAlias(true);
        int width = getWidth();
        int height = getHeight();
        float left = 0 + getPaddingLeft(), right = width - getPaddingRight(), top = 0 + getPaddingTop(), bottom = height - getPaddingBottom();
        //TODO drawText
//        DrawText drawText = new DrawText(canvas, left, right, top, bottom).invoke();
//        left = drawText.getLeft();
//        right = drawText.getRight();
//        top = drawText.getTop();
//        bottom = drawText.getBottom();
//        mPaint.setColor(textColor);
//        mPaint.setTextSize(textSize);
//        canvas.drawText("paths=" + paths.length, 100, 100, mPaint);
        Path[] paths = formPaths(left, right, top, bottom, level);
        for (int i = 0; i < paths.length; i++) {
            boolean isd = i % 2 == 0;
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(isd ? fieldColor1 : fieldColor2);
            canvas.drawPath(paths[i], mPaint);
        }
        for (int i = 0; i < paths.length; i++) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(webColor);
            mPaint.setStrokeWidth(webWidth);
            canvas.drawPath(paths[i], mPaint);
        }
        //画轴线
        drawAx(canvas, left, right, top, bottom);
        drawDatas(canvas, left, right, top, bottom);
        canvas.restore();

    }

    private void drawDatas(Canvas canvas, float left, float right, float top, float bottom) {
        if (datas == null) return;
        String[] ds = datas.split(",");
        for (String d : ds) {
            new MDatas(d).draw(canvas, left, right, top, bottom);
        }


    }


    private void drawAx(Canvas canvas, float left, float right, float top, float bottom) {
        float width = Math.abs(right - left);
        float height = Math.abs(bottom - top);
//        mPaint.setTextSize(30);
//        mPaint.setColor(0xffff0000);
//        canvas.drawText("adsfasdfasdfadsf" + width + height, width / 2, height / 2, mPaint);
        float[] pointRight = new float[]{right, top + height / 2};
        float[] pointTop = new float[]{left + width / 2, top};
        float[] pointLeft = new float[]{left, top + height / 2};
        float[] pointBottom = new float[]{left + width / 2, bottom};
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(webColor);
        mPaint.setStrokeWidth(webWidth);
        canvas.drawLines(pointFormLine(pointRight, pointLeft), mPaint);
        canvas.drawLines(pointFormLine(pointTop, pointBottom), mPaint);
    }


    private Path[] formPaths(float left, float right, float top, float bottom, int level) {
        Path[] result = new Path[level];
        //计算间隔
        float width = Math.abs(right - left);
        float height = Math.abs(bottom - top);
        float levelWidth = width / 2 / level;
        float levelHeight = height / 2 / level;
        //初始化顶点
        float[] pointRight;
        float[] pointTop;
        float[] pointLeft;
        float[] pointBottom;
        for (int i = 0; i < level; i++) {
            pointRight = new float[]{right - i * levelWidth, top + height / 2};
            pointTop = new float[]{left + width / 2, top + i * levelHeight};
            pointLeft = new float[]{left + i * levelWidth, top + height / 2};
            pointBottom = new float[]{left + width / 2, bottom - levelHeight * i};
            Path path = new Path();
            path.moveTo(pointRight[0], pointRight[1]);
            path.lineTo(pointTop[0], pointTop[1]);
            path.lineTo(pointLeft[0], pointLeft[1]);
            path.lineTo(pointBottom[0], pointBottom[1]);
            path.close();
            result[i] = path;
//            Path path2 = new Path();
//            path2.moveTo(pointRight[0], pointRight[1]);
//            path2.lineTo(pointTop[0], pointTop[1]);
//            path2.lineTo(pointLeft[0], pointLeft[1]);
//            path2.lineTo(pointBottom[0], pointBottom[1]);
//            path2.close();
//            result[i * 2 + 1] = path2;
        }
        return result;
    }

    private float[] line2Lines(float[]... lines) {
        float[] result = new float[lines.length * 4];
        for (int i = 0; i < result.length; i++) {
            result[i] = lines[i / 4][i % 4];
        }
        return result;
    }

    private float[] pointFormLine(float[] point1, float[] point2) {
        return new float[]{
                point1[0], point1[1],
                point2[0], point2[1]
        };
    }

    public String getDatas() {
        return datas;
    }

    /**
     * "l-b-r-t-color-drawable-drawableSize,l-b-r-t-color-drawable-drawableSize"
     */
    public void setDatas(String datas) {
        this.datas = datas;
        invalidate();
    }

    private class DrawText {
        private Canvas canvas;
        private float left;
        private float right;
        private float top;
        private float bottom;

        public DrawText(Canvas canvas, float left, float right, float top, float bottom) {
            this.canvas = canvas;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public float getLeft() {
            return left;
        }

        public float getRight() {
            return right;
        }

        public float getTop() {
            return top;
        }

        public float getBottom() {
            return bottom;
        }

        public DrawText invoke() {
            if (texts != null && texts.length > 0) {
                mPaint.setColor(textColor);
                mPaint.setTextSize(textSize);
                try {
                    float l = mPaint.measureText(texts[0]);
                    float r = mPaint.measureText(texts[2]);
                    l = r = Math.max(l, r);
                    canvas.drawText(texts[0], left, top + Math.abs(bottom - top) / 2 + textSize / 2, mPaint);
                    float b = mPaint.measureText(texts[1]);
                    canvas.drawText(texts[1], left + Math.abs(right - left) / 2 - b / 2, bottom + textSize, mPaint);
                    canvas.drawText(texts[2], right - r, top + Math.abs(bottom - top) / 2 + textSize / 2, mPaint);
                    float t = mPaint.measureText(texts[3]);
                    canvas.drawText(texts[3], left + Math.abs(right - left) / 2 - t / 2, top, mPaint);
                    left = left + l;
                    right = right - r;
                    top = top + textSize;
                    bottom = bottom - textSize;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return this;
        }
    }

    //    private float[] formData(int width, int height, int level) {
//        //线数
//        int lineCounts = level * 4 + 2;
//        float[] result;
//        //计算间隔
//        float levelWidth = width / 2 / level;
//        float levelHeight = height / 2 / level;
//        //初始化顶点
//        float[] pointRight = {width, height / 2};
//        float[] pointTop = {width / 2, height};
//        float[] pointLeft = {0, height / 2};
//        float[] pointBottom = {width / 2, 0};
//        //初始化线数组
//        float[][] lines = new float[lineCounts][4];
//        //添加轴线
//        lines[0] = pointFormLine(pointRight, pointLeft);
//        lines[1] = pointFormLine(pointTop, pointBottom);
//        for (int i = 0; i < level; i++) {
//            pointRight = new float[]{width - i * levelWidth, height / 2};
//            pointTop = new float[]{width / 2, height - i * levelHeight};
//            pointLeft = new float[]{0 + i * levelWidth, height / 2};
//            pointBottom = new float[]{width / 2, 0 + levelHeight * i};
//
//            int index = i * 4;
//            lines[index + 2] = pointFormLine(pointRight, pointTop);
//            lines[index + 3] = pointFormLine(pointTop, pointLeft);
//            lines[index + 4] = pointFormLine(pointLeft, pointBottom);
//            lines[index + 5] = pointFormLine(pointBottom, pointRight);
//        }
//        result = line2Lines(lines);
//        return result;
//    }
    class MDatas {
        /**
         * l-b-r-t-color-drawable-drawableSize
         */
        float pleft, pbottom, pright, ptop, drawableSize;
        int colorStoke, colorSolid, stokeWidth;
        private Drawable drawable;

        MDatas(String data) {
            String[] d = data.split("-");
            if (d.length != 8)
                throw new IllegalArgumentException("格式错误！不符合l-b-r-t-colorStoke-colorSolid-stokeWidth-drawableSize");
            pleft = parseFloat(d[0], 0);
            pbottom = parseFloat(d[1], 0);
            pright = parseFloat(d[2], 0);
            ptop = parseFloat(d[3], 0);
            colorStoke = parseColor(d[4], 0xffff0000);
            colorSolid = parseColor(d[5], 0xffffff00);
            stokeWidth = parseInt(d[6], 0);
            drawableSize = parseFloat(d[7], 20);
        }

        void draw(Canvas canvas, float left, float right, float top, float bottom) {
            float width = Math.abs(right - left);
            float height = Math.abs(bottom - top);
//            mPaint.setTextSize(30);
//            mPaint.setColor(0xffff0000);
//            canvas.drawText("adsfasdfasdfadsf" + width + height, width / 2, height / 2, mPaint);
            float rleft = left + (1 - pleft) * width / 2;
            float rright = left + (1 + pright) * width / 2;
            float rtop = top + (1 - ptop) * height / 2;
            float rbottom = top + (1 + pbottom) * height / 2;
            float[] pointRight = new float[]{rright, top + height / 2};
            float[] pointTop = new float[]{left + width / 2, rtop};
            float[] pointLeft = new float[]{rleft, top + height / 2};
            float[] pointBottom = new float[]{left + width / 2, rbottom};
            Path path = new Path();
            path.moveTo(pointRight[0], pointRight[1]);
            path.lineTo(pointTop[0], pointTop[1]);
            path.lineTo(pointLeft[0], pointLeft[1]);
            path.lineTo(pointBottom[0], pointBottom[1]);
            path.close();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(stokeWidth);
            mPaint.setColor(colorStoke);
            canvas.drawPath(path, mPaint);
            Path path2 = new Path();
            path2.moveTo(pointRight[0] - stokeWidth, pointRight[1]);
            path2.lineTo(pointTop[0], pointTop[1] + stokeWidth);
            path2.lineTo(pointLeft[0] + stokeWidth, pointLeft[1]);
            path2.lineTo(pointBottom[0], pointBottom[1] - stokeWidth);
            path2.close();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(colorSolid);
            canvas.drawPath(path2, mPaint);
            if (mDrawable != null) {
                setDrawableLocation(mDrawable, pointRight, drawableSize, canvas);
                setDrawableLocation(mDrawable, pointLeft, drawableSize, canvas);
                setDrawableLocation(mDrawable, pointTop, drawableSize, canvas);
                setDrawableLocation(mDrawable, pointBottom, drawableSize, canvas);

            }

        }

        private void setDrawableLocation(Drawable mDrawable, float[] point, float size, Canvas canvas) {
//            setBounds(int left, int top, int right, int bottom)
            size = size / 2;
            mDrawable.setBounds(
                    Math.round(point[0] - size),
                    Math.round(point[1] - size),
                    Math.round(point[0] + size),
                    Math.round(point[1] + size));
            mDrawable.draw(canvas);
        }
    }

    private int parseInt(String s, int defaultValue) {
//            StringUtils.toInt()
        defaultValue = Integer.parseInt(s);
        return defaultValue;
    }

    private int parseColor(String s, int defaultValue) {
        int result = 0;
        if (s.startsWith("0x")) {
            char[] cs = new char[s.length() - 2];
            s.getChars(2, s.length(), cs, 0);
            for (int i = 0; i < cs.length; i++) {
                int fd = cs.length - i - 1, temp = Integer.parseInt(cs[i] + "", 16);
                for (int i1 = 0; i1 < fd; i1++) {
                    temp = temp * 16;
                }
                result = result + temp;
            }
            defaultValue = result;
        } else {
            defaultValue = parseInt(s, defaultValue);
        }
        return defaultValue;
    }

    private float parseFloat(String s, float defaultValue) {
        try {
            defaultValue = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
