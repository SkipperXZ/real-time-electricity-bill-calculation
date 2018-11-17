package com.example.a59011178.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a59011178.home.edit.EditActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    private EditText name, power,  hrPerDay, dayPerMonth ;
    private RadioGroup ability;
    private Button add,getWatt;
    private DatabaseHelper mhelper;
    private AutoCompleteTextView type;
    private LinearLayout mpower_Layout;

//<<<<<<< HEAD
    String[] types = { "Electric fan", "Electric fan", "Air 12000 BTU", "Air 15000 BTU", "Air 18000 BTU", "Vacuum bottle", "Electric rice cooker", "Water heater", "Microwave", "Toaster", "Electric iron", "Dry iron", "Incandescent lamb bulbs", "Compact-fluorescent bulbs", "Fluorescent bulbs", "LED lighting", "Television 14 inch.", "Television 20 inch.", "Television 24 inch.", "Computer", "Refrigerator 4 cubic", "Refrigerator 6 cubic", "Refrigerator 12 cubic", "Washing machine", "Hair dryer", "Vacuum cleaner", "Modem, Router", "Telephone", "Tablet", "Power bank"
    };
    int[] types_power = {75,            104,            3500,               4400,           5300,           750,                1000,               3500,           700,        1000,       1000,           1750,           100,                    20,                             36,                 18,             120,                    200,                250,                    550,        70,                     90,                     240,                    1000,               1300,               1000,           10,         10,             10,     2200};
//=======
    //String[] types = { "Electric fan", "Exhaust fan","Rice cooker", "Iron", "Water heater", "Toster", "Hair dryer", "Washing machine", "Refrigerator", "Air-conditioner", "Vacuum cleaner", "Stove", "Television", "DVD player"
    //};
//>>>>>>> master

    String myAbility;

    private int ID = -1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addequipment);

        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, types);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.type);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

        name = (EditText)findViewById(R.id.item_name);
        type = (AutoCompleteTextView) findViewById(R.id.type);
        power = (EditText) findViewById(R.id.power);
        ability = (RadioGroup) this.findViewById(R.id.ability);
        hrPerDay = (EditText) findViewById(R.id.a_hrPerDay);
        dayPerMonth = (EditText) findViewById(R.id.a_dayPerMonth);
        getWatt = (Button) findViewById(R.id.getdefault);

        getWatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultPower();
            }
        });

        ability.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) AddItemActivity.this.findViewById(checkedId);
                myAbility = radioButton.getText().toString();
            }
        });

        mhelper = new DatabaseHelper(this);

        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);

                builder.setTitle(getString(R.string.add_data_title));
                builder.setMessage(getString(R.string.add_data_message));

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyy");
                final String currentDate = df.format(c.getTime());

                builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Item item = new Item(0,0,0,8,30,0,0,null,null,null, currentDate);

                        item.setName(name.getText().toString());
                        item.setType(type.getText().toString());
                        item.setPower(Integer.parseInt(power.getText().toString()));
                        item.setAbility(myAbility);
                        item.setHrPerDay(Integer.parseInt(hrPerDay.getText().toString()));
                        item.setDayPerMonth(Integer.parseInt(dayPerMonth.getText().toString()));
                        item.setDate(currentDate);

                        if (ID == -1){
                            mhelper.addItem(item);
                        } else {
                            item.setId(ID);
                            //mHelper.updateFriend(friend);
                        }

                        Intent BackpressedIntent = new Intent();
                        BackpressedIntent .setClass(getApplicationContext(),HomeActivity.class);
                        BackpressedIntent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(BackpressedIntent );
                        finish();
                    }
                });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.show();
            }
        });
    }

    private void showDefaultPower() {

        AutoCompleteTextView childTextView = (AutoCompleteTextView) findViewById(R.id.type);
        String childTextViewValue = childTextView.getText().toString();

        int powerVal ;

        switch (childTextViewValue){
            case "Electric fan" :  powerVal = 50;
                break;
            case "Exhaust fan" : powerVal = 60;
                break;
            case "Rice cooker" : powerVal = 70;
                break;
            default:  powerVal = 0; break;
        }
        power.setText(String.valueOf(powerVal));
    }

}