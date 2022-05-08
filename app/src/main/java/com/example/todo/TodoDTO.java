package com.example.todo;

public class TodoDTO {
    private long id;
    private String titel;
    private String prio;

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
