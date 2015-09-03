package com.weidongjian.meitu.wheelviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.weidongjian.meitu.wheelviewdemo.view.LoopListener;
import com.weidongjian.meitu.wheelviewdemo.view.LoopView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout rootview;
    private RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        rootview = (RelativeLayout) findViewById(R.id.rootview);

        LoopView loopView = new LoopView(this);
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add("item " + i);
        }
        //设置是否循环播放
        loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                Log.d("debug", "Item " + item);
            }
        });
        //设置原始数据
        loopView.setArrayList(list);
        //设置初始位置
        loopView.setPosition(5);
        //设置字体大小
        loopView.setTextSize(30);
        rootview.addView(loopView, layoutParams);

    }

}
