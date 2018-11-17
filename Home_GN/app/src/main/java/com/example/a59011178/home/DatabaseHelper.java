package com.example.a59011178.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, Item.DATABASE_NAME, null, Item.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT)",
                Item.TABLE,
                Item.Column.ID,
                Item.Column.POWER,
                Item.Column.HR,
                Item.Column.NAME,
                Item.Column.TYPE,
                Item.Column.ABILITY,
                Item.Column.HRperDay,
                Item.Column.DAYperMONTH,
                Item.Column.TIME,
                Item.Column.TOTALMONEY,
                Item.Column.DATE);

        Log.i(TAG, CREATE_ITEM_TABLE);

        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + Item.TABLE;

        db.execSQL(DROP_ITEM_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

    public List<Item> getItemList() {
        List<Item> items_list = new ArrayList<>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (Item.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            //cursor.getInt(cursor.getColumnIndex(Item.Column.POWER)),
            // cursor.getString(cursor.getColumnIndex(Item.Column.ABILITY)),

            items_list.add(new Item(cursor.getInt(cursor.getColumnIndex(Item.Column.POWER)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.HR)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.ID)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.HRperDay)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.DAYperMONTH)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.TIME)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.TOTALMONEY)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.NAME)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TYPE)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.ABILITY)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.DATE))));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return items_list;

    }

    public void addItem(Item item){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.POWER,item.getPower());
        values.put(Item.Column.NAME,item.getName());
        values.put(Item.Column.TYPE,item.getType());
        values.put(Item.Column.ABILITY,item.getAbility());
        values.put(Item.Column.DATE,item.getDate());
        values.put(Item.Column.HRperDay,item.getHrPerDay());
        values.put(Item.Column.DAYperMONTH,item.getDayPerMonth());

        sqLiteDatabase.insert(Item.TABLE,null,values);

        sqLiteDatabase.close();

    }

    public Item getItem(String id){

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Item.TABLE,
                null,
                Item.Column.ID + " = ?",
                new String[] {id},
                null,
                null,
                null,
                null
                );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Item item = new Item();

        item.setId((int) cursor.getLong(Integer.parseInt(Item.Column.ID)));
        item.setPower((int) cursor.getLong(Integer.parseInt(Item.Column.POWER)));
        item.setName(cursor.getString(Integer.parseInt(Item.Column.NAME)));
        item.setType(cursor.getString(Integer.parseInt(Item.Column.TYPE)));
        item.setAbility(cursor.getString(Integer.parseInt(Item.Column.ABILITY)));
        item.setDate(cursor.getString(Integer.parseInt(Item.Column.DATE)));
        item.setHrPerDay((int) cursor.getLong(Integer.parseInt(Item.Column.HRperDay)));
        item.setDayPerMonth((int) cursor.getLong(Integer.parseInt(Item.Column.DAYperMONTH)));

        return item;
    }
}
