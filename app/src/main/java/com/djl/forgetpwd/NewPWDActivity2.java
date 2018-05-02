package com.djl.forgetpwd;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.djl.androidutils.DJLUtils;
import com.djl.forgetpwd.adapter.NewPWDAdapter;
import com.djl.forgetpwd.bean.NameValuePairBean;
import com.djl.forgetpwd.simple_successor.MyActivity;
import com.djl.forgetpwd.view.RecyclerViewItemDecoration;

public class NewPWDActivity2 extends MyActivity implements NewPWDAdapter.OnClickListener {

    private static final int requestCode = 8888;
    private RecyclerView recyclerView;
    private static final int resultCodeChange = 88881;
    private NameValuePairBean mBean;
    private Paint paint;

    @Override
    public int contentLayoutId() {
        return R.layout.activity_new_pwd2;
    }

    @Override
    public void initData() {
        paint = new Paint();
        paint.setColor(0xffff0000);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                new AlertDialog.Builder(NewPWDActivity2.this).setSingleChoiceItems(new String[]{"证号", "证件", "文本"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        recyclerView = (RecyclerView) vFinder.getView(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, 0xff9c9c9c, 1, 0, 0));
        mBean = (NameValuePairBean) getIntent().getSerializableExtra("data");
        if (mBean == null) {
            // TODO TEST
            mBean = getData();
        }
//        else if (mBean.getSubs() == null) {
//
//            finish_();
//            return;
//        }
        recyclerView.setAdapter(new NewPWDAdapter(mBean.getSubs(), this));
    }

//    private List<NameValuePairBean> getBeen(ArrayList<NameValuePairBean> nameValuePairBeen, NameValuePairBean data) {
//        ArrayList<NameValuePairBean> been = data.getSubs();
//        data.setSubs(null);
//        nameValuePairBeen.add(data);
//        if (been != null && been.size() > 0) {
//            for (NameValuePairBean nameValuePairBean : been) {
//                nameValuePairBean.setProgress(data.getProgress() / 2);
//                getBeen(nameValuePairBeen, nameValuePairBean);
//            }
//        }
//        return nameValuePairBeen;
//    }

    private NameValuePairBean getData() {
        NameValuePairBean base = new NameValuePairBean();
        NameValuePairBean[] pairBeen = {
                new NameValuePairBean().setName("sub1Name").setValue("sub1Value"),
                new NameValuePairBean().setName("sub2Name").setValue("sub2Value"),
                new NameValuePairBean().setName("sub3Name").setValue("sub3Value").setSubs(new NameValuePairBean[]{
                        new NameValuePairBean().setName("subsub1Name").setValue("subsub1Value"),
                        new NameValuePairBean().setName("subsub2Name").setValue("subsub2Value"),
                }),
        };
        base.setName("baseName").setValue("baseValue").setSubs(pairBeen);
        return base;
    }

    @Override
    public void onClick(View v) {

    }

    public static void start(MyActivity activity, NameValuePairBean bean, int index) {
        Intent intent = new Intent(activity, NewPWDActivity2.class);
        intent.putExtra("data", bean);
        intent.putExtra("index", index);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCode) {
            switch (resultCode) {
                case resultCodeChange:
                    NameValuePairBean bean = (NameValuePairBean) data.getSerializableExtra("data");
                    int index = data.getIntExtra("index", -1);
                    if (bean != null) {
                        if (index < 0) {
                            mBean.getSubs().add(bean);
                        } else {
                            mBean.getSubs().set(index, bean);
                        }
                    }
                    break;
            }
        }

//        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(NewPWDAdapter.MyHolder holder, NameValuePairBean bean, int position, int resId) {

        switch (resId) {
            case R.id.ivCopyContent:
                Snackbar.make(recyclerView, "数据已复制", 1000).show();
                break;
            default:
//                if (bean.getSubs() == null || bean.getSubs().size() <= 0) {
//                    Snackbar.make(recyclerView, "没数据", 1000).show();
//                } else {
                start(this, bean, position);
//                }
                break;
        }


    }

    @Override
    public void onLongClick(NewPWDAdapter.MyHolder holder, final NameValuePairBean bean, final int position, int resId) {
        new AlertDialog.Builder(this).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"edit", "删除", "查看"}), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        NewPWDEditActivity.start(NewPWDActivity2.this, bean, position);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                DJLUtils.toastS(getApplicationContext(), "" + which);
                dialog.dismiss();
            }
        }).create().show();
    }
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_pwd2);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}
