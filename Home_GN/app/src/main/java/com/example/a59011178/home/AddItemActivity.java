package com.example.a59011178.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    private EditText name, power, type;
    private RadioGroup ability;
    private Button add;
    private DatabaseHelper mhelper;

    String myAbility;

    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addequipment);

        name = (EditText)findViewById(R.id.item_name);
        type = (EditText) findViewById(R.id.type);
        power = (EditText) findViewById(R.id.power);

        ability = (RadioGroup) this.findViewById(R.id.ability);

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
                        item.setHrPerDay(8);
                        item.setDayPerMonth(30);
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

}
