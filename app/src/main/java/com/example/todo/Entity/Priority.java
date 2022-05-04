package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "priority",
        foreignKeys = {
                @ForeignKey(
                        entity = Todo.class,
                        parentColumns = "id",
                        childColumns = "priorityId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)
public class Priority {

    @PrimaryKey(autoGenerate = true)
    public long priorityId;

    public long id;

    public String name;

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }
}
