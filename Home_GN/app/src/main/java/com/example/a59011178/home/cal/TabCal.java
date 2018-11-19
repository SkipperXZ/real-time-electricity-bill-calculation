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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;


import java.util.List;
import static android.media.CamcorderProfile.get;

public class TabCal extends Fragment implements View.OnClickListener  {

    private ListView list;
    private ItemListAdapter_cal adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    private Bundle bundle;
    private Item item, objectItme, subArrName;
    private String id, name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabcal, container, false);

//        Bundle bundle  = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString(Item.Column.ID);
        }

        list = (ListView)rootView.findViewById(R.id.listView_cal);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_cal(this.getActivity(), mItemList);
        list.setAdapter(adapter);

        registerForContextMenu(list);

        FloatingActionButton cal = (FloatingActionButton)rootView.findViewById(R.id.cal_now);
        cal.setOnClickListener(this);

        return rootView;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

//        item = mHelp.getItem(id);

        int j = list.getSelectedItemPosition() + 1 ;

//        objectItme = (Item)list.getSelectedItem();

        objectItme = (Item) list.getAdapter().getItem(j);

        name = objectItme.getName().toString();

        if (v.getId() == R.id.listView_cal) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            // menu.setHeaderTitle(item.getName());
            menu.setHeaderTitle(name);
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
//        String listItemName = item.getName();


        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), EmptyActivity.class);

        startActivity(intent);
    }
}