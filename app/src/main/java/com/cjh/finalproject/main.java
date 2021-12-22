package com.cjh.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


public class main extends Fragment {
    private ImageButton btn_led;
    private ImageButton btn_rec;
    private TextView tv_led;
    private TextView tv_rec;
    private TextView tv_progress;
    private TextView tv_COdt;
    String check = "off";
    ProgressBar pb;
    int max = 120;
    int speed = 30; // 값 올릴수록 Progress 채워지는 속도 느려짐

    int lv1 = 40, lv2 = 80;
    int coLv1 = Color.parseColor("#C6FF00"), coLv2 = Color.parseColor("#FFFF00"), coLv3 = Color.parseColor("#F44336");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main2, container, false);

        btn_led = v.findViewById(R.id.btn_led);
        btn_rec = v.findViewById(R.id.btn_rec);
        tv_led = v.findViewById(R.id.tv_led);
        tv_rec = v.findViewById(R.id.tv_rec);
        tv_progress = v.findViewById(R.id.tv_progress);
        tv_COdt = v.findViewById(R.id.tv_COdt);
        pb = (ProgressBar) v.findViewById(R.id.progress_bar);
        pb.setProgress(70);

        new PrograssThread(max).start();

        btn_led.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg = btn_led.getBackground();
                if (check == "off") {
                    bg.setTint(Color.parseColor("#FBC02D"));
                    tv_led.setText("ON");
                    tv_led.setTextColor(Color.parseColor("#FBC02D"));
                    check = "on";

                } else {
                    bg.setTint(Color.parseColor("#FFFFFF"));
                    tv_led.setText("OFF");
                    tv_led.setTextColor(Color.parseColor("#FFFFFF"));
                    check = "off";
                }
            }
        });

        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg2 = btn_rec.getBackground();
                if (check == "off") {
                    bg2.setTint(Color.parseColor("#D50000"));
                    tv_rec.setText("ON");
                    tv_rec.setTextColor(Color.parseColor("#D50000"));
                    check = "on";
                } else {
                    bg2.setTint(Color.parseColor("#FFFFFF"));
                    tv_rec.setText("OFF");
                    tv_rec.setTextColor(Color.parseColor("#FFFFFF"));
                    check = "off";
                }
            }
        });

        return v;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            pb.setProgress(msg.arg1);
            if (msg.arg1 < lv1) {
                pb.getProgressDrawable().setColorFilter(coLv1, PorterDuff.Mode.ADD);
            } else if (msg.arg1 < lv2) {
                pb.getProgressDrawable().setColorFilter(coLv2, PorterDuff.Mode.ADD);
                tv_progress.setText("주의");
                tv_COdt.setText("500ppm");
            } else {
                pb.getProgressDrawable().setColorFilter(coLv3, PorterDuff.Mode.ADD);
                tv_progress.setText("위험");
                tv_COdt.setText("1000ppm");
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