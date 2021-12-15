package com.cjh.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class blackbox_data extends AppCompatActivity {
    ArrayList<DataVO> data = new ArrayList<>();
    private ListView blackbox_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blackbox);

        blackbox_data = (ListView) findViewById(R.id.blackbox_data);

        data.add(new DataVO(R.drawable.img1, "최진혁", "ㅇㅇ"));
        data.add(new DataVO(R.drawable.img2, "최진혁", "ㅇㅇ"));
        data.add(new DataVO(R.drawable.img3, "최진혁", "ㅇㅇ"));
        data.add(new DataVO(R.drawable.img4, "최진혁", "ㅇㅇ"));

        DataAdapter adapter = new DataAdapter(getApplicationContext(),R.layout.data, data);
        blackbox_data.setAdapter(adapter);

    }
}