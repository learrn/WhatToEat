package com.whattoeat.whattoeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "WhatEat";
    TextView mText;
    TextView mEat;
    Button mButton;
    List<String> foodList;
    Boolean isStart = false;
    Random random = new Random();
    String str = "盖浇饭 砂锅 大排档 米线 满汉全席 西餐 麻辣烫 自助餐 炒面 快餐 水果 西北风 馄饨 火锅 烧烤 泡面 速冻水饺 日本料理 涮羊肉 味千拉面 肯德基 面包 扬州炒饭 自助餐 茶餐厅 海底捞 咖啡 比萨 麦当劳 兰州拉面 沙县小吃 烤鱼 海鲜 铁板烧 韩国料理 粥 快餐 东南亚菜 甜点 农家菜 川菜 粤菜 湘菜 本帮菜 竹笋烤肉";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.mText);
        mEat = (TextView) findViewById(R.id.mEat);
        mButton = (Button) findViewById(R.id.mButton);
        mButton.setOnClickListener(this);
        initFoodList();
    }

    private void initFoodList() {
        foodList =new ArrayList<>();
        Collections.addAll(foodList, str.split(" "));
        Log.d(TAG,foodList.toString());
    }

    @Override
    public void onClick(View v) {
        if (v == mButton) {
            if (!isStart){
                isStart = true;
                mButton.setText("stop");
                getFoodFromList();
            }else {
                isStart = false;
                mButton.setText("restart");
            }
        }
    }

    private void getFoodFromList(){
        if (foodList.isEmpty()){
            return;
        }else {
            int index;
            while (isStart){
                index = random.nextInt(foodList.size());
                mEat.setText(foodList.get(index));
            }
        }
    }


}
