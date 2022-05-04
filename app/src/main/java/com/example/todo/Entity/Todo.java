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

    public Todo() {
    }

    public Todo(String titel, String beschreibung, String datetime) {
        Titel = titel;
        Beschreibung = beschreibung;
        this.datetime = datetime;
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
}