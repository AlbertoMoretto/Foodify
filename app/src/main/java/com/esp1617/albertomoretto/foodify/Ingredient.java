package com.esp1617.albertomoretto.foodify;

/**
 * Created by albertomoretto on 03/05/17.
 */

public class Ingredient {
    private String name;
    private float price;

    public Ingredient(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
