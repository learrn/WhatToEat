package com.whattoeat.whattoeat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "WhatEat";
    private final String foodListKey = "food_list";
    SharedPreferences sp;
    TextView mText;
    TextView mEat;
    Button mButton;
    List<String> foodList;
    Boolean isStart = false;
    Random random = new Random();
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getFoodFromList();
            handler.postDelayed(this, 100);
        }
    };
    EditText mFoodListText;
    String str;

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
        sp = getPreferences(Activity.MODE_PRIVATE);
        str = sp.getString(foodListKey,getString(R.string.default_food_list));
        foodList = new ArrayList<>();
        Collections.addAll(foodList, str.split(" "));
        Log.d(TAG, foodList.toString());
    }

    @Override
    public void onClick(View v) {
        if (v == mButton) {
            if (!isStart) {
                isStart = true;
                mButton.setText("stop");
                handler.post(runnable);
            } else {
                isStart = false;
                mButton.setText("restart");
                handler.removeCallbacks(runnable);
            }
        }
    }

    //随机显示食物列表里的值
    private void getFoodFromList() {
        if (!foodList.isEmpty()) {
            int index;
            index = random.nextInt(foodList.size());
            mEat.setText(foodList.get(index));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.foodList:
                showFoodListDialog();
                break;
            default:
                break;
        }
        return true;
    }

    //创建食物列表Dialog
    private void showFoodListDialog() {
        AlertDialog.Builder foodListBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_food_list, null);
        mFoodListText = (EditText) dialogView.findViewById(R.id.food_list_text);
        mFoodListText.setText(str);
        foodListBuilder.setTitle(R.string.food_list)
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(mFoodListText.getText().toString())) {
                            SharedPreferences.Editor editor = sp.edit();
                            str = mFoodListText.getText().toString();
                            foodList.clear();
                            Collections.addAll(foodList, str.split(" "));
                            editor.putString(foodListKey,str);
                            editor.apply();
                        }
                    }
                })
                .setNegativeButton("CanCel", null);
        foodListBuilder.create().show();
    }
}
