package com.example.a59011178.home.cal;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import com.example.a59011178.home.AddItemActivity;
import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.HomeActivity;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.example.a59011178.home.equip.AddTime2;


import java.util.List;


public class TabCal extends Fragment implements View.OnClickListener  {

    private ListView lvItem;
    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    private ItemListAdapter_cal adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabcal, container, false);

        lvItem = (ListView) rootView.findViewById(R.id.listView_cal);
        mHelp = new DatabaseHelper(this.getContext());
        mItemList = mHelp.getItemList();

        adapter = new ItemListAdapter_cal(this.getActivity(), mItemList);
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

        float result = calculatenow();

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView =  inflater.inflate(R.layout.activity_result, null);

        final TextView mText = (TextView) mView.findViewById(R.id.textresult);
        final TextView mResult = (TextView) mView.findViewById(R.id.bahtresult);

       // Button btnOK = (Button) mView.findViewById(R.id.backcal);

        mResult.setText(String.valueOf(result)+" Baht");
       // AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AppTheme));
       // builder.setTitle(R.string.result_title);
       // mBuilder.setMessage(String.valueOf(result)+" Baht");
        mBuilder.setPositiveButton("OK", null);

        mBuilder.setView(mView);
        mBuilder.show();

    }

    private float calculatenow() {


        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        float total = 0;
        int size = mItemList.size();

        for (int i=0;i<size;i++){

            Item nowItem = mItemList.get(i);

            float power = nowItem.getPower();
            float hrPerDay = nowItem.getHrPerDay();
            float dayPerMonth = nowItem.getDayPerMonth();
            float bahtUnit = 7;

            float sum = hrPerDay*power*dayPerMonth*bahtUnit/1000;

            total += sum;

        }
         return total;

    }

}

