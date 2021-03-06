package com.esp1617.albertomoretto.foodify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity per azzerare tutte le variabili dell'applicazione, cioè il valore del conto, l'ID degli ordini
 * (e rispettive notifiche), totale prezzo ordini in sospeso, lista elementi degli ordini in sospeso.
 * Dopo aver premuto il bottone RESET verrà visualizzato un messaggio di avvenuto azzeramento delle variabili.
 */
public class ResetActivity extends AppCompatActivity {
    private Button mResetButton;
    private TextView mResetTextView;
    private float billsTotal;
    private float savedBillValue;
    private String itemsReady;
    private int countID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences billToPay = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY, Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);

        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        billsTotal = billToPay.getFloat(FoodifyTags.SHARED_BILL_TO_PAY, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        itemsReady = billToPay.getString(FoodifyTags.SHARED_ORDERS_LIST_READY,FoodifyConstants.DEFAULT_ITEMS_READY);
        countID = billToPay.getInt(FoodifyTags.ORDER_NOTIFICATION, FoodifyConstants.DEFAULT_ORDER_ID);
        final SharedPreferences.Editor editorBill = billToPay.edit();
        final SharedPreferences.Editor editorAcc = sharedPref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetTextView = (TextView) findViewById(R.id.reset_textView);


        mResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResetButton.setVisibility(View.GONE);
                mResetTextView.setTextColor(Color.BLACK);
                mResetTextView.setText(getResources().getString(R.string.reset_OK_message));

                savedBillValue  = FoodifyConstants.DEFAULT_ACCOUNT_VALUE;
                billsTotal = FoodifyConstants.DEFAULT_ACCOUNT_VALUE;
                itemsReady = FoodifyConstants.DEFAULT_ITEMS_READY;
                countID = FoodifyConstants.DEFAULT_ORDER_ID;

                editorBill.putFloat(FoodifyTags.SHARED_BILL_TO_PAY, billsTotal);
                editorBill.putString(FoodifyTags.SHARED_ORDERS_LIST_READY, itemsReady);
                editorBill.putInt(FoodifyTags.ORDER_NOTIFICATION, countID);
                editorBill.apply();
                editorAcc.putFloat(FoodifyTags.BILL_VALUE, savedBillValue);
                editorAcc.apply();
            }
        });


    }
}
