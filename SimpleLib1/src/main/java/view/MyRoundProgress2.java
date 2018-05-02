package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by DJl on 2016/4/14.
 * email:1554068430@qq.com
 */
public class MyRoundProgress2 extends RoundProgressBarWidthNumber {

    public MyRoundProgress2(Context context) {
        super(context);
    }

    public MyRoundProgress2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        int sweepAngleAll = 360;
        if (startAngle > 90)
            sweepAngleAll = 360 - 2 * (startAngle - 90);
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop()
                + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        // draw unreaded bar
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
//        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), startAngle,
                sweepAngleAll, false, mPaint);
        // draw reached bar
        if (getProgress() > 0) {
            mPaint.setColor(mReachedBarColor);
            mPaint.setStrokeWidth(mReachedProgressBarHeight);
            float sweepAngle = getProgress() * 1.0f / getMax() * sweepAngleAll;
            canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), startAngle,
                    sweepAngle, false, mPaint);
        }
        // draw text
        if (mIfDrawText) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mTextColor);
            canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight,
                    mPaint);
        }
        canvas.restore();

    }

}
