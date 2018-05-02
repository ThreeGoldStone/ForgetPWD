package com.djl.androidutils.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.djl.androidutils.wideget.LabelBean;

import java.util.List;

/**
 * 
 * 
 */
public class MyViewPagerAdapter extends PagerAdapter {

	// 界面列表
	public List<LabelBean> lableBeans;

	public MyViewPagerAdapter(List<LabelBean> views) {
		this.lableBeans = views;
	}

	public void setData(List<LabelBean> views) {
		this.lableBeans = views;
		notifyDataSetChanged();
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (lableBeans != null) {
			return lableBeans.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View container, int index) {
		if (lableBeans.size() <= 0)
			return null;
		((ViewPager) container).addView(lableBeans.get(index).mPageView, 0);
		// lableBeans.get(index).getmOPSLintener().onPageInit(lableBeans.get(index));
		return lableBeans.get(index).mPageView;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (lableBeans.size() > 0) {
			((ViewPager) container).removeView(lableBeans.get(position).mPageView);
		}
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}
