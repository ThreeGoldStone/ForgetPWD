package com.djl.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item " + i);
        }
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this);
        adapter.setData(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btOrientation:
//                LinearLayoutManager layout = new LinearLayoutManager(this);
//                layout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                } else {
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                }
                break;
        }
    }
}
