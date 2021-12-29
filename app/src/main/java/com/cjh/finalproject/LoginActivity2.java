package com.cjh.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity2 extends AppCompatActivity {
    private EditText login_id1;
    private EditText login_pw2;
    private Button button_join1;
    private Button button_login1;

    private String nick;

    SharedPreferences spf;

    RequestQueue requestQueue; //전송하는 통로
    StringRequest stringRequest_login; //전송할 데이터, 설정(get/post) 등 담는 바구니!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        login_id1 = findViewById(R.id.login_id1);
        login_pw2 = findViewById(R.id.login_pw2);
        button_join1 = findViewById(R.id.button_join1);
        button_login1 = findViewById(R.id.button_login1);

        spf = getSharedPreferences("spf", Context.MODE_PRIVATE);

        //회원가입 버튼을 클릭하면~
        button_join1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 페이지로 넘어가 버렷~
                Intent intent = new Intent(LoginActivity2.this, activity_join2.class);
                startActivity(intent);
            }
        });



        //보내는애
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //로그린 서블렛 URL 주소
        String url_login = "http://project-student-6.ddns.net/pang_Com/LoginServlet";

        findViewById(R.id.button_login1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue.add(stringRequest_login);
            }
        });


        //
        stringRequest_login = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("null")) {
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity2.this,MainActivity.class).putExtra("nick", response);

                    nick = response;
                    Log.v("gas_nick: ",nick);

                    SharedPreferences.Editor edit = spf.edit();
                    edit.putString("nick",nick);
                    edit.commit();

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "ID를 확인해!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", login_id1.getText().toString());
                params.put("pw", login_pw2.getText().toString());

                return params;
            }
        };

    }


}