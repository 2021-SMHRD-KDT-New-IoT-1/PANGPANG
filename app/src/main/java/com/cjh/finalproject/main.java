package com.cjh.finalproject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class main extends Fragment {
    private ImageButton led_btn;
    private ImageButton rec_btn;
    private TextView led_on;
    private TextView rec_on;
    String check = "off";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        led_btn = v.findViewById(R.id.led_btn);
        rec_btn = v.findViewById(R.id.rec_btn);
        led_on = v.findViewById(R.id.led_on);
        rec_on = v.findViewById(R.id.rec_on);

        led_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg = led_btn.getBackground();
                if (check == "off") {
                    bg.setTint(Color.parseColor("#FBC02D"));
                    led_on.setText("ON");
                    check = "on";
                } else {
                    bg.setTint(Color.parseColor("#E1D3D3"));
                    led_on.setText("OFF");
                    check = "off";
                }
            }
        });

        rec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg2 = rec_btn.getBackground();
                if (check == "off") {
                    bg2.setTint(Color.parseColor("#D50000"));
                    rec_on.setText("ON");
                    check = "on";
                } else {
                    bg2.setTint(Color.parseColor("#E1D3D3"));
                    rec_on.setText("OFF");
                    check = "off";
                }
            }
        });



        return v;


    }
}