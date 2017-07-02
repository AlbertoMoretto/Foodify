package com.esp1617.albertomoretto.foodify;

import android.app.NotificationManager;
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
import android.widget.Toast;

// Activity che completa l'ordine consentendo all'utente di pagare
public class CheckOutActivity extends AppCompatActivity {

    private float savedBillValue;
    private float billsTotal;
    private String itemsReady;
    private TextView mAccountTextView;
    private TextView mTotalBillTextView;
    private TextView mBillListTextView;
    private Button mPayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CheckoutActivity","Activity started");
        setContentView(R.layout.activity_check_out);

        //prendo i valori del prezzo e delle pietanze dalle shared preferences
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);

        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        SharedPreferences billToPay = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY,Context.MODE_PRIVATE);
        billsTotal = billToPay.getFloat(FoodifyTags.SHARED_BILL_TO_PAY, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        itemsReady = billToPay.getString(FoodifyTags.SHARED_ORDERS_LIST_READY,FoodifyConstants.DEFAULT_ITEMS_READY);

        Log.d("CheckoutActivity","Shared variables setted");
        Log.d("CheckoutActivity",itemsReady);

        // creo una notification manager che userò successivamente per lanciare le notifiche
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

        mAccountTextView = (TextView) findViewById(R.id.bill_account_textView);
        mTotalBillTextView = (TextView) findViewById(R.id.bill_total_textView);
        mBillListTextView = (TextView) findViewById(R.id.bill_items_textView);
        mPayButton = (Button) findViewById(R.id.bill_pay_button);

        mAccountTextView.setText(getResources().getString(R.string.account_label)+" $"+savedBillValue);
        mBillListTextView.setText(itemsReady);
        mTotalBillTextView.setText(getResources().getString(R.string.bill_total_order_label)+" $"+billsTotal);

        mBillListTextView.setMovementMethod(new ScrollingMovementMethod());

        // aggiungo il listener al bottone per pagare
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se ci sono abbastanza soldi sul conto
                if(savedBillValue>=billsTotal) {
                    savedBillValue = savedBillValue - billsTotal;

                    billsTotal = FoodifyConstants.DEFAULT_ACCOUNT_VALUE;
                    itemsReady = FoodifyConstants.DEFAULT_ITEMS_READY;

                    // shared preferences creata per salvarsi i valori dell'ordine
                    SharedPreferences billToPay = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = billToPay.edit();
                    editor2.putFloat(FoodifyTags.SHARED_BILL_TO_PAY, billsTotal);
                    editor2.putString(FoodifyTags.SHARED_ORDERS_LIST_READY, itemsReady);
                    editor2.apply();

                    // shared preferences creata per salvarsi il valore del proprio conto
                    SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat(FoodifyTags.BILL_VALUE, savedBillValue);
                    editor.apply();

                    // cambia la grafica dell'activity visualizzando il messaggio che il pagamento
                    // è stato effettuato con successo
                    mAccountTextView.setVisibility(View.GONE);
                    mTotalBillTextView.setVisibility(View.GONE);
                    mPayButton.setVisibility(View.GONE);
                    mBillListTextView.setPadding(FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN,FoodifyConstants.PADDING_LEFT_RIGHT,FoodifyConstants.PADDING_TOP_DOWN);
                    mBillListTextView.setTextSize(FoodifyConstants.TEXT_SIZE_ORDER_MESSAGE);
                    mBillListTextView.setTextColor(Color.GREEN);
                    mBillListTextView.setText(R.string.payment_success_label);

                    // fa in modo che una volta premuto sull'activity corrente torni al menù
                    mBillListTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           Intent home = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(home);
                            finish();

                        }
                    });
                } else {
                    // se non ci sono abbastanza soldi sul conto per pagare l'ordine visualizza un
                    // messaggio di errore
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.payment_failed_label, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        // aggiunge un listener alla textview del proprio conto per poter aprire AccountActivity
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
        // Controlla a che richiesta stiamo rispondendo
        if (requestCode == FoodifyTags.ACCOUNT_CHARGING) {
            // Si assicura che la richiesta abbia avuto successo
            if (resultCode == RESULT_OK) {
                Log.d("Activity result","ok");
            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        // Se l'activity viene riaperta dopo essere stata mandata in pausa va a controllare il
        // valore del proprio conto (che potrebbe essere stato aggiornato dall'utente) nelle shared
        // preferences
        SharedPreferences sharedPref = getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);
        savedBillValue = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        mAccountTextView.setText(getResources().getString(R.string.account_label)+" $"+savedBillValue);
    }

}
