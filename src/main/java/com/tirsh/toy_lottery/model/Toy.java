package com.tirsh.toy_lottery.model;

public class Toy {
    private Integer id;
    private String title;
    private int quantity;
    private int dropFrequency;

    public Toy(Integer id, String title, int quantity, int dropFrequency) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.dropFrequency = dropFrequency;
    }

    public Toy(String title, int quantity, int dropFrequency) {
        this(null, title, quantity, dropFrequency);
    }

    public Toy(Toy toy) {
        this(toy.getId(), toy.getTitle(), toy.getQuantity(), toy.getDropFrequency());
    }

    public boolean isNew(){
        return this.id == null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDropFrequency() {
        return dropFrequency;
    }

    public void setDropFrequency(int dropFrequency) {
        this.dropFrequency = dropFrequency;
    }
}
