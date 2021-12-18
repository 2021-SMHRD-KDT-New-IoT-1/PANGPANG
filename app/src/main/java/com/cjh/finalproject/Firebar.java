package com.cjh.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Firebar extends AppCompatActivity {
    ProgressBar pb;
    int max = 120;
    int speed = 30; // 값 올릴수록 Progress 채워지는 속도 느려짐

    int lv1 = 40, lv2 = 80;
    int coLv1 = Color.parseColor("#43A047"), coLv2 = Color.parseColor("#DAA520"), coLv3 = Color.parseColor("#800000");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebar);

        pb = findViewById(R.id.progressBar);
        pb.setProgress(70);


        new PrograssThread(max).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            pb.setProgress(msg.arg1);
            if (msg.arg1 < lv1) {
                pb.getProgressDrawable().setColorFilter(coLv1, PorterDuff.Mode.ADD);
            } else if (msg.arg1 < lv2) {
                pb.getProgressDrawable().setColorFilter(coLv2, PorterDuff.Mode.ADD);
            } else {
                pb.getProgressDrawable().setColorFilter(coLv3, PorterDuff.Mode.ADD);

            }
        }
    };

    class PrograssThread extends Thread {
        private int max;

        PrograssThread(int max) {
            this.max = max;
        }

        @Override
        public void run() {
            for (int i = 0; i < max; i++) {
                Message msg = new Message();
                msg.arg1 = i;

                try {
                    Thread.sleep(speed);
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

}