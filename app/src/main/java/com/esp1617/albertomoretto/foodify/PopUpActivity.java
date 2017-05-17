package com.esp1617.albertomoretto.foodify;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//Activity che viene lanciata quando si sceglie un cibo dal menù di FoodActivity
// https://www.youtube.com/watch?v=fn5OlqQuOCk

public class PopUpActivity extends AppCompatActivity {
    private String selectedFood;
    private Food hamburger;
    private Food hotDog;
    private Food fries;
    private Food drink;
    private Food dessert;

    private Spinner mMeatSpinner;
    private Spinner mBreadSpinner;
    private Spinner mSizeSpinner;
    private Spinner mTypeSpinner;

    ArrayAdapter<String> breadAdapter, meatAdapter, sizeAdapter, typeAdapter;
    List<String> breadList, meatList,sizeList, typeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        selectedFood = getIntent().getStringExtra(FoodifyTags.SELECT_FOOD_CALLER);

        //Il layout che viene caricato varia a seconda del tipo di cibo che si sceglie
        //Creare diversi layout per diversi cibi
        switch (selectedFood) {
            case FoodifyTags.HAMBURGER:
                setContentView(R.layout.activity_pop_up_hamburger);
                //hamburger = new Food(Resources.getSystem().getString(R.string.hamburger_label));
                hamburger = new Food("hamburger");

                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_BUCKWHEAT_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_BUCKWHEAT_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_SESAME_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_SESAME_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_WHOLE_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_WHOLE_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);

                mBreadSpinner = (Spinner) findViewById(R.id.bread_type_spinner);
                breadList = new ArrayList<String>();
                List<Food.IngredientSelected> hamburgerList= hamburger.getIngredients();
                for(int i =0; i<hamburgerList.size(); i++){
                    if(hamburgerList.get(i).getIngredientName().getCategory().equals(FoodifyConstants.BREAD_CATEGORY)) {
                        breadList.add(hamburgerList.get(i).getIngredientName().getName());
                    }
                }
                breadAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, breadList);
                breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mBreadSpinner.setAdapter(breadAdapter);

                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_CLASSIC_NAME, FoodifyConstants.MEAT_HABURGER_CLASSIC_PRICE, FoodifyConstants.MEAT_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_DOUBLE_NAME, FoodifyConstants.MEAT_HABURGER_DOUBLE_PRICE, FoodifyConstants.MEAT_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_JUICY_NAME, FoodifyConstants.MEAT_HABURGER_JUICY_PRICE, FoodifyConstants.MEAT_CATEGORY);

                mMeatSpinner = (Spinner) findViewById(R.id.meat_type_spinner);
                meatList = new ArrayList<String>();
                //List<Food.IngredientSelected> hamburgerList= hamburger.getIngredients();
                for(int i =0; i<hamburgerList.size(); i++){
                    if(hamburgerList.get(i).getIngredientName().getCategory().equals(FoodifyConstants.MEAT_CATEGORY)) {
                        meatList.add(hamburgerList.get(i).getIngredientName().getName());
                    }
                }
                meatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meatList);
                meatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mMeatSpinner.setAdapter(meatAdapter);

                getWindow().setLayout((int) (width * .8), (int) (height * .6));
                break;

            case FoodifyTags.HOT_DOG:
                setContentView(R.layout.activity_pop_up_hot_dog);
                getWindow().setLayout((int) (width * .7), (int) (height * .5));
                break;

            case FoodifyTags.FRIES:
                setContentView(R.layout.activity_pop_up_fries);
                getWindow().setLayout((int) (width * .6), (int) (height * .4));
                break;

            case FoodifyTags.DRINK:
                setContentView(R.layout.activity_pop_up_drink);
                drink = new Food("drink");
                drink.addIngredient(FoodifyConstants.DRINK_SIZE_SMALL_NAME, FoodifyConstants.DRINK_SIZE_SMALL_PRICE, FoodifyConstants.SIZE_CATEGORY);
                drink.addIngredient(FoodifyConstants.DRINK_SIZE_MEDIUM_NAME, FoodifyConstants.DRINK_SIZE_MEDIUM_PRICE, FoodifyConstants.SIZE_CATEGORY);
                drink.addIngredient(FoodifyConstants.DRINK_SIZE_LARGE_NAME, FoodifyConstants.DRINK_SIZE_LARGE_PRICE, FoodifyConstants.SIZE_CATEGORY);

                mSizeSpinner = (Spinner) findViewById(R.id.drink_size_spinner);
                sizeList = new ArrayList<String>();
                List<Food.IngredientSelected> drinkList= drink.getIngredients();
                for(int i=0; i<drinkList.size();i++) {
                    if(drinkList.get(i).getIngredientName().getCategory().equals(FoodifyConstants.SIZE_CATEGORY)) {
                        sizeList.add(drinkList.get(i).getIngredientName().getName());
                    }
                }
                sizeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sizeList);
                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSizeSpinner.setAdapter(sizeAdapter);

                drink.addIngredient(FoodifyConstants.DRINK_TYPE_COKE_NAME,FoodifyConstants.DRINK_TYPE_COKE_PRICE,FoodifyConstants.TYPE_CATEGORY);
                drink.addIngredient(FoodifyConstants.DRINK_TYPE_DIET_COKE_NAME,FoodifyConstants.DRINK_TYPE_DIET_COKE_PRICE,FoodifyConstants.TYPE_CATEGORY);
                drink.addIngredient(FoodifyConstants.DRINK_TYPE_SPRITE_NAME,FoodifyConstants.DRINK_TYPE_SPRITE_PRICE,FoodifyConstants.TYPE_CATEGORY);
                drink.addIngredient(FoodifyConstants.DRINK_TYPE_WATER_NAME,FoodifyConstants.DRINK_TYPE_WATER_PRICE,FoodifyConstants.TYPE_CATEGORY);

                mTypeSpinner = (Spinner) findViewById(R.id.drink_type_spinner);
                typeList = new ArrayList<String>();
                for(int i=0; i<drinkList.size();i++) {
                    if(drinkList.get(i).getIngredientName().getCategory().equals(FoodifyConstants.TYPE_CATEGORY)) {
                        typeList.add(drinkList.get(i).getIngredientName().getName());
                    }
                }
                typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeList);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTypeSpinner.setAdapter(typeAdapter);
                getWindow().setLayout((int) (width * .6), (int) (height * .4));
                break;

            case FoodifyTags.DESSERT:
                setContentView(R.layout.activity_pop_up_dessert);
                getWindow().setLayout((int) (width * .6), (int) (height * .8));
                break;
        }
    }
}
