package com.example.billsplitter.entities;

import java.util.ArrayList;
import java.util.List;

public class Friend {
    private static int counter = 0;
    private int id;
    private String name;
    private List<Expence> expences;
    private boolean isPaying;

    public Friend(String name) {
        this.id = ++counter;
        this.name = name;
        this.expences = new ArrayList<>();
        this.isPaying = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Expence> getExpences() { return expences; }

    public void setExpences(List<Expence> expences) {
        this.expences = expences;
    }

    public boolean isPaying() { return isPaying; }
    public void setPaying(boolean paying) { isPaying = paying; }
}
