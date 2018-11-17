package com.example.a59011178.home.cal;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

public class ItemListAdapter_cal extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;


    public ItemListAdapter_cal(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
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
        TextView itemName = (TextView) v.findViewById(R.id.name_cal);
        TextView itemPower = (TextView) v.findViewById(R.id.power_cal);

        final TextView HRPerDay = (TextView) v.findViewById(R.id.hrPerDay);
        final TextView DayPerMonth = (TextView) v.findViewById(R.id.dayPerMonth);

        //LinearLayout mHrPerDay = (LinearLayout)v.findViewById(R.id.hrPerDay_layout);

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
                NumberPicker myNumPick = new NumberPicker(parent.getContext());
                myNumPick.setMaxValue(24);
                myNumPick.setMinValue(0);
                NumberPicker.OnValueChangeListener hrChange = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        HRPerDay.setText("Use " + newVal + " Hour/Day");
                    }
                };
                myNumPick.setOnValueChangedListener(hrChange);
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext()).setView(myNumPick);
                builder.setTitle("Hours you use per day");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
                NumberPicker myNumPick = new NumberPicker(parent.getContext());
                myNumPick.setMaxValue(31);
                myNumPick.setMinValue(0);
                NumberPicker.OnValueChangeListener hrChange = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        DayPerMonth.setText("Use " + newVal + " Day/Month");
                    }
                };
                myNumPick.setOnValueChangedListener(hrChange);
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext()).setView(myNumPick);
                builder.setTitle("Days you use per month");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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












