package com.example.todo;

public class TodoDTO {
    private long id;
    private String titel;
    private String prio;
//DTO to transfer the TODO_ID, name and priority_name to the TodoAdapter
    public TodoDTO(long id, String titel, String prio) {
        this.id=id;
        this.titel = titel;
        this.prio = prio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }
}
