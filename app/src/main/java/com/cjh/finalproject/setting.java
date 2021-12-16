package com.cjh.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class setting extends Fragment {
    private TextView set_logout;   // 세팅 로그아웃
    private TextView set_modify;   // 세팅 회원정보수정
    private TextView set_regist;   // 세팅 기기등록

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_setting, container, false);


        set_logout = v.findViewById(R.id.set_logout);
        set_modify = v.findViewById(R.id.set_modify);
        set_regist = v.findViewById(R.id.set_regist);


        // 세팅.xml에서 로그아웃텍스를 눌렀을때 로그인페이지로 이동
        set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),loginActivity.class);
                setting.this.startActivity(intent);
            }
        });

        return v;



    }
}