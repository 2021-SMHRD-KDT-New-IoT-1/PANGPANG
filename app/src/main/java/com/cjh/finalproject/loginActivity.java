package com.cjh.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    private EditText login_id;
    private EditText login_pw;
    private Button button_join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("로그인");

        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);
        button_join = findViewById(R.id.button_join);

        findViewById(R.id.button_login).setOnClickListener(v -> {
            doLogin();
        });
        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,joinActivity.class);
                loginActivity.this.startActivity(intent);
            }
        });
    }
    private void doLogin(){
        String ID = login_id.getText().toString().trim();
        String PW = login_pw.getText().toString().trim();

        if(login_id.length() == 0){
            Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_LONG).show();
            login_id.requestFocus();
            return;
        }
        if(login_pw.length() == 0){
            Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
            login_pw.requestFocus();
            return;
        }
    }
}