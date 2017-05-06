package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {
    private float savedAccountValue;
    private TextView mAccountTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE,Context.MODE_PRIVATE);
        savedAccountValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        Log.d("ddda", ""+savedAccountValue);
        mAccountTextView = (TextView) findViewById(R.id.account_value_text_view);
        mAccountTextView.setText(""+savedAccountValue+"$");

    }
}
