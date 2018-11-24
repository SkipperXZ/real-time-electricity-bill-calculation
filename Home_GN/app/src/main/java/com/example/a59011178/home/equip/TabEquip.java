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
                    totalBaht.setText(String.format("%.4f",(electricUsage/1000)*8)+" Baht");
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


        }
        return rootView;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //System.out.println("--------------------------------------"+lvItem.getCheckedItemPosition());

        if (v.getId() == R.id.listView_equip) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Item item =(Item) lvItem.getAdapter().getItem(info.position);
            menu.setHeaderTitle(item.getName());
//            String[] menuItems = getResources().getStringArray(R.array.menu);
//            for (int i = 0; i < menuItems.length; i++) {
//                menu.add(Menu.NONE, i, i, menuItems[i]);
//            }
            getActivity().getMenuInflater().inflate(R.menu.equip_options, menu);
            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Item item1 = (Item) lvItem.getAdapter().getItem(info.position);
        switch (item.getItemId())
        {
            case R.id.goToEdit:
                Intent intent = new Intent(getContext(),AddItemActivity.class);

                intent.putExtra(Item.Column.ID, item1.getId());
                intent.putExtra(Item.Column.NAME, item1.getName());
                intent.putExtra(Item.Column.TYPE, item1.getType());
                intent.putExtra(Item.Column.POWER, item1.getPower());
                intent.putExtra(Item.Column.ABILITY, item1.getAbility());
                intent.putExtra(Item.Column.HRperDay, item1.getHrPerDay());
                intent.putExtra(Item.Column.DAYperMONTH, item1.getDayPerMonth());

                startActivity(intent);

//                Toast toast = Toast.makeText ( getContext(), "Edit: " + item1.getName() , Toast.LENGTH_LONG );
//                toast.show ( );

                break;
            case R.id.goToDelete:

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext());
                builder.setTitle("Delete this equipment?");
                builder.setMessage("Are you sure to delete " + item1.getName() + "?");

                final String thisID = String.valueOf(item1.getId());

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHelp.deleteItem(thisID);

                                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();

                                Intent BackpressedIntent = new Intent();
                                BackpressedIntent .setClass(getContext(),HomeActivity.class);
                                BackpressedIntent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(BackpressedIntent);
                                getActivity().finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel), null);

                builder.show();

//                Toast toast2 = Toast.makeText ( getContext(), "Delete: " + item1.getName(), Toast.LENGTH_LONG );
//                toast2.show ( );

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}



