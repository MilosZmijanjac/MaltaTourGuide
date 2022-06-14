package com.example.maltatourguide.ui.news;

import androidx.room.Database;

import androidx.room.RoomDatabase;
@Database(entities = {News.class},version =1,exportSchema = false)
public  abstract class AppDataBase extends RoomDatabase {
public abstract News_DAO news_dao();
}
