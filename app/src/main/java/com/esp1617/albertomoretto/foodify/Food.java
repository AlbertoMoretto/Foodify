package com.esp1617.albertomoretto.foodify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertomoretto on 03/05/17.
 */

public class Food {
    private String name;
    private List<IngredientSelected> ingredients;
    private float totalPrice;

    public Food(String name) {
        this.name = name;
        ingredients = new ArrayList<IngredientSelected>();
        totalPrice = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void addIngredient(String name, float price, String category){
        Ingredient newI = new Ingredient(name, price, category);
        IngredientSelected i = new IngredientSelected(newI);
        ingredients.add(i);
    }


    public void setIngredientSelected(String name, boolean value){
        for(int i = 0; i<ingredients.size(); i++){
            if(ingredients.get(i).getIngredientName().getName().equals(name)) ingredients.get(i).setSelected(value);
        }
    }

    public List<IngredientSelected> getIngredients() {
        return ingredients;
    }

    public void setTotalPrice(){
        for(int i = 0; i<ingredients.size(); i++){
            if(ingredients.get(i).getSelected()) totalPrice+=ingredients.get(i).getIngredientName().getPrice();

        }

    }

    public float getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        String ingredientList="";
        for(int i = 0; i<ingredients.size(); i++){
            ingredientList+=(ingredients.get(i).getIngredientName().getName())+"--->"+(ingredients.get(i).getIngredientName().getPrice())+"--->"+(ingredients.get(i).getSelected());
            ingredientList+="\n";
        }
        return "Food{" +
                "name='" + name + '\'' +
                ", " + ingredientList+
                ", totalPrice=" + totalPrice +
                '}';
    }

    protected class IngredientSelected{
        private Ingredient ingredientName;
        private boolean selected;

        public IngredientSelected(Ingredient ingredient){
            ingredientName = ingredient;
            selected = false;
        }
        public void setSelected(boolean value){
            selected = value;
        }

        /*public void setDeselected(){
            selected = false;
        }*/

        public boolean getSelected(){
            return selected;
        }

        public Ingredient getIngredientName() {
            return ingredientName;
        }
    }
}
