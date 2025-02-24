package com.example.demoandroidsqlserver;

public class Book {
    private int id;
    private String name;
    private String description;
    private int price;
    private boolean isSold;

    public Book(int id, String name, String description, int price, boolean isSold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isSold = isSold;
    }

    public Book(String name, String description, int price, boolean isSold) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isSold = isSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}
