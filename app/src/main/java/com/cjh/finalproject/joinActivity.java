package com.cjh.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class joinActivity extends AppCompatActivity {
    private EditText join_id;
    private EditText join_pw;
    private EditText join_serialnumber;
    private Button join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_id = findViewById(R.id.login_id);
        join_pw = findViewById(R.id.join_pw);
        join_button = findViewById(R.id.join_button);
        join_serialnumber = findViewById(R.id.join_serialnumber);


    }
}