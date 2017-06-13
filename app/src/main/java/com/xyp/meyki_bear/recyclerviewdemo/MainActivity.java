package com.xyp.meyki_bear.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xyp.meyki_bear.recyclerviewdemo.adapter.AdapterActivity;
import com.xyp.meyki_bear.recyclerviewdemo.decoration.DecorationActivity;
import com.xyp.meyki_bear.recyclerviewdemo.layout_manager.LayoutActivity;
import com.xyp.meyki_bear.recyclerviewdemo.review1.ReviewActivity;
import com.xyp.meyki_bear.recyclerviewdemo.review2.Review2Activity;
import com.xyp.meyki_bear.recyclerviewdemo.viewpager.ViewPagerActivity;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.WheelActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.lv)
    ListView lv;

    private ArrayList<String> lists;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initData();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    private void initData() {
        lists = new ArrayList<>();
        lists.add("通用的适配adapter:MyBaseRecyclerViewAdapter");
        lists.add("布局管理器:layoutManager");
        lists.add("绘制间隔线的工具:ItemDecoration");
        lists.add("滚轮选择器");
        lists.add("频道管理");
        lists.add("仿照ViewPager");
        lists.add("通用适配重写1，头尾布局");
        lists.add("通用适配重写2，多布局效果");
        ViewPager v=new ViewPager(this);
        v.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=null;
        switch(position){
            case 0:
                intent=new Intent(this,AdapterActivity.class);
                break;
            case 1:
                intent=new Intent(this,LayoutActivity.class);
                break;
            case 2:
                intent=new Intent(this,DecorationActivity.class);
                break;
            case 3:
                intent=new Intent(this,WheelActivity.class);
                break;
            case 4:
                intent=new Intent(this,ChannelManagerActivity.class);
                break;
            case 5:
                intent=new Intent(this,ViewPagerActivity.class);
                break;
            case 6:
                intent=new Intent(this, ReviewActivity.class);
                break;
            case 7:
                intent=new Intent(this, Review2Activity.class);
                break;
        }
        startActivity(intent);
    }
}
