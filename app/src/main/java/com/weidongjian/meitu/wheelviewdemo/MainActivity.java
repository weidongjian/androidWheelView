package com.weidongjian.meitu.wheelviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.weidongjian.meitu.wheelviewdemo.view.LoopView;
import com.weidongjian.meitu.wheelviewdemo.view.OnItemSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoopView loopView = (LoopView) findViewById(R.id.loopView);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("item " + i);
        }
        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(MainActivity.this, "item " + index, Toast.LENGTH_SHORT).show();
            }
        });
        //设置原始数据
        loopView.setItems(list);
        //设置初始位置
        //设置字体大小
        loopView.setTextSize(20);
    }

}
