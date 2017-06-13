package com.xyp.meyki_bear.recyclerviewdemo.review2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyGroupAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyHeaderFooterAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/6/1 16:18
 * 修改人：meyki-bear
 * 修改时间：2017/6/1 16:18
 * 修改备注：
 */

public class Review2Activity extends AppCompatActivity {
    private List<String> strings;
    private List<List<String>> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        Button btn=(Button)findViewById(R.id.btn1);
        lists = new ArrayList<>();


        getIntent();
        RecyclerView rv1 = (RecyclerView) findViewById(R.id.rv1);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv1.setLayoutManager(layoutManager);
        final Review2Adapter r2Adapter = new Review2Adapter(this, lists);
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_string, rv1, false);
        View headerView1 = LayoutInflater.from(this).inflate(R.layout.item_string, rv1, false);
        r2Adapter.addHeaderView(headerView);
        r2Adapter.addFooterView(headerView1);

        View emptyView = LayoutInflater.from(this).inflate(R.layout.item_string_emty, rv1, false);
        r2Adapter.setEmptyView(emptyView, MyHeaderFooterAdapter.ShowWhat.SHOW_ALL);

        rv1.setAdapter(r2Adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lists.isEmpty()){
                    for (int i = 0; i < 10; i++) {
                        strings = new ArrayList<>();
                        Random random = new Random();
                        for (int j = 0; j < random.nextInt(40) + 2; j++) {
                            strings.add("第" + i);
                        }
                        lists.add(strings);
                    }
                }else{

                    lists.clear();
                }
                r2Adapter.notifyDataSetChanged();
            }
        });
        r2Adapter.setOnGroupClickListener(new MyGroupAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClickListener(int groupPosition) {
                Toast.makeText(Review2Activity.this, "点击了第" + groupPosition + "组", Toast.LENGTH_SHORT).show();
//                r2Adapter.notifyItemRangeRemoved(groupPosition, lists.get(groupPosition).size());
//                lists.remove(groupPosition);
            }
        });
        r2Adapter.setOnChildClickListener(new MyGroupAdapter.OnChildClickListener() {
            @Override
            public void onChildClickListener(int groupPosition, int childPosition) {
                Toast.makeText(Review2Activity.this, "点击了第" + groupPosition + "组的第" + childPosition + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
