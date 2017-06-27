package com.esp1617.albertomoretto.foodify;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YourOrderActivity extends AppCompatActivity {

    private float totalPrice;
    private String selectedItems;
    private TextView mOrderItemsTextView;
    private TextView mOrderTitle;
    private Button mConfirmButton;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);


        mOrderItemsTextView = (TextView) findViewById(R.id.bill);
        mOrderTitle = (TextView) findViewById(R.id.order_title);
        mConfirmButton = (Button) findViewById(R.id.confirm_bill);
        mDeleteButton = (Button) findViewById(R.id.delete_bill);

        totalPrice = getIntent().getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);
        selectedItems = getIntent().getStringExtra(FoodifyTags.EXTRA_ITEMS_ORDER);

        mOrderItemsTextView.setText(selectedItems);

        mOrderItemsTextView.setMovementMethod(new ScrollingMovementMethod());

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderTitle.setVisibility(View.GONE);
                mConfirmButton.setVisibility(View.GONE);
                mDeleteButton.setVisibility(View.GONE);
                mOrderItemsTextView.setPadding(FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN,FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN);
                mOrderItemsTextView.setTextSize(FoodifyConstants.TEXT_SIZE_ORDER_MESSAGE);
                mOrderItemsTextView.setTextColor(Color.GREEN);
                mOrderItemsTextView.setText(R.string.order_processing);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderTitle.setVisibility(View.GONE);
                mConfirmButton.setVisibility(View.GONE);
                mDeleteButton.setVisibility(View.GONE);
                mOrderItemsTextView.setPadding(FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN,FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN);
                mOrderItemsTextView.setTextSize(FoodifyConstants.TEXT_SIZE_ORDER_MESSAGE);
                mOrderItemsTextView.setTextColor(Color.RED);
                mOrderItemsTextView.setText(R.string.order_deleted);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
            Intent i = new Intent(this, FoodActivity.class);
            startActivity(i);
            finish();
    }
}
