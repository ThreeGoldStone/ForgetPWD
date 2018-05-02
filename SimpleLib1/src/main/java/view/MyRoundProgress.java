package view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by DJl on 2016/4/14.
 * email:1554068430@qq.com
 */
public class MyRoundProgress extends RoundProgressBarWidthNumber {
    private OnDrawListener onDrawListener;

    public MyRoundProgress(Context context) {
        super(context);
    }

    public MyRoundProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (getOnDrawListener() != null) getOnDrawListener().onDraw(getProgress());
        super.onDraw(canvas);
    }

    public OnDrawListener getOnDrawListener() {
        return onDrawListener;
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    public static interface OnDrawListener {
        void onDraw(int progress);
    }
}
