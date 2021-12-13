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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 처음부터 1번 화면으로 나오게 만드는 방법
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout, new main()).commit();





    }
}

