package com.example.a59011178.home.equip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;


public class TabEquip extends Fragment {

    private ListView lvItem;
    private ItemListAdapter_equip adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;




    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabequip, container, false);

        lvItem = (ListView)rootView.findViewById(R.id.listView_equip);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_equip(this.getActivity(), mItemList);
        lvItem.setAdapter(adapter);

        return rootView;


    }

}



