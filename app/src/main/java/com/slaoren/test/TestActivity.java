package com.slaoren.test;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.slaoren.R;


public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
