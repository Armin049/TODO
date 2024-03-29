package com.example.todo.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todo.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategory();

    @Query("SELECT * FROM Category WHERE name=:name")
    List<Category> getCategoryByName(String name);

    @Query("SELECT * FROM Category WHERE Category_id=:id")
    List<Category> getCategoryByID(long id);

    @Query("DELETE FROM Category where name=:name")
    void deleteByName(String name);

    @Query("DELETE FROM Category where Category_id=:id")
    void deleteByID(long id);

    @Query("SELECT name FROM CATEGORY")
    List<String> getAllTitels();
}
