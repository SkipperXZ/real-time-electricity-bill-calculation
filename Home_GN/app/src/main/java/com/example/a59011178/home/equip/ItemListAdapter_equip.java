package com.example.a59011178.home.equip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.HomeActivity;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.timer.CountUpTimer;

import java.util.List;

public class ItemListAdapter_equip extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;
    private DatabaseHelper mDBHelper;

    public ItemListAdapter_equip(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
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

        mDBHelper = new DatabaseHelper(parent.getContext());
        final String nowID = String.valueOf(mItemList.get(position).getId());

        View v = View.inflate(mContext, R.layout.sublist_equipment, null);

        TextView itemName = (TextView) v.findViewById(R.id.name_equip);
        TextView itemPower = (TextView) v.findViewById(R.id.power_equip);

        final TextView itemMin = (TextView) v.findViewById(R.id.min_equip);
        final Switch timeSwitch = (Switch) v.findViewById(R.id.on_off);

        itemName.setText(mItemList.get(position).getName());
        itemPower.setText("(" + String.valueOf(mItemList.get(position).getPower()) + "W)");
        itemMin.setText(secToHR(mItemList.get(position).getHr()));

        v.setTag(mItemList.get(position).getId());

        final TextView mShowDialog = (TextView) v.findViewById(R.id.offset);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(parent.getContext());
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.timeoffset, null);

                final NumberPicker hour = (NumberPicker) mView.findViewById(R.id.numberPicker);
                hour.setMinValue(0);
                hour.setMaxValue(48);

                final NumberPicker minute = (NumberPicker) mView.findViewById(R.id.numberPicker1);
                minute.setMinValue(0);
                minute.setMaxValue(59);

//                Button mAdd = (Button) mView.findViewById(R.id.Plus_time);
//                Button mDelete = (Button) mView.findViewById(R.id.Delete_time);

                mBuilder.setPositiveButton("Plus time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int getSec = hrToSec(hour.getValue(),minute.getValue());
                        int databaseSec = mItemList.get(position).getHr();
                        int nowSec = getSec + databaseSec;

                        mItemList.get(position).setHr(nowSec);
                        itemMin.setText(secToHR(nowSec));

                        mDBHelper.updateHr(nowID, nowSec);

                        Toast toast = Toast.makeText(parent.getContext(), "Plus time for " + hour.getValue() + " hour and " + minute.getValue() + " minute",  Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                );

                mBuilder.setNegativeButton("Delete time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int getSec = hrToSec(hour.getValue(),minute.getValue());
                        int databaseSec = mItemList.get(position).getHr();

                        if(databaseSec < getSec){
                            Toast toast = Toast.makeText(parent.getContext(), "Time can not be negative.",  Toast.LENGTH_SHORT);
                            toast.show();

                        }else {
                            int nowSec = databaseSec - getSec;
                            mDBHelper.updateHr(nowID, nowSec);

                            mItemList.get(position).setHr(nowSec);
                            itemMin.setText(secToHR(nowSec));

                            Toast toast = Toast.makeText(parent.getContext(), "Delete time for " + hour.getValue() + " hour and " + minute.getValue() + " minute",  Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                );

//                mAdd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                mDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                AlertDialog dialog = mBuilder.create();

                mBuilder.setView(mView);
                mBuilder.show();
            }
        });

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            CountUpTimer timer;
            int hr = mItemList.get(position).getHr();

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    hr = mItemList.get(position).getHr();
                    itemMin.setText(secToHR(hr));
                    timer = new CountUpTimer(2000000000) {
                        public void onTick(int second) {
                            itemMin.setText(secToHR(hr + second));
                            mItemList.get(position).setHr(hr + timer.getSecond());
                        }

                        @Override
                        public void onFinish() {
                            mItemList.get(position).setHr(hr + timer.getSecond() + 1);
                            hr = mItemList.get(position).getHr();
                            this.start();
                        }
                    };
                    timer.start();
                } else {
                    mItemList.get(position).setHr(hr + timer.getSecond());
                    timer.cancel();
                    timer = null;
                }
            }
        });


        return v;
    }

    public int hrToSec(int hr, int min){
        int sec = (hr*3600)+(min*60);

        return sec;
    }

    public String secToHR(int sec){
        int min;
        int hour;

        min = sec / 60;
        sec -= min * 60;

        hour = min / 60;
        min -= hour * 60;

        return hour + ":" + min + ":" + sec;
    }

}
