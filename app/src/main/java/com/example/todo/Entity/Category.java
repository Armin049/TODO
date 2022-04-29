package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {

    @PrimaryKey
    public final long Category_id;
    public String name;

    public Category(long category_id, String name) {
        Category_id = category_id;
        this.name = name;
    }
}
