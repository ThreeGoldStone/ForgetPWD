package com.djl.recyclerviewdemo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DJl on 2017/9/4.
 * email:1554068430@qq.com
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        parent.
//        c.drawLine(0,0,);
//        c.drawARGB(0xff, 0xff, 0, 0);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 1);
    }
}
