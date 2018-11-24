package com.example.a59011178.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class SettingActivity extends AppCompatActivity {

    private TextView      upEmail,upPassword, sEnergycharge;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


       upPassword = findViewById(R.id.textView9);
       sEnergycharge  = findViewById(R.id.textView12);
        upPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UpdatePassword.class);
                startActivity(intent);}});

        sEnergycharge.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, RegisterActivity.class);
                startActivity(intent);}});

}}


