package com.xyp.meyki_bear.recyclerviewdemo.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.xyp.meyki_bear.recyclerviewdemo.R;

import java.util.ArrayList;

/**
 * 仿ViewPager实现的效果。由于ViewPager，setCurrent()，方法容易出现ANR的问题，所以尝试使用recyclerView实现
 */
public class ViewPagerActivity extends AppCompatActivity {
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        rv= (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        ArrayList<String> list=new ArrayList<String>();
        list.add("123");
        list.add("123");
        list.add("123");
        ViewPagerAdapter adapter=new ViewPagerAdapter(this,list);
        rv.setAdapter(adapter);
        PagerSnapHelper linearSnapHelper=new PagerSnapHelper();

        linearSnapHelper.attachToRecyclerView(rv);
    }
}
