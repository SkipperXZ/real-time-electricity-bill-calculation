package com.example.a59011178.home.timer;

import android.os.CountDownTimer;

import com.example.a59011178.home.Item;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;
    private final long duration;
    private int second;

    protected CountUpTimer(long durationMs) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    public void onFinish() {
    }

    public int getSecond() {
        return second;
    }

}