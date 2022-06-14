package com.example.maltatourguide.ui.sights;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={FavoriteList.class},version = 2,exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}