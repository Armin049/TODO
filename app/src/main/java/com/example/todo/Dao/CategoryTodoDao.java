package com.example.todo.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todo.Entity.Category;
import com.example.todo.Entity.CategoryTodo;
import com.example.todo.Entity.Todo;

import java.util.List;

@Dao
public interface CategoryTodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTodoCategory(CategoryTodo categoryTodo);

    @Query("SELECT * FROM CategoryTodo")
    List<CategoryTodo> getAllCategory();

    @Query("SELECT * FROM CategoryTodo WHERE id=:id")
    List<Category> getCategoryTodoById(long id);

    @Query("DELETE FROM CategoryTodo where id=:id")
    void deleteByID(long id);

    @Query("SELECT * FROM CategoryTodo WHERE Category_id=:id")
    List<Category> getCategoryTodoByCategoryID(long id);

    @Query("DELETE FROM CategoryTodo where Category_id=:id")
    void deleteByCategoryId(long id);
}
