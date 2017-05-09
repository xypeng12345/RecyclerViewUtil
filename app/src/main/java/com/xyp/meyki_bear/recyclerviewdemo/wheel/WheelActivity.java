package com.xyp.meyki_bear.recyclerviewdemo.wheel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.count.CountWheelAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.count.CountWheelView;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.decoration.RectChooseDecoration;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.loop.LoopAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.loop.LoopWheelView;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelView;


import java.util.ArrayList;
import java.util.List;

public class WheelActivity extends AppCompatActivity {
    private RecyclerView rv1;
    private RecyclerView rv2;
    private RecyclerView rv3;
    private WheelAdapter adapter1;
    private WheelAdapter adapter2;
    private WheelAdapter adapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        initView1();
        initView2();
        initView3();
    }

    private void initView3() {
        rv3= (RecyclerView) findViewById(R.id.rv3);
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            datas.add(i + "日");
        }
        adapter3 = new CountWheelAdapter(this, datas);
        rv3.setAdapter(adapter3);
//        WheelView wheelView=new CountWheelView(this, rv3);
        WheelView wheelView=WheelFactory.getWheelView(rv3,WheelFactory.TYPE_LOOP,this);
        wheelView.setDecoration(new RectChooseDecoration());
        wheelView.setRawCount(3);
    }
    private void initView2() {
        rv2= (RecyclerView) findViewById(R.id.rv2);
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            datas.add(i + "日");
        }
        adapter2 = new CountWheelAdapter(this, datas);
        rv2.setAdapter(adapter2);
        WheelView wheelView=new CountWheelView(this,rv2);
        wheelView.setDecoration(new RectChooseDecoration());
        wheelView.setRawCount(7);
    }

    private void initView1() {
        rv1= (RecyclerView) findViewById(R.id.rv1);
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i + "日");
        }
        adapter1 = new LoopAdapter(this, datas);
        rv1.setAdapter(adapter1);
        WheelView wheelView=new LoopWheelView(this,rv1);
        wheelView.setDecoration(new RectChooseDecoration());
        wheelView.setRawCount(11);
        wheelView.setOnItemChooseListener(new WheelView.OnItemChooseListener() {
            @Override
            public void onItemChooseListener(int position) {
                Toast.makeText(WheelActivity.this, "选择了第"+position+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
