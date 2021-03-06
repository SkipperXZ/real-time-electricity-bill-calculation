package com.example.a59011178.home.cal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

public class ItemListAdapter_cal extends BaseAdapter {

    LayoutInflater li;

    private Context mContext;
    private List<Item> mItemList;
    private DatabaseHelper mDBHelper;

    public ItemListAdapter_cal(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.sublist_calculate, null);

        mDBHelper = new DatabaseHelper(parent.getContext());

        TextView itemName = (TextView)v.findViewById(R.id.name_cal);
        TextView itemPower = (TextView)v.findViewById(R.id.power_cal);

        final TextView HRPerDay = (TextView)v.findViewById(R.id.hrPerDay);
        final TextView DayPerMonth = (TextView)v.findViewById(R.id.dayPerMonth);

        final String nowID = String.valueOf(mItemList.get(position).getId());

        itemName.setText(mItemList.get(position).getName());
        itemPower.setText(String.valueOf(mItemList.get(position).getPower()) + " WATT");

        HRPerDay.setText("Use " + String.valueOf(mItemList.get(position).getHrPerDay()) + " Hour/Day");
        DayPerMonth.setText("Use " + String.valueOf(mItemList.get(position).getDayPerMonth()) + " Day/Month");

        HRPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerHr();
            }

            private void numberPickerHr() {
                final NumberPicker myNumPick = new NumberPicker(parent.getContext());
                myNumPick.setMaxValue(24);
                myNumPick.setMinValue(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext()).setView(myNumPick);
                builder.setTitle("Hours you use per day");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBHelper.updateHRperDay( nowID, myNumPick.getValue());
                                HRPerDay.setText("Use " + myNumPick.getValue() + " Hour/Day");
                            }
                        }
                );
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                );
                builder.show();
            }
        });

        DayPerMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPickerDay();
            }

            private void numberPickerDay() {
                final NumberPicker myNumPick = new NumberPicker(parent.getContext());
                myNumPick.setMaxValue(31);
                myNumPick.setMinValue(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext()).setView(myNumPick);
                builder.setTitle("Days you use per month");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBHelper.updateDayPerMonth( nowID, myNumPick.getValue());
                                DayPerMonth.setText("Use " + myNumPick.getValue() + " Day/Month");
                            }
                        }
                );
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                );
                builder.show();
            }
        });

        v.setTag(mItemList.get(position).getId());
        return v;
    }

}












