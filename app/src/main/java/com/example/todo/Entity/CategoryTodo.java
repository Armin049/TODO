package com.example.todo.Entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"id","Category_id"})
public class CategoryTodo {

    public long id;
    public long Category_id;

}
