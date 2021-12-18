package com.cjh.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnview;
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnview = findViewById(R.id.bnview);
        layout = findViewById(R.id.layout);


                // 처음부터 1번 화면으로 나오게 만드는 방법
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout, new main()).commit();



        bnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new blackbox()).commit();
                }else if(item.getItemId() == R.id.tab1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new main()).commit();
                }else if(item.getItemId() == R.id.tab2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new setting()).commit();
                }
                return true;
            }
        });


    }
}

