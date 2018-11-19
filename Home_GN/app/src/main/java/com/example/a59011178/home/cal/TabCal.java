package com.example.a59011178.home.cal;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;


import java.util.List;


public class TabCal extends Fragment implements View.OnClickListener  {

    private ListView lvItem;
    private ItemListAdapter_cal adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabcal, container, false);

        lvItem = (ListView) rootView.findViewById(R.id.listView_cal);
        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_cal(getActivity(), mItemList);
        lvItem.setAdapter(adapter);
        registerForContextMenu(lvItem);



        FloatingActionButton cal = (FloatingActionButton)rootView.findViewById(R.id.cal_now);

        cal.setOnClickListener(this);

        return rootView;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //System.out.println("--------------------------------------"+lvItem.getCheckedItemPosition());

        if (v.getId() == R.id.listView_cal) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Item item =(Item) lvItem.getAdapter().getItem(info.position);
            menu.setHeaderTitle(item.getName());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        startActivity(intent);
    }
}