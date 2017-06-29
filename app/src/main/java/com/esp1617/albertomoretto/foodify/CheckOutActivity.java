package com.esp1617.albertomoretto.foodify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CheckOutActivity extends AppCompatActivity {
    private int notifyID;
    private String bill;
    private float total;
    private float savedBillValue;
    private String account;
    private String totalOrder;


    private TextView mAccountTextView;
    private TextView mTotalBillTextView;
    private TextView mBillListTextView;
    private Button mPayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);


        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        notifyID = getIntent().getIntExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,0);
        bill = getIntent().getStringExtra(FoodifyTags.EXTRA_ITEMS_ORDER);
        total = getIntent().getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);

        mAccountTextView = (TextView) findViewById(R.id.bill_account_textView);
        mTotalBillTextView = (TextView) findViewById(R.id.bill_total_textView);
        mBillListTextView = (TextView) findViewById(R.id.bill_items_textView);
        mPayButton = (Button) findViewById(R.id.bill_pay_button);

        /*account = getResources().getString(R.string.account_label)+" "+savedBillValue;
        totalOrder = getResources().getString(R.string.bill_total_order_label)+" "+total;*/

        mAccountTextView.setText(getResources().getString(R.string.account_label)+" $"+savedBillValue);
        mBillListTextView.setText(bill);
        mTotalBillTextView.setText(getResources().getString(R.string.bill_total_order_label)+" $"+total);

        mBillListTextView.setMovementMethod(new ScrollingMovementMethod());

        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notifyID);

        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(savedBillValue>=total) {
                    savedBillValue = savedBillValue - total;

                    SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat(FoodifyTags.BILL_VALUE, savedBillValue);
                    editor.commit();

                    mAccountTextView.setVisibility(View.GONE);
                    mTotalBillTextView.setVisibility(View.GONE);
                    mPayButton.setVisibility(View.GONE);
                    mBillListTextView.setPadding(FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN,FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN);
                    mBillListTextView.setTextSize(FoodifyConstants.TEXT_SIZE_ORDER_MESSAGE);
                    mBillListTextView.setTextColor(Color.GREEN);
                    mBillListTextView.setText(R.string.payment_success_label);

                    mBillListTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           Intent home = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(home);
                            finish();

                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.payment_failed_label, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        mAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivityForResult(intent, FoodifyTags.ACCOUNT_CHARGING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == FoodifyTags.ACCOUNT_CHARGING) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);
        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        mAccountTextView.setText(getResources().getString(R.string.account_label)+" $"+savedBillValue);
    }

}
