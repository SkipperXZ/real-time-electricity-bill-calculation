package com.example.a59011178.home.equip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59011178.home.AddItemActivity;
import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.HomeActivity;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.timer.CountUpTimer;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.List;


public class TabEquip extends Fragment implements View.OnClickListener {

    private ListView lvItem;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    private ItemListAdapter_equip adapter;

    private CountUpTimer realTimeElectricTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabequip, container, false);

        final TextView totalWatt = (TextView)rootView.findViewById(R.id.totalunit);
        final TextView totalBaht = (TextView)rootView.findViewById(R.id.expens);



        lvItem = (ListView)rootView.findViewById(R.id.listView_equip);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_equip(this.getActivity(), mItemList);

        if (!mItemList.isEmpty()){
            lvItem.setAdapter(adapter);
        }

        if(realTimeElectricTimer ==null) {
            realTimeElectricTimer = new CountUpTimer(2000000000) {
                public void onTick(int second) {
                    float electricUsage = 0;
                    if (mItemList != null) {
                        for (Item e : mItemList) {
                            electricUsage += (((float)e.getHr()*(float) e.getPower())/(float)3600);
                        }

                    totalWatt.setText(String.format("%.2f",electricUsage)+" Watt");
                    totalBaht.setText(String.format("%.4f",(electricUsage/1000)*mItemList.get(0).getTotalMoney())+" Baht");
                    }

                }

                @Override
                public void onFinish() {
                }
            };
            realTimeElectricTimer.start();
        }

        for (Item e:mItemList) {
            if (Boolean.parseBoolean(e.getState())) {
                e.setHr(e.getHrLastOn() + (int) Duration.between(LocalDateTime.parse(e.getTimeLastOn()), LocalDateTime.now()).getSeconds());
            }
            if(e.getAbility().equals("Time set")) {
                if (LocalTime.now().isAfter(LocalTime.parse(e.getTime_on())) && LocalTime.now().isBefore(LocalTime.parse(e.getTime_off()))&& !Boolean.parseBoolean(e.getState())) {
                    e.setState("true");
                } else if (LocalTime.now().isAfter(LocalTime.parse(e.getTime_off())) && Boolean.parseBoolean(e.getState())) {
                    e.setState("false");
                }
            }


        }
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}



