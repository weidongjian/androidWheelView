package com.weidongjian.meitu.wheelviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.weidongjian.meitu.wheelviewdemo.view.LoopView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout rootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootview = (RelativeLayout) findViewById(R.id.rootview);

        LoopView loopView = new LoopView(this);
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add("item " + i);
        }
        loopView.a(list);

        rootview.addView(loopView);
    }

}
