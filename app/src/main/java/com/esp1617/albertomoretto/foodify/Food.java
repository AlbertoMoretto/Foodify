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

    public void addIngredient(Ingredient ingredient){
        IngredientSelected i = new IngredientSelected(ingredient);
        ingredients.add(i);
    }

    public void setTotalPrice(){
        for(int i = 0; i<ingredients.size(); i++){
            if(ingredients.get(i).getSelected()) totalPrice+=ingredients.get(i).getIngredientName().getPrice();

        }
    }





    private class IngredientSelected{
        private Ingredient ingredientName;
        private boolean selected;

        public IngredientSelected(Ingredient ingredient){
            ingredientName = ingredient;
            selected = false;
        }
        public void setIngredientSelected(){
            selected = true;
        }

        public void setIngredientDeselected(){
            selected = false;
        }

        public boolean getSelected(){
            return selected;
        }

        public Ingredient getIngredientName() {
            return ingredientName;
        }
    }
}
