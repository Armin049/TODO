package com.example.todo.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todo.Entity.Priority;

import java.util.List;

@Dao
public interface PriorityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPriority(Priority priority);

    @Query("SELECT * FROM priority")
    List<Priority> getAllPriority();

    @Query("SELECT * FROM priority WHERE name=:name")
    List<Priority> getPriorityByName(String name);

    @Query("SELECT * FROM priority WHERE priorityId=:id")
    List<Priority> getPriorityByID(long id);

    @Query("DELETE FROM priority where PriorityId=:id")
    void deleteById(long id);
}
