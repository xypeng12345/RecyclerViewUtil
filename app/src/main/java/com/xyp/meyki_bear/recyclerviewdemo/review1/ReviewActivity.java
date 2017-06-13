package com.xyp.meyki_bear.recyclerviewdemo.review1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/5/31 17:58
 * 修改人：meyki-bear
 * 修改时间：2017/5/31 17:58
 * 修改备注：
 */

public class ReviewActivity extends AppCompatActivity {
    private List<String> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            lists.add("第" + i);
        }
        getIntent();
        RecyclerView rv1 = (RecyclerView) findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        R2Adapter r2Adapter = new R2Adapter(this, lists);
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_string, rv1, false);
        View headerView1 = LayoutInflater.from(this).inflate(R.layout.item_string, rv1, false);
        r2Adapter.addHeaderView(headerView);
        r2Adapter.addFooterView(headerView1);
        rv1.setAdapter(r2Adapter);
        r2Adapter.setOnItemClickListener(new MyBaseRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(ReviewActivity.this, "点击了第"+position+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
