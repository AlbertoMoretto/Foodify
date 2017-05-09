package com.esp1617.albertomoretto.foodify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
//Activity che viene lanciata quando si sceglie un cibo dal menù di FoodActivity
// https://www.youtube.com/watch?v=fn5OlqQuOCk

public class PopUpActivity extends AppCompatActivity {
    String selectedFood;

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
