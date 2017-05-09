package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {
    private float savedAccountValue;

    private TextView mAccountTextView;

    private LinearLayout mHamburgerLayout;
    private LinearLayout mHotDogLayout;
    private LinearLayout mFriesLayout;
    private LinearLayout mDrinkLayout;
    private LinearLayout mDessertLayout;
    private LinearLayout mOrderLayout;
    private LinearLayout mAccountLayout;

    private Intent billIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(FoodifyTags.FOOD_ACTIVITY_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE,Context.MODE_PRIVATE);
        savedAccountValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        mAccountTextView = (TextView) findViewById(R.id.account_value_text_view);

        mHamburgerLayout = (LinearLayout) findViewById(R.id.hamburger_linear_layout);
        mHotDogLayout = (LinearLayout) findViewById(R.id.hot_dog_linear_layout);
        mFriesLayout = (LinearLayout) findViewById(R.id.fries_linear_layout);
        mDrinkLayout = (LinearLayout) findViewById(R.id.drink_linear_layout);
        mDessertLayout = (LinearLayout) findViewById(R.id.dessert_linear_layout);
        mOrderLayout = (LinearLayout) findViewById(R.id.order_linear_layout);
        mAccountLayout = (LinearLayout) findViewById(R.id.account_linear_layout);

        mAccountTextView.setText(""+savedAccountValue+"$");

        mHamburgerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch PopUpActivity with data from the launcher
                popUpLauncher(FoodifyTags.HAMBURGER);
            }
        });
        mHotDogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch PopUpActivity with data from the launcher
                popUpLauncher(FoodifyTags.HOT_DOG);
            }
        });
        mFriesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch PopUpActivity with data from the launcher
                popUpLauncher(FoodifyTags.FRIES);
            }
        });
        mDrinkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch PopUpActivity with data from the launcher
                popUpLauncher(FoodifyTags.DRINK);
            }
        });
        mDessertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch PopUpActivity with data from the launcher
                popUpLauncher(FoodifyTags.DESSERT);
            }
        });

        mOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch CheckoutActivity which show food ordered by user
                openOrder();
            }
        });
        mAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch AccountActivity
                openAccount();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(FoodifyTags.FOOD_ACTIVITY_TAG, "onResume() called");
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE,Context.MODE_PRIVATE);
        savedAccountValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        if(mAccountTextView!=null) mAccountTextView.setText(""+savedAccountValue+"$");

    }

    //method used to start PopUpActivity, passing to it the caller via intent putExtra
    private void popUpLauncher(String componentCaller){
        Intent intent = new Intent(this, PopUpActivity.class);
        intent.putExtra(FoodifyTags.SELECT_FOOD_CALLER, componentCaller);
        startActivity(intent);

    }
    // THIS MUST BE CHANGED, openAccount is declared in the same way in MainActivity and it does the same thing
    public void openAccount(){
        billIntent = new Intent(this, AccountActivity.class);
        startActivity(billIntent);
    }

    private void openOrder(){
        //Launch an activity that represents items choosen by the user
    }

}
