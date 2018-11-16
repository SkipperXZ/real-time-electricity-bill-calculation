package com.example.a59011178.home.cal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.subList_calculate,null);
        TextView itemName = (TextView)v.findViewById(R.id.name_cal);
        TextView itemPower = (TextView)v.findViewById(R.id.power_cal);

        TextView HRPerDay = (TextView)v.findViewById(R.id.hrPerDay);
        TextView itemDayPerMonth = (TextView)v.findViewById(R.id.dayPerMonth);

        LinearLayout mHrPerDay = (LinearLayout)v.findViewById(R.id.hrPerDay_layout);

        itemName.setText(mItemList.get(position).getName());
        itemPower.setText(String.valueOf(mItemList.get(position).getPower()) + " WATT");

        HRPerDay.setText("Use " + String.valueOf(mItemList.get(position).getHrPerDay()) + " Hour/Day");
        itemDayPerMonth.setText("Use " + String.valueOf(mItemList.get(position).getDayPerMonth()) + " Day/Month");
        v.setTag(mItemList.get(position).getId());
        return v;
    }
}












