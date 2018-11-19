package com.example.a59011178.home.equip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.sublist_equipment,null);

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

        TextView mShowDialog = (TextView) v.findViewById(R.id.offset);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(parent.getContext());

                LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View mView =  inflater.inflate(R.layout.timeoffset, null);
                final EditText mName = (EditText) mView.findViewById(R.id.editText);
                final EditText mTime = (EditText) mView.findViewById(R.id.editText7);
                Button mAdd = (Button) mView.findViewById(R.id.button3);
                //final Intent intent = new Intent(AddTime.this,);

                //Intent intent = new Intent(parent.getContext(),AddTime2.class);
                //mContext.startActivity(intent);

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(HomeActivity.this, "success", Toast.LENGTH_LONG).show();
                        if (!mName.getText().toString().isEmpty() && !mTime.getText().toString().isEmpty()) {
                            Toast.makeText(parent.getContext(), "done", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(parent.getContext(), AddTime2.class);
                        mContext.startActivity(intent);

                    }
                });
                mBuilder.setView(mView);
                android.support.v7.app.AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        return v;
    }
}
