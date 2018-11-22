package com.example.a59011178.home.equip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.timer.CountUpTimer;

import java.util.List;


public class TabEquip extends Fragment {

    private ListView lvItem;
    private ItemListAdapter_equip adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    private CountUpTimer realTimeElectricTimer;





    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabequip, container, false);
        final TextView totalWatt = (TextView)rootView.findViewById(R.id.totalunit);
        final TextView totalBaht = (TextView)rootView.findViewById(R.id.expens);

        lvItem = (ListView)rootView.findViewById(R.id.listView_equip);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_equip(this.getActivity(), mItemList);
        lvItem.setAdapter(adapter);

        if(realTimeElectricTimer ==null) {
            realTimeElectricTimer = new CountUpTimer(2000000000) {
                public void onTick(int second) {
                    float electricUsage = 0;
                    if (mItemList != null) {
                        for (Item e : mItemList) {
                            electricUsage += (((float)e.getHr()*(float) e.getPower())/(float)3600);
                        }
                    totalWatt.setText(String.format("%.2f",electricUsage)+" Watt");
                    totalBaht.setText(String.format("%.4f",(electricUsage/1000)*8)+" Baht");
                    }
                }

                @Override
                public void onFinish() {
                }
            };
            realTimeElectricTimer.start();
        }

        return rootView;


    }

}



