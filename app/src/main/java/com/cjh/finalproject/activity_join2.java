package com.cjh.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class activity_join2 extends AppCompatActivity {
    EditText join_id1;
    EditText join_pw1;
    EditText join_nickname1;


    RequestQueue requestQueue; //전송하는 통로
    StringRequest stringRequest_join; //전송할 데이터, 설정(get/post) 등 담는 바구니!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        join_id1 = findViewById(R.id.join_id1);
        join_pw1 = findViewById(R.id.join_pw1);
        join_nickname1 = findViewById(R.id.join_nickname1);


        // 1.통로생성~
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL String 변수에 저장
        String url_join = "http://172.30.1.28:8082/MemberServer2/JoinServlet";

        // 3. StringRequest 만들기~
        // - 매개변수 4개~, 1:전송방식, 2:url, 3:응답리스너, 4:에러리스너
        stringRequest_join = new StringRequest(Request.Method.POST, url_join, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답을 처리하는 곳
                //회원가입 성공했으면 성공! 이라고 Toast메세지 띄우기
                //실패시 ID를 다시 확인하세요 라고 Toast메세지 띄우기
                if(response.equals("1")){
                    Toast.makeText(getApplicationContext(),"회원가입 성공!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_join2.this, LoginActivity2.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "ID를 확인해!!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 에러 찍어보기
                Log.v("asdf", error.toString());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", join_id1.getText().toString());
                params.put("pw", join_pw1.getText().toString());
                params.put("nick", join_nickname1.getText().toString());

                return params;
            }
        };

        findViewById(R.id.join_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest_join);
            }
        });



    }
}