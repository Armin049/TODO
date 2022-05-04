package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public long Category_id;
    public String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public long getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(long category_id) {
        Category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
