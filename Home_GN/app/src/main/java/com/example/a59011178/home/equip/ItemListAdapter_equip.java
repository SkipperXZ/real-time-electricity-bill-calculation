package com.example.a59011178.home.equip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a59011178.home.HomeActivity;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.timer.CountUpTimer;

import java.util.List;
import java.util.ResourceBundle;

public class ItemListAdapter_equip extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;
    CountUpTimer timer;
    TimePicker timePicker;

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

        View v = View.inflate(mContext,R.layout.sublist_equipment,null);

        TextView itemName = (TextView)v.findViewById(R.id.name_equip);
        TextView itemPower = (TextView)v.findViewById(R.id.power_equip);
        final TextView itemMin = (TextView)v.findViewById(R.id.min_equip);
        final Switch   timeSwitch =(Switch)v.findViewById(R.id.on_off);

        itemName.setText(mItemList.get(position).getName());
        itemPower.setText("(" + String.valueOf(mItemList.get(position).getPower()) + "W)");
        itemMin.setText(String.valueOf(mItemList.get(position).getHr()) + " min.");

        v.setTag(mItemList.get(position).getId());

        TextView mShowDialog = (TextView) v.findViewById(R.id.offset);
        mShowDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(parent.getContext());

                LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            View mView =  inflater.inflate(R.layout.timeoffset, null);

          final TimePicker timePicker = (TimePicker)mView.findViewById(R.id.timePicker);
                timePicker.setIs24HourView(true);
                timePicker.isShown();

               Button mAdd = (Button) mView.findViewById(R.id.button3);
//                final Intent intent = new Intent(AddTime.this,);
//
//                Intent intent = new Intent(parent.getContext(),AddTime2.class);
//                mContext.startActivity(intent);
//
                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                   public void onClick(View v) {
                        //Toast.makeText(HomeActivity.this, "success", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(parent.getContext(), HomeActivity.class);
                       mContext.startActivity(intent);

                    }
                });
             mBuilder.setView(mView);
             android.support.v7.app.AlertDialog dialog = mBuilder.create();
              dialog.show();

            }
        });

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = position;
                    final int hr = mItemList.get(position).getHr();
                    if(isChecked) {

                        itemMin.setText(String.valueOf(hr));
                        timer = new CountUpTimer( 1000000000,  mItemList.get(position)) {
                            public void onTick(int second) {
                                itemMin.setText(String.valueOf(timer.getInitTime() + second));
                            }
                        };
                        timer.start();
                    }
                    else{
                        if(timer != null){
                            mItemList.get(position).setHr(timer.getInitTime() + timer.getSecond());
                            timer.cancel();
                        }
                    }
            }
        });


      return v;
    }


}
