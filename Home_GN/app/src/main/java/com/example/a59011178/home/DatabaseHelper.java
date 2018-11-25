package com.example.a59011178.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private static final String DATABASE_ALTER = "ALTER TABLE " + Item.TABLE + " ADD COLUMN " + Item.Column.STAGE + " string;" ;
    private static final String DATABASE_ALTER2 = "ALTER TABLE " + Item.TABLE + " ADD COLUMN " + Item.Column.TIME_ON + " string;" ;
    private static final String DATABASE_ALTER3 = "ALTER TABLE " + Item.TABLE + " ADD COLUMN " + Item.Column.TIME_OFF + " string;" ;
    private static final String DATABASE_ALTER4 = "ALTER TABLE " + Item.TABLE + " ADD COLUMN " + Item.Column.TIME_LAST_ON + " string;" ;
    private static final String DATABASE_ALTER5 = "ALTER TABLE " + Item.TABLE + " ADD COLUMN " + Item.Column.HR_LAST_ON + " int;" ;

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, Item.DATABASE_NAME, null, Item.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = String.format("CREATE TABLE %s" + "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s TEXT DEFAULT 'Manual/On-Off', %s INTEGER DEFAULT 8, %s INTEGER DEFAULT 30, %s INTEGER , %s INTEGER DEFAULT 7, %s TEXT, %s TEXT, %s TEXT DEFAULT '00:00', %s TEXT DEFAULT '00:00', %s INTEGER, %s TEXT)",
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
                Item.Column.DATE,
                Item.Column.STAGE,
                Item.Column.TIME_ON,
                Item.Column.TIME_OFF,
                Item.Column.HR_LAST_ON,
                Item.Column.TIME_LAST_ON);

        Log.i(TAG, CREATE_ITEM_TABLE);

        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            db.execSQL(DATABASE_ALTER);
        }
        if (oldVersion < 3){
            db.execSQL(DATABASE_ALTER2);
        }
        if (oldVersion < 4){
            db.execSQL(DATABASE_ALTER3);
        }
        if (oldVersion < 6){
            db.execSQL(DATABASE_ALTER4);
        }
        if (oldVersion < 7){
            db.execSQL(DATABASE_ALTER5);
        }
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

            items_list.add(new Item(
                    cursor.getInt(cursor.getColumnIndex(Item.Column.ID)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.POWER)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.HR)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.HRperDay)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.DAYperMONTH)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.TIME)),
                    cursor.getInt(cursor.getColumnIndex(Item.Column.TOTALMONEY)),

                    cursor.getString(cursor.getColumnIndex(Item.Column.NAME)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TYPE)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.ABILITY)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.DATE)),

                    cursor.getString(cursor.getColumnIndex(Item.Column.STAGE)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TIME_ON)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TIME_OFF)),

                    cursor.getInt(cursor.getColumnIndex(Item.Column.HR_LAST_ON)),
                    cursor.getString(cursor.getColumnIndex(Item.Column.TIME_LAST_ON))

            ));

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
        values.put(Item.Column.STAGE,item.getState());

        sqLiteDatabase.insert(Item.TABLE,null,values);

        sqLiteDatabase.close();

    }

    public void addItemWithSetTime(Item item){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.POWER,item.getPower());
        values.put(Item.Column.NAME,item.getName());
        values.put(Item.Column.TYPE,item.getType());
        values.put(Item.Column.ABILITY,item.getAbility());
        values.put(Item.Column.DATE,item.getDate());
        values.put(Item.Column.HRperDay,item.getHrPerDay());
        values.put(Item.Column.DAYperMONTH,item.getDayPerMonth());
        values.put(Item.Column.STAGE,item.getState());
        values.put(Item.Column.TIME_ON,item.getTime_on());
        values.put(Item.Column.TIME_OFF,item.getTime_off());

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

    public void updateItem(Item item) {

            sqLiteDatabase  = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Item.Column.POWER,item.getPower());
            values.put(Item.Column.NAME,item.getName());
            values.put(Item.Column.TYPE,item.getType());
//            values.put(Item.Column.ABILITY,item.getAbility());
//            values.put(Item.Column.DATE,item.getDate());
            values.put(Item.Column.HRperDay,item.getHrPerDay());
            values.put(Item.Column.DAYperMONTH,item.getDayPerMonth());
            values.put(Item.Column.ABILITY,item.getAbility());

            int row = sqLiteDatabase.update(Item.TABLE,
                    values,
                    Item.Column.ID + " = ? ",
                    new String[] { String.valueOf(item.getId()) });

            sqLiteDatabase.close();
    }

    public void updateItemWithSetTime(Item item) {

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.POWER,item.getPower());
        values.put(Item.Column.NAME,item.getName());
        values.put(Item.Column.TYPE,item.getType());
        values.put(Item.Column.HRperDay,item.getHrPerDay());
        values.put(Item.Column.DAYperMONTH,item.getDayPerMonth());
        values.put(Item.Column.TIME_ON,item.getTime_on());
        values.put(Item.Column.TIME_OFF,item.getTime_off());
        values.put(Item.Column.ABILITY,item.getAbility());

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] { String.valueOf(item.getId()) });

        sqLiteDatabase.close();
    }

    public void updateHRperDay(String id, int hr){
        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.HRperDay, hr);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

        sqLiteDatabase.close();
    }

    public void updateDayPerMonth(String id, int day){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.DAYperMONTH, day);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

        sqLiteDatabase.close();

    }

    public void updateHr(String id,int hr){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.HR, hr);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

        sqLiteDatabase.close();

    }

    public void updateLastTimeOn(String id,int hrLastTime,String timeLastOn){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.HR_LAST_ON, hrLastTime);
        values.put(Item.Column.TIME_LAST_ON, timeLastOn);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

        sqLiteDatabase.close();

    }

    public void updateSwitch(String id,String ab){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.ABILITY, ab);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

        sqLiteDatabase.close();

    }

    public void newMonth(){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.HR, 0);

        int row = sqLiteDatabase.update(Item.TABLE, values,null,null);

        sqLiteDatabase.close();

    }

    public void cutOut(){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.STAGE, "false");

        int row = sqLiteDatabase.update(Item.TABLE, values,null,null);

        sqLiteDatabase.close();

    }

    public void allChangeToManual(){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.ABILITY, "Manual/On-Off");

        int row = sqLiteDatabase.update(Item.TABLE, values,null,null);

        sqLiteDatabase.close();

    }

    public void changeUint(int unit){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Item.Column.TOTALMONEY, unit);

        int row = sqLiteDatabase.update(Item.TABLE, values,null,null);

        sqLiteDatabase.close();

    }

    public void updateState(String id,Boolean nowStage,int sec){

        sqLiteDatabase  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String s = nowStage.toString();

        values.put(Item.Column.STAGE, nowStage.toString());
        values.put(Item.Column.HR, sec);

        int row = sqLiteDatabase.update(Item.TABLE,
                values,
                Item.Column.ID + " = ? ",
                new String[] {id});

     //   System.out.println("--------------------------------------"+id+"+ boolen +"+s+" - "+sec);

        sqLiteDatabase.close();

    }

    public void deleteItem(String id){

        sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(Item.TABLE, Item.Column.ID + " = " + id, null);

        sqLiteDatabase.close();


    }

}
