package com.esp1617.albertomoretto.foodify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

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

    private Button confirmHamburger;
    private Button confirmHotdog;
    private Button confirmDrink;
    private Button confirmDessert;
    private Button confirmFries;

    //hamburger checkbox
    private CheckBox checkboxSalad;
    private CheckBox checkboxTomatoes;
    private CheckBox checkboxHamburgerOnions;
    private CheckBox checkboxCucumber;

    //hotdog checkbox
    private CheckBox checkboxBreadCrumbs;
    private CheckBox checkboxHotdogOnions;
    private CheckBox checkboxMustard;
    private CheckBox checkboxHotdogMayo;
    private CheckBox checkboxHotdogKetchup;

    //fries checkbox
    private CheckBox checkboxFriesMayo;
    private CheckBox checkboxFriesKetchup;
    private CheckBox checkboxBarbecueSauce;

    //dessert checkbox
    private CheckBox checkboxIceCream;
    private CheckBox checkboxJellyBean;
    private CheckBox checkboxKitkat;
    private CheckBox checkboxLollipop;
    private CheckBox checkboxMarshmellow;
    private CheckBox checkboxNougat;



    ArrayAdapter<String> breadAdapter, meatAdapter, sizeAdapter, typeAdapter;
    List<String> breadList, meatList,sizeList, typeList;
    private final int zeroValue=0;

    private void setOrder(float price, String order) {
        Intent data = new Intent();
        data.putExtra(FoodifyTags.EXTRA_PRICE_SETTED,price);
        data.putExtra(FoodifyTags.EXTRA_ORDER_SETTED,order);
        setResult(RESULT_OK,data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        selectedFood = getIntent().getStringExtra(FoodifyTags.SELECT_FOOD_CALLER);

        hamburger = new Food("hamburger");
        hotDog = new Food("hotdog");
        fries = new Food("fries");
        drink = new Food("drink");
        dessert = new Food("dessert");

        //Il layout che viene caricato varia a seconda del tipo di cibo che si sceglie
        //Creare diversi layout per diversi cibi
        switch (selectedFood) {
            case FoodifyTags.HAMBURGER:
                setContentView(R.layout.activity_pop_up_hamburger);

                //assegno i checkbox
                checkboxSalad=(CheckBox) findViewById(R.id.checkBoxSalad);
                checkboxTomatoes=(CheckBox) findViewById(R.id.checkBoxTomatoes);
                checkboxHamburgerOnions=(CheckBox) findViewById(R.id.checkBoxHamburgerOnions);
                checkboxCucumber=(CheckBox) findViewById(R.id.checkBoxCucumber);

                //aggiungo gli ingredienti possibili
                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_BUCKWHEAT_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_BUCKWHEAT_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_SESAME_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_SESAME_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.BREAD_HAMBURGER_WHOLE_BREAD_NAME, FoodifyConstants.BREAD_HAMBURGER_WHOLE_BREAD_PRICE, FoodifyConstants.BREAD_CATEGORY);

                mBreadSpinner = (Spinner) findViewById(R.id.bread_type_spinner);
                breadList = new ArrayList<String>();
                final List<Food.IngredientSelected> hamburgerList= hamburger.getIngredients();
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

                hamburger.addIngredient(FoodifyConstants.SALAD,FoodifyConstants.SALAD_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.TOMATOES,FoodifyConstants.TOMATOES_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.ONIONS,FoodifyConstants.ONIONS_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hamburger.addIngredient(FoodifyConstants.CUCUMBER,FoodifyConstants.CUCUMBER_PRICE,FoodifyConstants.ADDITION_CATEGORY);

                //dico cosa deve fare il bottone conferma
                confirmHamburger= (Button) findViewById(R.id.buttonConfirmHamburger);
                confirmHamburger.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        hamburger.setIngredientSelected((String)mBreadSpinner.getSelectedItem(),true);
                        hamburger.setIngredientSelected((String)mMeatSpinner.getSelectedItem(),true);
                        hamburger.setIngredientSelected(FoodifyConstants.SALAD,checkboxSalad.isChecked());
                        hamburger.setIngredientSelected(FoodifyConstants.TOMATOES,checkboxTomatoes.isChecked());
                        hamburger.setIngredientSelected(FoodifyConstants.ONIONS,checkboxHamburgerOnions.isChecked());
                        hamburger.setIngredientSelected(FoodifyConstants.CUCUMBER,checkboxCucumber.isChecked());

                        hamburger.setTotalPrice();
                        Log.d("Controllo", hamburger.toString());
                        setOrder(hamburger.getTotalPrice(),hamburger.toString());
                        finish();
                    }
                });

                getWindow().setLayout((int) (width * .8), (int) (height * .6));
                break;

            case FoodifyTags.HOT_DOG:
                setContentView(R.layout.activity_pop_up_hot_dog);

                //assegno i checkbox
                checkboxBreadCrumbs=(CheckBox) findViewById(R.id.checkBoxBreadcrumbs);
                checkboxHotdogOnions=(CheckBox) findViewById(R.id.checkBoxHotdogOnions);
                checkboxMustard=(CheckBox) findViewById(R.id.checkBoxMustard);
                checkboxHotdogMayo=(CheckBox) findViewById(R.id.checkBoxHotdogMayo);
                checkboxHotdogKetchup=(CheckBox) findViewById(R.id.checkBoxHotdogKetchup);

                //aggiungo gli ingredienti possibili
                hotDog.addIngredient(FoodifyConstants.BREADCRUMBS,FoodifyConstants.BREADCRUMBS_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hotDog.addIngredient(FoodifyConstants.ONIONS,FoodifyConstants.ONIONS_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hotDog.addIngredient(FoodifyConstants.MUSTARD,FoodifyConstants.MUSTARD_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hotDog.addIngredient(FoodifyConstants.MAYONNAISE,FoodifyConstants.MAYONNAISE_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                hotDog.addIngredient(FoodifyConstants.KETCHUP,FoodifyConstants.KETCHUP_PRICE,FoodifyConstants.ADDITION_CATEGORY);

                //dico cosa deve fare il tasto  conferma
                confirmHotdog= (Button) findViewById(R.id.buttonConfirmHotdog);
                confirmHotdog.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        hotDog.setIngredientSelected(FoodifyConstants.BREADCRUMBS,checkboxBreadCrumbs.isChecked());
                        hotDog.setIngredientSelected(FoodifyConstants.ONIONS,checkboxHotdogOnions.isChecked());
                        hotDog.setIngredientSelected(FoodifyConstants.MUSTARD,checkboxMustard.isChecked());
                        hotDog.setIngredientSelected(FoodifyConstants.MAYONNAISE,checkboxHotdogMayo.isChecked());
                        hotDog.setIngredientSelected(FoodifyConstants.KETCHUP,checkboxHotdogKetchup.isChecked());

                        hotDog.setTotalPrice();
                        Log.d("Controllo", hotDog.toString());
                        setOrder(hotDog.getTotalPrice(),hotDog.toString());
                        finish();
                    }
                });

                getWindow().setLayout((int) (width * .7), (int) (height * .5));
                break;

            case FoodifyTags.FRIES:
                setContentView(R.layout.activity_pop_up_fries);

                //assegno i checkbox
                checkboxBarbecueSauce=(CheckBox) findViewById(R.id.checkBoxBarbecueSauce);
                checkboxFriesMayo=(CheckBox) findViewById(R.id.checkBoxFriesMayo);
                checkboxFriesKetchup=(CheckBox) findViewById(R.id.checkBoxFriesKetchup);

                //aggiungo gli ingredienti possibili
                fries.addIngredient(FoodifyConstants.BARBECUE_SAUCE,FoodifyConstants.BARBECUE_SAUCE_PRICE,FoodifyConstants.ADDITION_CATEGORY);                hotDog.addIngredient(FoodifyConstants.MAYONNAISE,FoodifyConstants.MAYONNAISE_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                fries.addIngredient(FoodifyConstants.MAYONNAISE,FoodifyConstants.MAYONNAISE_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                fries.addIngredient(FoodifyConstants.KETCHUP,FoodifyConstants.KETCHUP_PRICE,FoodifyConstants.ADDITION_CATEGORY);

                //dico cosa deve fare il tasto conferma
                confirmFries= (Button) findViewById(R.id.buttonConfirmFries);
                confirmFries.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        fries.setIngredientSelected(FoodifyConstants.BARBECUE_SAUCE,checkboxBarbecueSauce.isChecked());
                        fries.setIngredientSelected(FoodifyConstants.MAYONNAISE,checkboxFriesMayo.isChecked());
                        fries.setIngredientSelected(FoodifyConstants.KETCHUP,checkboxFriesKetchup.isChecked());

                        fries.setTotalPrice();
                        Log.d("Controllo", fries.toString());
                        setOrder(fries.getTotalPrice(),fries.toString());
                        finish();
                    }
                });

                getWindow().setLayout((int) (width * .6), (int) (height * .4));
                break;

            case FoodifyTags.DRINK:
                setContentView(R.layout.activity_pop_up_drink);

                //aggiungo gli ingredienti possibili
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

                //dico cosa deve fare il tasto conferma
                confirmDrink= (Button) findViewById(R.id.buttonConfirmDrink);
                confirmDrink.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        drink.setIngredientSelected((String)mSizeSpinner.getSelectedItem(),true);
                        drink.setIngredientSelected((String)mTypeSpinner.getSelectedItem(),true);

                        drink.setTotalPrice();
                        Log.d("Controllo", drink.toString());
                        setOrder(drink.getTotalPrice(),drink.toString());
                        finish();
                    }
                });

                getWindow().setLayout((int) (width * .6), (int) (height * .4));
                break;

            case FoodifyTags.DESSERT:
                setContentView(R.layout.activity_pop_up_dessert);

                //assegno i checkbox
                checkboxIceCream=(CheckBox) findViewById(R.id.checkboxIceCream);
                checkboxJellyBean=(CheckBox) findViewById(R.id.checkboxJallyBean);
                checkboxKitkat=(CheckBox) findViewById(R.id.checkboxKitKat);
                checkboxLollipop=(CheckBox) findViewById(R.id.checkboxLollipop);
                checkboxMarshmellow=(CheckBox) findViewById(R.id.checkboxMarshmellow);
                checkboxNougat=(CheckBox) findViewById(R.id.checkboxNougat);


                //aggiungo i vari dolci
                dessert.addIngredient(FoodifyConstants.ICE_CREAM_SANDWICH,FoodifyConstants.ICE_CREAM_SANDWICH_PRICE,FoodifyConstants.ADDITION_CATEGORY);                hotDog.addIngredient(FoodifyConstants.MAYONNAISE,FoodifyConstants.MAYONNAISE_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                dessert.addIngredient(FoodifyConstants.JELLY_BEAN,FoodifyConstants.JELLY_BEAN_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                dessert.addIngredient(FoodifyConstants.KIT_KAT,FoodifyConstants.KIT_KAT_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                dessert.addIngredient(FoodifyConstants.LOLLIPOP,FoodifyConstants.LOLLIPOP_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                dessert.addIngredient(FoodifyConstants.MARSHMELLOW,FoodifyConstants.MARSHMELLOW_PRICE,FoodifyConstants.ADDITION_CATEGORY);
                dessert.addIngredient(FoodifyConstants.NOUGAT,FoodifyConstants.NOUGAT_PRICE,FoodifyConstants.ADDITION_CATEGORY);


                //dico cosa deve fare il tasto conferma
                confirmDessert= (Button) findViewById(R.id.buttonConfirmDessert);
                confirmDessert.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dessert.setIngredientSelected(FoodifyConstants.ICE_CREAM_SANDWICH,checkboxIceCream.isChecked());
                        dessert.setIngredientSelected(FoodifyConstants.JELLY_BEAN,checkboxJellyBean.isChecked());
                        dessert.setIngredientSelected(FoodifyConstants.KIT_KAT,checkboxKitkat.isChecked());
                        dessert.setIngredientSelected(FoodifyConstants.LOLLIPOP,checkboxLollipop.isChecked());
                        dessert.setIngredientSelected(FoodifyConstants.MARSHMELLOW,checkboxMarshmellow.isChecked());
                        dessert.setIngredientSelected(FoodifyConstants.NOUGAT,checkboxNougat.isChecked());

                        dessert.setTotalPrice();
                        Log.d("Controllo", dessert.toString());
                        setOrder(dessert.getTotalPrice(),dessert.toString());
                        finish();

                    }
                });

                getWindow().setLayout((int) (width * .6), (int) (height * .8));
                break;
        }

    }
}
