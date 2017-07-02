package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YourOrderActivity extends AppCompatActivity {

    private float totalPrice;
    private int totalOrderSize;
    private String selectedItems;
    private TextView mOrderItemsTextView;
    private TextView mOrderTitle;
    private Button mConfirmButton;
    private Button mDeleteButton;
    private int notifyID;

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
        totalOrderSize = getIntent().getIntExtra(FoodifyTags.EXTRA_SIZE_ORDER,0);

        SharedPreferences sharedPrefNotify = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY, Context.MODE_PRIVATE);
        Log.d("sharedpref", "good");
        notifyID = sharedPrefNotify.getInt(FoodifyTags.ORDER_NOTIFICATION, FoodifyConstants.DEFAULT_ORDER_ID);


        mOrderItemsTextView.setText(selectedItems);

        mOrderItemsTextView.setMovementMethod(new ScrollingMovementMethod());

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent timerNotification = new Intent(getApplicationContext(),NotificationService.class);
                timerNotification.putExtra(FoodifyTags.EXTRA_SIZE_ORDER,totalOrderSize);
                timerNotification.putExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,notifyID);
                timerNotification.putExtra(FoodifyTags.EXTRA_ITEMS_ORDER,selectedItems);
                timerNotification.putExtra(FoodifyTags.EXTRA_PRICE_ORDER,totalPrice);
                timerNotification.setAction(FoodifyTags.ACTION_START);
                startService(timerNotification);

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(FoodifyTags.EXTRA_EXIT_APP,true);
                updateNotifyID();
                startActivity(i);
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

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(FoodifyTags.ORDER_NOTIFICATION, notifyID);
        editor.commit();


    }

    private void updateNotifyID()
    {
        if(notifyID<FoodifyConstants.MAX_NOTIFY_ID) notifyID++;
        else notifyID = 0;
        SharedPreferences sharedPrefNotify = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefNotify.edit();
        editor.putInt(FoodifyTags.ORDER_NOTIFICATION, notifyID);
        editor.commit();
    }
}
