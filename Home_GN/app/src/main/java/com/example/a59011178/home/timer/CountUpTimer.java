package com.example.a59011178.home.timer;

import android.os.CountDownTimer;

import com.example.a59011178.home.Item;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;
    private final long duration;
    private int second;
    private Item item;
    private int initTime;

    protected CountUpTimer(long durationMs , Item item) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
        this.item = item;
        this.initTime = item.getHr();
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        item.setHr(second+initTime);
        initTime = item.getHr();
        this.start();
    }

    public int getSecond() {
        return second;
    }

    public int getInitTime() {
        return initTime;
    }
}