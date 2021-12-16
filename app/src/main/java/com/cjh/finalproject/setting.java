package com.cjh.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class setting extends Fragment {
    private TextView setting_bar1;
    private TextView setting_bar2;
    private TextView setting_bar3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting, container, false);


        setting_bar1 = (TextView) v.findViewById(R.id.setting_bar1);
        setting_bar2 = (TextView) v.findViewById(R.id.setting_bar2);
        setting_bar3 = (TextView) v.findViewById(R.id.setting_bar3);


        return v;
    }
}