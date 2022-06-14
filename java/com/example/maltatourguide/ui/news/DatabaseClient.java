package com.example.maltatourguide.ui.news;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient mInstance;
    private final AppDataBase appDataBase;
    private DatabaseClient(Context context){
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,
                "allData").fallbackToDestructiveMigration().build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if(mInstance == null){
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }
    public AppDataBase getAppDataBase(){
        return appDataBase;
    }
}
