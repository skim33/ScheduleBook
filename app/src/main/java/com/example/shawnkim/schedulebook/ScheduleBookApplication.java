package com.example.shawnkim.schedulebook;

import android.app.Application;

import io.realm.Realm;

public class ScheduleBookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
