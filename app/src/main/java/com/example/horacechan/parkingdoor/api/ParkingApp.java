package com.example.horacechan.parkingdoor.api;

import android.app.Application;
import android.content.Context;

import com.example.horacechan.parkingdoor.api.http.base.VolleyManager;


public class ParkingApp extends Application {

    public static Context APP_CONTEXT;

    public static final String HOST="http://192.168.199.246:8080/service";

    public static final String MARKERID="e5a3715d-047c-11e6-b034-00ff9099da81";


    @Override
    public void onCreate() {
        super.onCreate();

        APP_CONTEXT=getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10<<10<<10);
    }
}
