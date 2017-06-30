package com.esp1617.albertomoretto.foodify;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class PayReceiver extends BroadcastReceiver {
    private float billsTotal;
    private float myAccount;
    private String itemsReady;
    private String readyID;
    private String[] readyIDList;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences billToPay = context.getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY,Context.MODE_PRIVATE);
        billsTotal = billToPay.getFloat(FoodifyTags.SHARED_BILL_TO_PAY, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        itemsReady = billToPay.getString(FoodifyTags.SHARED_ORDERS_LIST_READY,FoodifyConstants.DEFAULT_ITEMS_READY);
        readyID = billToPay.getString(FoodifyTags.SHARED_ID_ORDERS_READY,FoodifyConstants.DEFAULT_ITEMS_READY);
        SharedPreferences sharedPref = context.getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);
        myAccount = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        SharedPreferences.Editor editorAcc = sharedPref.edit();
        SharedPreferences.Editor editor = billToPay.edit();

        Log.d("PayReceiver","Broadcast receiver attivato");
        Log.d("Soldi account",""+myAccount);
        Log.d("Totale ordine",""+billsTotal);
        if(myAccount >= billsTotal) {
            myAccount -= billsTotal;

            billsTotal = FoodifyConstants.DEFAULT_ACCOUNT_VALUE;
            itemsReady = FoodifyConstants.DEFAULT_ITEMS_READY;

            final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            readyIDList = readyID.split("/");
            for (String s : readyIDList) {
                Log.d("TEMP ID",s);
                int tempID =Integer.parseInt(s);
                mNotificationManager.cancel(tempID);
            }
            readyID = FoodifyConstants.DEFAULT_ITEMS_READY;

            editor.putString(FoodifyTags.SHARED_ID_ORDERS_READY, readyID);
            editor.putFloat(FoodifyTags.SHARED_BILL_TO_PAY, billsTotal);
            editor.putString(FoodifyTags.SHARED_ORDERS_LIST_READY, itemsReady);
            editor.commit();

            editorAcc.putFloat(FoodifyTags.BILL_VALUE, myAccount);
            editorAcc.commit();

        } else {
            Toast paymentFailed = Toast.makeText(context,"You don't have enough money! \n Tap on notification body.",Toast.LENGTH_LONG);
            paymentFailed.show();
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
