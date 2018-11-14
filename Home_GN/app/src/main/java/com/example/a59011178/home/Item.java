package com.example.a59011178.home;

import android.provider.BaseColumns;

public class Item {
    private int power, hr ,id;
    private String name, type, ability;

    private String eve_aaa;

    public static final String DATABASE_NAME = "eve_item.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "item";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String POWER = "power";
        public static final String HR = "hr";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String ABILITY = "ability";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Item(int id,int power,int hr, String name, String type, String ability) {
        this.id = id;
        this.power = power;
        this.hr = hr;
        this.name = name;
        this.type = type;
        this.ability = ability;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }


}

