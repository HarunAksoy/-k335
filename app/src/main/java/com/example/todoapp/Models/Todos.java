package com.example.todoapp.Models;

public class Todos {
    int id;
    private String title;
    private String description;
    private String priorty;
    private String address;


    public Todos(int id, String title, String description, String priority, String address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priorty = priority;
        this.address = address;
    }


    public Todos(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorty() {
        return priorty;
    }

    public void setPriorty(String priorty) {
        this.priorty = priorty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}