package com.esp1617.albertomoretto.foodify;

/**
 * Created by albertomoretto on 03/05/17.
 */

public class Ingredient {
    private String name;
    private float price;
    private String category; //{MEAT, BREAD, SAUCES, DRINKTYPE, DRINKSIZE}

    public Ingredient(String name, float price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
