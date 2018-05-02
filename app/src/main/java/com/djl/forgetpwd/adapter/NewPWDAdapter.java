package com.djl.forgetpwd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.djl.forgetpwd.R;
import com.djl.forgetpwd.bean.NameValuePairBean;

import java.util.List;

/**
 * Created by DJl on 2018/1/2.
 * email:1554068430@qq.com
 */

public class NewPWDAdapter extends RecyclerView.Adapter<NewPWDAdapter.MyHolder> {
    List<NameValuePairBean> been;
    OnClickListener onClickListener;

    public NewPWDAdapter(List<NameValuePairBean> been, OnClickListener onClickListener) {
        this.been = been;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_input_pair, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        NameValuePairBean bean = been.get(position);
        holder.name.setText(bean.getName());
        holder.content.setText(bean.getValue());
        holder.itemView.setClickable(true);
        holder.index.setText("" + position);
        holder.ivCopyContent.setClickable(true);
        holder.ivCopyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder, been.get(position), position, holder.ivCopyContent.getId());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder, been.get(position), position, 0);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onLongClick(holder, been.get(position), position, 0);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return been.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name, content, index;
        public ImageView ivCopyContent;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView) this.itemView.findViewById(R.id.etInputName);
            index = (TextView) this.itemView.findViewById(R.id.tvIndex);
            content = (TextView) itemView.findViewById(R.id.etInputContent);
            ivCopyContent = (ImageView) itemView.findViewById(R.id.ivCopyContent);
        }
    }

    public static interface OnClickListener {
        void onClick(MyHolder holder, NameValuePairBean bean, int position, int ResId);

        void onLongClick(MyHolder holder, NameValuePairBean bean, int position, int ResId);
    }
}
