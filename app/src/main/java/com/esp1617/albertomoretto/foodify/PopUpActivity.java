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

    ArrayAdapter<String> adapter;
    List<String> list;


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
                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_CLASSIC_NAME, FoodifyConstants.MEAT_HABURGER_CLASSIC_PRICE, FoodifyConstants.MEAT_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_DOUBLE_NAME, FoodifyConstants.MEAT_HABURGER_DOUBLE_PRICE, FoodifyConstants.MEAT_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.MEAT_HABURGER_JUICY_NAME, FoodifyConstants.MEAT_HABURGER_JUICY_PRICE, FoodifyConstants.MEAT_CATEGORY);

                mMeatSpinner = (Spinner) findViewById(R.id.meat_type_spinner);
                list = new ArrayList<String>();
                List<Food.IngredientSelected> hamburgerList= hamburger.getIngredients();
                for(int i =0; i<hamburgerList.size(); i++){
                    if(hamburgerList.get(i).getIngredientName().getCategory().equals(FoodifyConstants.MEAT_CATEGORY)) {
                        list.add(hamburgerList.get(i).getIngredientName().getName());
                    }
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mMeatSpinner.setAdapter(adapter);

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
                getWindow().setLayout((int) (width * .6), (int) (height * .4));
                break;

            case FoodifyTags.DESSERT:
                setContentView(R.layout.activity_pop_up_dessert);
                getWindow().setLayout((int) (width * .6), (int) (height * .8));
                break;
        }
    }
}
