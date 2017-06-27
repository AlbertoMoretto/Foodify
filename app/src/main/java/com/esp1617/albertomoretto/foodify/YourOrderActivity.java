package com.esp1617.albertomoretto.foodify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class YourOrderActivity extends AppCompatActivity {

    private float totalPrice;
    private String selectedItems;

    private TextView mOrderItemsTextView;
    private Button mConfirmButton;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);

        mOrderItemsTextView = (TextView) findViewById(R.id.bill);
        mConfirmButton = (Button) findViewById(R.id.confirm_bill);
        mDeleteButton = (Button) findViewById(R.id.delete_bill);

        totalPrice = getIntent().getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);
        selectedItems = getIntent().getStringExtra(FoodifyTags.EXTRA_ITEMS_ORDER);

        mOrderItemsTextView.setText(selectedItems);
    }
}
