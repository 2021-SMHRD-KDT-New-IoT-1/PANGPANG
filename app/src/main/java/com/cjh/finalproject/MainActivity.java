package com.cjh.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnview;
    private FrameLayout layout;
    private TextView tv_user;

    private String nick;

    private SharedPreferences spf;
    private String nick_spf;

    RequestQueue requestQueue; // 전송하는 통로
    StringRequest stringRequest_gas; // 전송할 데이터, 설정(get/post)등 담는 바구니!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnview = findViewById(R.id.bnview);
        layout = findViewById(R.id.layout);
        tv_user = findViewById(R.id.tv_user);

        spf = getSharedPreferences("spf", Context.MODE_PRIVATE);
        nick_spf = spf.getString("nick","nick");

        // 서버에서 닉네임 받아오는 부분
        nick = getIntent().getStringExtra("nick");

        tv_user.setText(nick_spf + "님 환영합니다!");

                // 처음부터 1번 화면으로 나오게 만드는 방법
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout, new main()).commit();



        bnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new blackbox()).commit();
                }else if(item.getItemId() == R.id.tab1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new main()).commit();
                }else if(item.getItemId() == R.id.tab3){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new setting()).commit();
                }
                return true;
            }
        });


    }
}

