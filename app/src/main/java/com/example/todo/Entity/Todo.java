package com.example.todo.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String Titel;
    public String Beschreibung;
    public String datetime;

    enum Prioritaet{
        gering,
        mittel,
        hoch,
        sehr_hoch
    };

    public Todo() {
    }

    public Todo(long id, String titel, String beschreibung, String datetime) {
        this.id = id;
        Titel = titel;
        Beschreibung = beschreibung;
        this.datetime = datetime;
    }
}