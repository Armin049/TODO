package com.example.todo.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo.Entity.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTodo(Todo todo);

    @Query("SELECT * FROM todo")
    List<Todo> getAllTodos();

    @Query("SELECT * FROM todo WHERE id=:id")
    List<Todo> getTodoById(long id);

    @Query("SELECT * FROM todo WHERE Titel=:name")
    List<Todo> getTodoByName(String name);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(Todo todo);

    @Query("DELETE FROM todo where id=:id")
    void deleteById(long id);

    @Query("delete from todo")
    void removeAllTodos();
}
