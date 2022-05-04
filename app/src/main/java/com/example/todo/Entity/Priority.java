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

    public long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
