package com.djl.forgetpwd.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.djl.forgetpwd.R;
import com.djl.forgetpwd.bean.PictureContentBean;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PictureContentBean} and makes a call to the
 * specified {@link OnItemClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPwdDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyPwdDetailRecyclerViewAdapter.ViewHolder> {

    private final List<PictureContentBean> mValues;
    private final OnItemClickListener mListener;

    public MyPwdDetailRecyclerViewAdapter(List<PictureContentBean> items, OnItemClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_pwd_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        boolean edit = holder.mItem.isEdit();
//        holder.mPic.setText(mValues.get(position).id);
        holder.tvTitle.setText(holder.mItem.getTitle());
        holder.mCreateTime.setText(holder.mItem.getCreateTime());
        // 编辑模式显示编辑状态下的文本
        holder.watchContent(edit);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemClick(holder, position, v);
                }
            }
        };
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener)
                    mListener.onItemLongClick(holder, position, v);
                return true;
            }
        };
        holder.mCancelBt.setOnClickListener(onClickListener);
        holder.mEditBt.setOnClickListener(onClickListener);
        holder.mSaveBt.setOnClickListener(onClickListener);
        holder.mCopyBt.setOnClickListener(onClickListener);
        holder.mDeleteBt.setOnClickListener(onClickListener);
        holder.mView.setOnClickListener(onClickListener);
        holder.mView.setOnLongClickListener(onLongClickListener);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        public View mView;
        public Button mCopyBt;
        public Button mEditBt;
        public Button mSaveBt;
        public Button mCancelBt;
        public Button mDeleteBt;
        public TextView mCreateTime;
        public EditText mContent;
        public EditText tvTitle;
        public LinearLayout llItemNewPwdDetailsEditBar;
        public PictureContentBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCreateTime = view.findViewById(R.id.tvCreateTime);
            mCancelBt = view.findViewById(R.id.btCancel);
            mDeleteBt = view.findViewById(R.id.btDelete);
            mCopyBt = view.findViewById(R.id.btCopy);
            mEditBt = view.findViewById(R.id.btEdit);
            mSaveBt = view.findViewById(R.id.tvSave);
            mContent = view.findViewById(R.id.etContent);
            tvTitle = view.findViewById(R.id.tvTitle);
            llItemNewPwdDetailsEditBar = view.findViewById(R.id.llItemNewPwdDetailsEditBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContent.getText() + "'";
        }

        /**
         * 编辑状态下监听content内容的变化，并保存到temporaryContent
         *
         * @param edit
         */
        public void watchContent(boolean edit) {

            tvTitle.setEnabled(edit);
            mContent.setEnabled(edit);
            if (edit) {
                mContent.addTextChangedListener(this);
                mContent.requestFocus();
                mContent.setSelection(mContent.getText().toString().length());
            } else {
                // 清除edittext上的焦点
                mEditBt.requestFocus();
                mContent.removeTextChangedListener(this);
                mItem.setTemporaryContent("");
            }
            mContent.setText(edit ? mItem.getTemporaryContent() : mItem.getContent());
            llItemNewPwdDetailsEditBar.setVisibility(edit ? View.VISIBLE : View.GONE);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mItem.setTemporaryContent(s.toString());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public static interface OnItemClickListener {
        // TODO: Update argument type and name
        void onItemClick(ViewHolder holder, int position, View clickView);

        void onItemLongClick(ViewHolder holder, int position, View clickView);
    }
}
