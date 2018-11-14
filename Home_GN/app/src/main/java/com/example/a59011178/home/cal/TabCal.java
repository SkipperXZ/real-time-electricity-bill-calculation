package com.example.a59011178.home.cal;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.equip.ItemListAdapter_equip;

import java.util.List;

public class TabCal extends Fragment {

    private ListView lvItem;
    private ItemListAdapter_cal adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabcal, container, false);

        lvItem = (ListView)rootView.findViewById(R.id.listView_cal);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_cal(this.getActivity(), mItemList);
        lvItem.setAdapter(adapter);

        return rootView;

    }
}