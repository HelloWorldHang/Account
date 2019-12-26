package com.example.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.MyPackage;

public class baobiao extends AppCompatActivity {
    private PieChart mPieChart;
    private List<PieData> pieData;
    public static final int COUNT = 1;//连续绘制
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baobiao);

        mPieChart = f(R.id.mPieChart);
        pieData = new ArrayList<>();
        MyPackage myPackage = new MyPackage(baobiao.this);
        float consumeSum = 0;
        Map<String, Float> map = null;
        /*for (int i = 0; i < 6; i++) {
            pieData.add(new PieData("text" + i, (float) Math.random() * 90));
        }*/
        Intent intent = getIntent();
        if (intent.getStringExtra("flag").equals("day")){
            consumeSum = myPackage.getTodayConsumeSum();
            map = myPackage.getTodayBaoBiao();
        }else {
            consumeSum = myPackage.getConsumeSum();
            map = myPackage.getBaoBiao();
        }
        for(Map.Entry<String, Float> entry : map.entrySet()){
            String txt = entry.getKey();
            Float money = entry.getValue();
            txt += -money+"元";
            pieData.add(new PieData(txt, (float)money/consumeSum));
        }
        mPieChart.setData(pieData, COUNT);
    }

    public <T extends View> T f(int id) {
        return (T) findViewById(id);
    }
}
