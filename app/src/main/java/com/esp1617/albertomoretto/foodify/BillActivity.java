package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BillActivity extends AppCompatActivity {

    private TextView mBillTextView;
    private Button mAddMoneyButton;
    private float currentBillValue;
    private float savedBillValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        mBillTextView = (TextView) findViewById(R.id.bill_text_view);
        mAddMoneyButton = (Button) findViewById(R.id.add_money_button);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_BILL_VALUE);
        mBillTextView.setText(""+savedBillValue+"$");
        mAddMoneyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add money to your bill
                currentBillValue = savedBillValue + FoodifyConstants.STANDARD_BILL_UPDATE_VALUE;
                mBillTextView.setText(""+ currentBillValue +"$"); //Hardcoded $ and €
                savedBillValue = currentBillValue;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(FoodifyTags.BILL_VALUE, savedBillValue);
        editor.commit();


    }
}
