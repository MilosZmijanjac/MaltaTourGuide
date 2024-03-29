package com.example.maltatourguide.ui.news;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface News_DAO {
    @Insert
    void Insert(News item);
//    @Update
//    void Update(News_Data item);
//    @Delete
//    void Delete(News_Data item);
   @Query("DELETE FROM  news_table")
   void deleteAllNews();

    @Query("Select *  from news_table LIMIT 15")
    List<News> getAllData();
}
