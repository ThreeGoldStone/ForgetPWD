package com.djl.androidutils.wideget;

import android.app.Fragment;
import android.view.View;
//private int mShowTextColor = -1;
// private int mNormalTextColor = -1;
// private int mShowBgID = -1;
// private int mNormalBgID = -1;
///**
//* 设置text的颜色
//* 
//* @param showTextColor
//* @param normalTextColor
//* @return
//*/
//public LabelBean setBarTextColor(int showTextColor, int normalTextColor) {
//	this.mShowTextColor = showTextColor;
//	this.mNormalTextColor = normalTextColor;
//	return this;
//}

///**
//* 设置bar的背景id
//* 
//* @param showBgID
//* @param normalBgID
//* @return
//*/
//public LabelBean setBarBackgroundResID(int showBgID, int normalBgID) {
//	this.mShowBgID = showBgID;
//	this.mNormalBgID = normalBgID;
//	return this;
//}
public class LabelBean {
	public View mBarView;
	public View mPageView;
	public Fragment mFragment;
	private OnPageShowListener mOPSLintener;
	private boolean mIsShow;
	private Object[] mCaches;

	public LabelBean(View labelView,  View pageView,
			 OnPageShowListener mOPSLintener) {
		mBarView = labelView;
		mPageView = pageView;
		this.mOPSLintener = mOPSLintener;
	}

	public LabelBean( View labelView,  Fragment fragment,
			 OnPageShowListener mOPSLintener) {
		mBarView = labelView;
		this.mFragment = fragment;
		this.mOPSLintener = mOPSLintener;
	}

	public OnPageShowListener getmOPSLintener() {
		return mOPSLintener;
	}

	public LabelBean setmOPSLintener(OnPageShowListener mOPSLintener) {
		this.mOPSLintener = mOPSLintener;
		return this;
	}

	/** 设置该Lable是否显示 */
	public void setShow(boolean isShow) {
		// 显示状态不同时再进入
		if (mIsShow != isShow) {
			if (isShow) {
				mOPSLintener.onPageShow(this, mCaches);
			} else {
				mOPSLintener.onPageNotShow(this, mCaches);
			}
			mIsShow = isShow;
		}
	}

	/** 返回该Lable是否正在显示 */
	public boolean isShow() {
		return mIsShow;
	}

	/** 页面显示出来的回调 */
	public static interface OnPageShowListener {
		/**
		 * 页面显示出来
		 * 
		 * @param mCaches
		 */
		void onPageShow(LabelBean labelBean, Object[] caches);

		/**
		 * 页面从显示变不显示
		 * 
		 * @param mCaches
		 */
		void onPageNotShow(LabelBean labelBean, Object[] caches);

	}

	public void setCacheData(Object... caches) {
		this.mCaches = caches;
	}
}
