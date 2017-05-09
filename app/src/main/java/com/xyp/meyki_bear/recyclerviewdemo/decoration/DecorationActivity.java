package com.xyp.meyki_bear.recyclerviewdemo.decoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.xyp.meyki_bear.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DecorationActivity extends AppCompatActivity {

    @InjectView(R.id.rv)
    RecyclerView rv;
    private MyDecorationAdapter adapter;
    private List<CityBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);
        ButterKnife.inject(this);
        initData();
        adapter = new MyDecorationAdapter(this, list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new MyReliefDecoration(this,list));
    }

    private void initData() {
        list = new ArrayList<>();
        CityBean cityBean = new CityBean();
        cityBean.setCityName("北京");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("重庆");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("深圳");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("海口");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("陕西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("合肥");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("大连");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("东京");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("南京");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("铜川");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("宝鸡");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("山东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("米脂");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广西");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("广东");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("富县");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("延安");
        list.add(cityBean);
        cityBean = new CityBean();
        cityBean.setCityName("延川");
        list.add(cityBean);
        for (int i = 0; i < list.size(); i++) {
            CityBean cityBean1 = list.get(i);
            cityBean1.setPinyin(Pinyin.toPinyin(cityBean1.getCityName().charAt(0)));
        }
        Collections.sort(list, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean o1, CityBean o2) {
                return o1.getPinyin().compareTo(o2.getCityName());
            }
        });
    }
}
