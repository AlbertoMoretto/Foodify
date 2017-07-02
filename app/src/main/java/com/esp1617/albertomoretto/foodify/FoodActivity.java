package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

// Activity che gestisce il menù da cui si apriranno le varie pop up activty
public class FoodActivity extends AppCompatActivity {
    private float savedAccountValue;
    private float orderValue;

    private String orderComponents;
    private int orderSize;

    private TextView mAccountTextView;
    private TextView mOrderTextView;

    private Button mShowOrderButton;

    private LinearLayout mHamburgerLayout;
    private LinearLayout mHotDogLayout;
    private LinearLayout mFriesLayout;
    private LinearLayout mDrinkLayout;
    private LinearLayout mDessertLayout;
    private LinearLayout mAccountLayout;

    private Intent billIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(FoodifyTags.FOOD_ACTIVITY_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        orderValue = 0.0f;
        orderComponents = "";
        orderSize = 0;

        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE,Context.MODE_PRIVATE);
        savedAccountValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        mAccountTextView = (TextView) findViewById(R.id.account_value_text_view);
        mOrderTextView = (TextView) findViewById(R.id.order_value_text_view);

        mHamburgerLayout = (LinearLayout) findViewById(R.id.hamburger_linear_layout);
        mHotDogLayout = (LinearLayout) findViewById(R.id.hot_dog_linear_layout);
        mFriesLayout = (LinearLayout) findViewById(R.id.fries_linear_layout);
        mDrinkLayout = (LinearLayout) findViewById(R.id.drink_linear_layout);
        mDessertLayout = (LinearLayout) findViewById(R.id.dessert_linear_layout);
        mAccountLayout = (LinearLayout) findViewById(R.id.account_linear_layout);

        mShowOrderButton = (Button) findViewById(R.id.show_order);

        mAccountTextView.setText(""+savedAccountValue+"$");

        // Aggiungo i listener alle voci del menù
        mHamburgerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia PopUpActivity degli hamburger
                popUpLauncher(FoodifyTags.HAMBURGER);
            }
        });
        mHotDogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia PopUpActivity degli hotdog
                popUpLauncher(FoodifyTags.HOT_DOG);
            }
        });
        mFriesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia PopUpActivity delle patatine
                popUpLauncher(FoodifyTags.FRIES);
            }
        });
        mDrinkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia PopUpActivity delle bevande
                popUpLauncher(FoodifyTags.DRINK);
            }
        });
        mDessertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia PopUpActivity dei dessert
                popUpLauncher(FoodifyTags.DESSERT);
            }
        });


        mAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia AccountActivity
                openAccount();
            }
        });

        mShowOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancia activity del riepilogo ordine
                showOrder(orderValue,orderComponents,orderSize);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Controlla a che richiesta stiamo rispondendo
        if (requestCode == FoodifyTags.POPUP_CONFIRM_REQUEST) {
            // Si assicura che la richiesta abbia avuto successo
            if (resultCode == RESULT_OK) {
                orderSize++;
                orderComponents += data.getStringExtra(FoodifyTags.EXTRA_ORDER_SETTED);
                Log.d("Order",orderComponents);
                // ottiene i valori del prezzo e la lista ingredienti dalla PopUpActivity
                orderValue += data.getFloatExtra(FoodifyTags.EXTRA_PRICE_SETTED,0.0f);
                mOrderTextView.setText(""+orderValue+"$");
            }
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(FoodifyTags.FOOD_ACTIVITY_TAG, "onResume() called");
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE,Context.MODE_PRIVATE);
        savedAccountValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        if(mAccountTextView!=null) mAccountTextView.setText(""+savedAccountValue+"$");

    }


    /**
     * Metodo che avvia la PopUpActivity relativa alla stringa
     * @param componentCaller
     */
    private void popUpLauncher(String componentCaller){
        Intent intent = new Intent(this, PopUpActivity.class);
        intent.putExtra(FoodifyTags.SELECT_FOOD_CALLER, componentCaller);
        startActivityForResult(intent, FoodifyTags.POPUP_CONFIRM_REQUEST);

    }

    /**
     * Metodo che avvia l'activity YourOrderActivity che visualizzerà l'ordine
     * @param orderPrice prezzo dell'ordine
     * @param orderItems pietanze e ingredienti dell'ordine
     * @param totalOrder grandezza pietanze ordinate
     */
    private void showOrder(float orderPrice, String orderItems, int totalOrder) {
        Intent showOrder = new Intent(this, YourOrderActivity.class);
        showOrder.putExtra(FoodifyTags.EXTRA_PRICE_ORDER,orderPrice);
        showOrder.putExtra(FoodifyTags.EXTRA_ITEMS_ORDER,orderItems);
        showOrder.putExtra(FoodifyTags.EXTRA_SIZE_ORDER,totalOrder);
        startActivity(showOrder);
        finish();
    }

    /**
     * Metodo per avviare l'activity AccountActivity che mostrerà i soldi sul
     * proprio conto
     */
    public void openAccount(){
        billIntent = new Intent(this, AccountActivity.class);
        startActivity(billIntent);
    }


}
