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
        String CREATE_ITEM_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s TEXT)",
                Item.TABLE,
                Item.Column.ID,
                Item.Column.POWER,
                Item.Column.HR,
                Item.Column.NAME,
                Item.Column.TYPE,
                Item.Column.ABILITY);

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

            items_list.add(new Item(cursor.getInt(cursor.getColumnIndex(Item.Column.ID)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.POWER)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.HR)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.NAME)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TYPE)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.ABILITY))));

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

        sqLiteDatabase.insert(Item.TABLE,null,values);

        sqLiteDatabase.close();

    }
}
