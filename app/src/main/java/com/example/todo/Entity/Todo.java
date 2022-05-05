package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo",
        foreignKeys = {
                @ForeignKey(
                        entity = Todo.class,
                        parentColumns = "id",
                        childColumns = "priorityId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String Titel;
    public String Beschreibung;
    public String datetime;

    public long priorityId;

    public Todo() {
    }

    public Todo(String titel, String beschreibung, String datetime, long priorityId) {
        Titel = titel;
        Beschreibung = beschreibung;
        this.datetime = datetime;
        this.priorityId=priorityId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitel() {
        return Titel;
    }

    public void setTitel(String titel) {
        Titel = titel;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }
}