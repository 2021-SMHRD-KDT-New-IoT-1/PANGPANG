package com.cjh.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cjh.finalproject.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton led_off;
    private ImageButton rec_off;
    private ImageButton fire_off;
    private ImageButton camera_off;
    String check = "off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        led_off = findViewById(R.id.led_off);
        rec_off = findViewById(R.id.rec_off);
        fire_off = findViewById(R.id.fire_off);
        camera_off = findViewById(R.id.camera_off);


        led_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg = led_off.getBackground();
                if (check == "off") {
                    bg.setTint(Color.parseColor("#FBC02D"));
                    check = "on";
                } else {
                    bg.setTint(Color.parseColor("#FFFDE4"));
                    check = "off";
                }
            }
        });
        rec_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg2 = rec_off.getBackground();
                if (check == "off") {
                    bg2.setTint(Color.parseColor("#D50000"));
                    check = "on";
                } else {
                    bg2.setTint(Color.parseColor("#FFFDE4"));
                    check = "off";
                }
            }
        });
        fire_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg3 = fire_off.getBackground();
                if (check == "off") {
                    bg3.setTint(Color.parseColor("#D50000"));
                    check = "on";
                } else {
                    bg3.setTint(Color.parseColor("#FFFDE4"));
                    check = "off";
                }
            }
        });
        camera_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg4 = camera_off.getBackground();
                if (check == "off") {
                    bg4.setTint(Color.parseColor("#FBC02D"));
                    check = "on";
                } else {
                    bg4.setTint(Color.parseColor("#FFFDE4"));
                    check = "off";
                }
            }
        });


    }
}

