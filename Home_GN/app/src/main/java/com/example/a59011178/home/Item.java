package com.example.a59011178.home;

import android.provider.BaseColumns;

import java.util.Date;

public class Item implements Comparable<Item> {

    private int power, hr ,id, hrPerDay, dayPerMonth, time, totalMoney;
    private String name, type, ability, date;

    public static final String DATABASE_NAME = "eve_item.db";
    public static final int DATABASE_VERSION = 5;
    public static final String TABLE = "item";

    @Override
    public int compareTo(Item other) {
        return (int) (id - other.getId());
    }

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String POWER = "power";
        public static final String HR = "hr";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String ABILITY = "ability";
        public static final String HRperDay = "hrPerDay";
        public static final String DAYperMONTH = "dayPerDay";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String TOTALMONEY = "totalMoney";
        public static final String STAGE = "stage";
        public static final String TIME_ON = "time_on";
        public static final String TIME_OFF = "time_off";


    }

    public Item(){

    }

    public Item(int id, int power, int hr, int hrPerDay, int dayPerMonth, int time, int totalMoney, String name, String type, String ability, String date) {
        this.id = id;
        this.power = power;
        this.hr = hr;
        this.hrPerDay = hrPerDay;
        this.dayPerMonth = dayPerMonth;
        this.time = time;
        this.totalMoney = totalMoney;
        this.name = name;
        this.type = type;
        this.ability = ability;
        this.date = date;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHrPerDay(int hrPerDay) {
        this.hrPerDay = hrPerDay;
    }

    public void setDayPerMonth(int dayPerMonth) {
        this.dayPerMonth = dayPerMonth;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPower() {
        return power;
    }

    public int getHr() {
        return hr;
    }

    public int getId() {
        return id;
    }

    public int getHrPerDay() {
        return hrPerDay;
    }

    public int getDayPerMonth() {
        return dayPerMonth;
    }

    public int getTime() {
        return time;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAbility() {
        return ability;
    }

    public String getDate() {
        return date;
    }

}

