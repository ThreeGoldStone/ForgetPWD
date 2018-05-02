package com.djl.androidutils.simple;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by DJl on 2017/2/14.
 * email:1554068430@qq.com
 */

public class SimpleViewFinder {
    private HashMap<Integer, View> mViews;
    private Activity activity;
    private View contentView;

    public SimpleViewFinder(Activity activity) {
        this.activity = activity;
    }

    public SimpleViewFinder(View contentView) {
        this.contentView = contentView;
    }

    public TextView getTextView(int id) {
        return (TextView) getView(id);
    }

    public Button getButton(int id) {
        return (Button) getView(id);
    }

    public EditText getEditText(int id) {
        return (EditText) getView(id);
    }

    public ProgressBar getProgressBar(int id) {
        return (ProgressBar) getView(id);
    }

    public ImageView getImageView(int id) {
        return (ImageView) getView(id);
    }

    public ViewPager getViewPager(int id) {
        return (ViewPager) getView(id);
    }

    public Spinner getSpinner(int id) {
        return (Spinner) getView(id);
    }

    public CheckBox getCheckBox(int id) {
        return (CheckBox) getView(id);
    }

    public GridView getGridView(int id) {
        return (GridView) getView(id);
    }

    public ListView getListView(int id) {
        return (ListView) getView(id);
    }

    public LinearLayout getLinearLayout(int id) {
        return (LinearLayout) getView(id);
    }

    public RelativeLayout getRelativeLayout(int id) {
        return (RelativeLayout) getView(id);
    }

    public TableLayout getTableLayout(int id) {
        return (TableLayout) getView(id);
    }

    public RadioButton getRadioButton(int id) {
        return (RadioButton) getView(id);
    }

    public RadioGroup getRadioGroup(int id) {
        return (RadioGroup) getView(id);
    }

    public WebView getWebView(int id) {
        return (WebView) getView(id);
    }

    public View getView(int id) {
        if (mViews == null) {
            mViews = new HashMap<>();
        }
        if ((!mViews.containsKey(id)) || mViews.get(id) == null) {
            View view = null;
            if (contentView != null) {
                view = contentView.findViewById(id);
            } else if (activity != null) {
                view = activity.findViewById(id);
            }
            mViews.put(id, view);
        }
        return mViews.get(id);
    }

}
