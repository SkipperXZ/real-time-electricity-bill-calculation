package com.example.a59011178.home.edit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private ListView lvItem;
    private ItemListAdapter_edit adapter;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editEquipment);

        lvItem = (ListView)findViewById(R.id.listView_edit);

        mHelp = new DatabaseHelper(this);
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_edit(getApplicationContext(), mItemList);
        lvItem.setAdapter(adapter);

    }
}
