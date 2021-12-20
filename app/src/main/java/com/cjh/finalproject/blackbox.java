package com.cjh.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class blackbox extends Fragment {
    ArrayList<DataVO> data = new ArrayList<>();
    private ListView blackbox_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blackbox, container, false);

        blackbox_data = (ListView) v.findViewById(R.id.blackbox_data);

        data.add(new DataVO(R.drawable.img1, "1", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img2, "2", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img3, "3", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));
        data.add(new DataVO(R.drawable.img4, "4", "2021년12월24일 13시24분"));

        DataAdapter adapter = new DataAdapter(getActivity(),R.layout.data, data);
        blackbox_data.setAdapter(adapter);

        return v;
    }
}