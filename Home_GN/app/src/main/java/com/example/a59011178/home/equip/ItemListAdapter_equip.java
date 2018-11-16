package com.example.a59011178.home.equip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

public class ItemListAdapter_equip extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;

    public ItemListAdapter_equip(Context mContext, List<Item> mItemList) {
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
        View v = View.inflate(mContext, R.layout.sublist_euipment,null);
        TextView itemName = (TextView)v.findViewById(R.id.name_equip);
        TextView itemPower = (TextView)v.findViewById(R.id.power_equip);
        TextView itemMin = (TextView)v.findViewById(R.id.min_equip);

//        TextView itemType = (TextView)v.findViewById(R.id.Type);
//        TextView itemAbility = (TextView)v.findViewById(R.id.Ability);

        itemName.setText(mItemList.get(position).getName());
        itemPower.setText("(" + String.valueOf(mItemList.get(position).getPower()) + "W)");
        itemMin.setText(String.valueOf(mItemList.get(position).getHr()) + " min.");

//        itemType.setText(mItemList.get(position).getType());
//        itemAbility.setText(mItemList.get(position).getAbility());

        v.setTag(mItemList.get(position).getId());
        return v;
    }
}
