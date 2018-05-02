package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 当textview设置了固定宽并且宽度不够显示内容时，会自动修改字体大小，使之放下
 * Created by DJl on 2016/4/28.
 * email:1554068430@qq.com
 */
public class AdustTextSizeByWidthTextView extends TextView {

    private Paint p;
    private int widthMeasureSpec;
    private int heightMeasureSpec;
    private boolean needReSize = true;

    public AdustTextSizeByWidthTextView(Context context) {
        super(context);
    }

    public float DefaultTextSize, currentTextSize, miniTextSize = 15,maxWidth;
    public static String TAG = "djl";

    public AdustTextSizeByWidthTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DefaultTextSize = getTextSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        super.onMeasure(this.widthMeasureSpec, this.heightMeasureSpec);
//        widthMode = widthMeasureSpec;
//        Log.i(TAG, "onMeasure: widthMode+" + widthMode);
//        if (widthMode > 0) {
//            currentTextSize = DefaultTextSize;
//            String text = getText().toString();
//            ajustTextSize(text);
//            Log.i(TAG, "onMeasure: text=" + text);
//            setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize);
//        }
//        Log.i(TAG, "final: " + getTextSize());
    }


    private void ajustTextSize(String text) {
        Log.i(TAG, "ajustTextSize: " + currentTextSize);
        float width = getWidth() - getPaddingRight() - getPaddingLeft();
        currentTextSize = Math.min(width, currentTextSize);
        Log.i(TAG, "ajustTextSize: width=" + width);
        if (p == null) {
            p = new Paint();
        }
        p.setTextSize(currentTextSize);
        p.setTypeface(getTypeface());
        float textWidth = p.measureText(text);
        if (textWidth > width) {
            currentTextSize = currentTextSize - 1;
            if (currentTextSize > miniTextSize)
                ajustTextSize(text);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lines = getLineCount();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            lines = getMaxLines();
        }
        if (lines == 1 && needReSize) {
            needReSize = false;
            currentTextSize = DefaultTextSize;
            ajustTextSize(getText().toString());
            setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        needReSize = true;
        super.setText(text, type);
    }
}

