package com.example.todo.Entity;

import androidx.room.Entity;


@Entity(primaryKeys = {"id","Category_id"})
public class CategoryTodo {

    public long id;
    public long Category_id;

    public CategoryTodo() {
    }

    public CategoryTodo(long id, long category_id) {
        this.id = id;
        Category_id = category_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(long category_id) {
        Category_id = category_id;
    }
}
