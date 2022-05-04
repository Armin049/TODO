package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "priority")
public class Priority {

    @PrimaryKey(autoGenerate = true)
    public long priorityId;

    public String name;

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }
}
