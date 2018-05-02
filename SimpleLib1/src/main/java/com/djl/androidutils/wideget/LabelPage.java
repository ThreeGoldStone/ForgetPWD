package com.djl.androidutils.wideget;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.djl.androidutils.MemoryRecord;
import com.djl.androidutils.adapter.MyViewPagerAdapter;

import java.util.ArrayList;

/**
 * @author DJL E-mail:
 * @version 1.0
 * @date 2015-12-24 下午1:31:56
 * @parameter
 */
public class LabelPage implements OnClickListener {
    private ArrayList<LabelBean> labelBeans;
    private int mCurrentShowIndex;
    private ViewPager mPager;
    private ViewGroup mLayout;
    private MyViewPagerAdapter mAdapter;
    /**
     * 额外的页面滑动监听
     */
    public OnPageChangeListener pageChangeListener;

    /**
     * initWithViewPager
     *
     * @param pager
     */
    public LabelPage(ViewPager pager) {
        MemoryRecord.add(this);
        this.mPager = pager;
    }

    public LabelPage(FrameLayout fl) {
        this.mLayout = fl;
    }

    public LabelPage(RelativeLayout rl) {
        this.mLayout = rl;
    }

    public void init(Activity activity) {
        initViewPager();
        initFrameLayout();
        // TODO fragment 支持
        for (LabelBean lb : getLabelBeans()) {
            if (lb.mBarView != null) {
                lb.mBarView.setClickable(true);
                lb.mBarView.setOnClickListener(this);
            }
        }
    }

    public void init() {
        initViewPager2();
        initFrameLayout();
        // TODO fragment 支持
        for (LabelBean lb : getLabelBeans()) {
            if (lb.mBarView != null) {
                lb.mBarView.setClickable(true);
                lb.mBarView.setOnClickListener(this);
            }
        }
    }

    private void initFrameLayout() {
        if (mLayout != null) {
            for (LabelBean lb : getLabelBeans()) {
                mLayout.addView(lb.mPageView);
            }

        }

    }

    private void initViewPager() {
        if (mPager != null) {
            mAdapter = new MyViewPagerAdapter(getLabelBeans());
            mPager.setAdapter(mAdapter);
            mPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    setCurrentShow(position);
                    if (pageChangeListener != null)
                        pageChangeListener.onPageSelected(position);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    if (pageChangeListener != null)
                        pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (pageChangeListener != null)
                        pageChangeListener.onPageScrollStateChanged(state);
                }
            });
        }
    }

    private void initViewPager2() {
        if (mPager != null) {
            mAdapter = new MyViewPagerAdapter(getLabelBeans());
            mPager.setAdapter(mAdapter);
            mPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    setCurrentShow(position);
                    if (pageChangeListener != null)
                        pageChangeListener.onPageSelected(position);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    if (pageChangeListener != null)
                        pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (pageChangeListener != null)
                        pageChangeListener.onPageScrollStateChanged(state);
                }
            });
        }
    }

    public void add(LabelBean labelBean) {
        getLabelBeans().add(labelBean);
        if (mAdapter != null) {
            mAdapter.setData(labelBeans);
        }
    }

    public void remove(int index) {
        getLabelBeans().remove(index);
        if (mPager != null) {
            mPager.setAdapter(new MyViewPagerAdapter(labelBeans));
//			mAdapter.setData(labelBeans);
            int toShow = getCurrentShow();
            if (getLabelBeans().size() > 0) {
                if (toShow >= getLabelBeans().size()) {
                    toShow = toShow - 1;
                }
                toShow = Math.min(toShow, getLabelBeans().size() - 1);
                toShow = Math.max(toShow, 0);
                setCurrentShow(toShow);
            }
        }
    }

    public ArrayList<LabelBean> getLabelBeans() {
        if (labelBeans == null) {
            labelBeans = new ArrayList<>();
        }
        return labelBeans;
    }

    /**
     * 获取当前显示的位置
     *
     * @return
     */
    public int getCurrentShow() {
        return mCurrentShowIndex;
    }

    public View getView(int index, int id) {
        return labelBeans.get(index).mPageView.findViewById(id);
    }

    public View getView(int index) {
        return labelBeans.get(index).mPageView;
    }

    /**
     * 设置当前显示的位置
     *
     * @param index
     */
    public void setCurrentShow(int index) {
        // 设置Pager滚动到指定页
        if (mPager != null) {
            mPager.setCurrentItem(index);
        }
        // 设置FrameLayout的子View显示对应的页
        this.mCurrentShowIndex = index;
        for (int i = 0; i < getLabelBeans().size(); i++) {
            LabelBean labelBean = getLabelBeans().get(i);
            if (mLayout != null) {
                // labelBean.mPageView.setVisibility(View.V)
            }
            labelBean.setShow(index == i);
        }
    }

    @Override
    public void onClick(View v) {
        int index = -1;
        for (int i = 0; i < getLabelBeans().size(); i++) {
            if (getLabelBeans().get(i).mBarView.getId() == v.getId()) {
                index = i;
            }
        }
        if (index >= 0) {
            setCurrentShow(index);
        }
    }

    public void exit() {
        if (mPager != null) {
            mPager.clearOnPageChangeListeners();
            mPager.setAdapter(null);
        }
        if (getLabelBeans() != null) {
            for (LabelBean lb : getLabelBeans()) {
                if (lb.mBarView != null) {
                    lb.mBarView.setOnClickListener(null);
                }
            }
            getLabelBeans().clear();
        }
    }
}
