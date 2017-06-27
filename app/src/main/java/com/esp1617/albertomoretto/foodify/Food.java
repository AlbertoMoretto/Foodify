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

        //se viene inizializzato un oggetto hotdog o fries aggiungo un ingrediente con un prezzo di base dato che non
        //si può scegliere nella pop up activity
        if (name.equals("hotdog")){
            addIngredient(FoodifyConstants.BASIC_HOTDOG_INGREDIENTS,FoodifyConstants.BASIC_HOTDOG_INGREDIENTS_PRICE,FoodifyConstants.ADDITION_CATEGORY);
            setIngredientSelected(FoodifyConstants.BASIC_HOTDOG_INGREDIENTS,true);
        }else if (name.equals("fries")){
            addIngredient(FoodifyConstants.BASIC_FRIES_INGREDIENTS,FoodifyConstants.BASIC_FRIES_INGREDIENTS_PRICE,FoodifyConstants.ADDITION_CATEGORY);
            setIngredientSelected(FoodifyConstants.BASIC_FRIES_INGREDIENTS,true);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Aggiunge un ingrediente alla lista degli ingredienti possibili
     * @param name nome
     * @param price prezzo
     * @param category categoria
     */
    public void addIngredient(String name, float price, String category){
        Ingredient newI = new Ingredient(name, price, category);
        IngredientSelected i = new IngredientSelected(newI);
        ingredients.add(i);
    }

    /**
     * Imposta un ingrediente selezionato o deselezionato in base al valore booleano passato
     * @param name nome ingrediente
     * @param value true o false
     */
    public void setIngredientSelected(String name, boolean value){
        for(int i = 0; i<ingredients.size(); i++){
            if(ingredients.get(i).getIngredientName().getName().equals(name)) ingredients.get(i).setSelected(value);
        }
    }

    public List<IngredientSelected> getIngredients() {
        return ingredients;
    }

    /**
     * Setta il prezzo totale del cibo in base a quali ingredienti della lista sono selezionati
     */
    public void setTotalPrice(){
        for(int i = 0; i<ingredients.size(); i++){
            if(ingredients.get(i).getSelected()) totalPrice+=ingredients.get(i).getIngredientName().getPrice();

        }

    }

    public float getTotalPrice() {
        return totalPrice;
    }

    @Override
    /**
     * Metodo per stampare le info di un  cibo
     */
    public String toString() {
        String ingredientList="";
        ingredientList += name.toUpperCase()+":\n";
        for(int i = 0; i<ingredients.size(); i++) {
            if(ingredients.get(i).getSelected()) {
                ingredientList += (ingredients.get(i).getIngredientName().getName());
                ingredientList += "\n";
            }
        }
        ingredientList +="\n";
        return ingredientList;
    }

    /**
     * Classe interna che contiene un ingrediente e il suo valore booleano che indica se è selezionato
     * oppure no
     */
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
