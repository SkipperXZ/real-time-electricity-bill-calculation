package com.example.a59011178.home.edit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a59011178.home.AddItemActivity;
import com.example.a59011178.home.HomeActivity;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

public class ItemListAdapter_edit extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;

    public ItemListAdapter_edit(Context mContext, List<Item> mItemList) {
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.sublist_editequipment,null);
        TextView itemAbility = (TextView)v.findViewById(R.id.edit_ability);
        TextView itemDayPerMonth = (TextView)v.findViewById(R.id.edit_dayPerMonth);
        TextView itemHRPerDay = (TextView)v.findViewById(R.id.edit_hrPerDay);
        TextView itemName = (TextView)v.findViewById(R.id.edit_name);
        TextView itemPower = (TextView)v.findViewById(R.id.edit_power);
        TextView itemType = (TextView)v.findViewById(R.id.edit_type);

        TextView edit = (TextView)v.findViewById(R.id.goToEdit);
        TextView delete = (TextView)v.findViewById(R.id.goToDelete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( parent.getContext() , AddItemActivity.class);
                mContext.startActivity(intent);
            }
        });

        itemName.setText(mItemList.get(position).getName());
        itemAbility.setText(mItemList.get(position).getAbility());
        itemType.setText(mItemList.get(position).getType());
        itemPower.setText(String.valueOf(mItemList.get(position).getPower()) + " WATT");
        itemHRPerDay.setText("Use " + String.valueOf(mItemList.get(position).getHrPerDay()) + " Hour/Day");
        itemDayPerMonth.setText("Use " + String.valueOf(mItemList.get(position).getDayPerMonth()) + " Day/Month");

        v.setTag(mItemList.get(position).getId());


        return v;
    }
}