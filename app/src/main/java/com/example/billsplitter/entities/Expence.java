package com.example.billsplitter.entities;

public class Expence {
    private int id;
    private String description;
    private double price;

    public Expence(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }
    public String getDescription() { return description;  }
    public double getPrice() { return price; }
}
