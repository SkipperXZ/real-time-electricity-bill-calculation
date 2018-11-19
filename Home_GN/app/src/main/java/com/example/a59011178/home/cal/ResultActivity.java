package com.example.a59011178.home.cal;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        float result = getIntent().getExtras().getFloat("total");


        TextView bahtresult = (TextView)findViewById(R.id.bahtresult);
        bahtresult.setText(String.valueOf(result)+ "Baht");
        Button backcal = (Button)findViewById(R.id.backcal);

        backcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();

            }
        });
    }

}
