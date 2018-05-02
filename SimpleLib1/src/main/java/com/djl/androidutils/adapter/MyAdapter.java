package com.djl.androidutils.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/**
 * @author DJL E-mail:
 * @date 2015-9-7 上午10:48:43
 * @version 1.0
 * @parameter
 */

/**
 * @author DJL E-mail:
 * @version 1.0
 * @date 2015-6-10 下午12:17:37
 * @parameter
 */
public abstract class MyAdapter<E> extends BaseAdapter {
    /**
     * 需要填充的数据
     */
    private List<E> datas;
    /**
     * 上下文
     */
    private WeakReference<Activity> context;

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void setData(List<E> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public MyAdapter(List<E> datas, Activity context) {
        this.datas = datas;
        this.context = new WeakReference<>(context);
    }
    public List<E> getDatas() {
        return datas;
    }
    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public E getItem(int position) {
        return datas != null ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<Integer, View> holder = null;
        if (convertView == null) {
            convertView = initConvertView().view;
            // Log.i("djl", "initConvertView");
        }
        holder = (HashMap<Integer, View>) convertView.getTag();
        setView(holder, getItem(position), position);
        return convertView;
    }

    /**
     * 初始化带tag的itemView
     *
     * @param layoutid itemView的layout布局文件
     * @param viewIds  控件的ids
     * @return
     */
    protected ViewWithTag getViewWithTag(int layoutid, int... viewIds) {
        View layout = context.get().getLayoutInflater().inflate(layoutid, null);
//        View layout = View.inflate(context.get().getApplicationContext(), layoutid, null);
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < viewIds.length; i++) {
            map.put(viewIds[i], layout.findViewById(viewIds[i]));
        }
        layout.setTag(map);
        return new ViewWithTag(layout);
    }

    ;

    /**
     * 设置contentView里面空间与数据的显示关系
     *
     * @param holder   存放控件的Map
     * @param item     数据
     * @param position 位置
     */
    protected abstract void setView(HashMap<Integer, View> holder, E item, int position);

    /**
     * @return 返回带有初始tag的itemView
     * @see 例如： return getViewWithTag(context, R.layout.item, R.id.tvage,
     * R.id.tvLover, R.id.tvName);
     */
    protected abstract ViewWithTag initConvertView();

    /**
     * 创建方式：getViewWithTag();
     *
     * @author DJL E-mail:
     * @version 1.0
     * @date 2015-9-18 下午4:47:27
     * @parameter
     */
    protected class ViewWithTag {
        public ViewWithTag(View view) {
            this.view = view;
        }

        public View view;
    }
}