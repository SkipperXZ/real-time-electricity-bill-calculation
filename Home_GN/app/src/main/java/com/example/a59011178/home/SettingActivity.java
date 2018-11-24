package com.example.a59011178.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SettingActivity extends AppCompatActivity {

    private TextView upEmail,upPassword, sEnergycharge;
    private DatabaseHelper mDBHelper = new DatabaseHelper(SettingActivity.this);
    private Item mItem = new Item();
    private List<Item> mItemList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

       upPassword = findViewById(R.id.textView9);
       sEnergycharge  = findViewById(R.id.textView12);

       mItemList = mDBHelper.getItemList();

       int i = 0;

       if (!mItemList.isEmpty()){
           i = mItemList.get(0).getTotalMoney();
        }

       sEnergycharge.setText("Now Your Energy charge is " + i + " Bath/unit");

       upPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UpdatePassword.class);
                startActivity(intent);}});

        sEnergycharge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberPicker();
            }

            private void numberPicker() {
                final NumberPicker myNumPick = new NumberPicker(SettingActivity.this);
                myNumPick.setMaxValue(20);
                myNumPick.setMinValue(1);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this).setView(myNumPick);
                builder.setTitle("Pick your energy charge/unit");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBHelper.changeUint(myNumPick.getValue());
                                sEnergycharge.setText("Now Your Energy charge is " + myNumPick.getValue() + "Bath/unit");
                            }
                        }
                );
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
                builder.show();
            }

        });

}}


