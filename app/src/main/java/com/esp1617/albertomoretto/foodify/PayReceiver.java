package com.esp1617.albertomoretto.foodify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.service.notification.StatusBarNotification;
import android.util.Log;


/**
 * Sottoclasse di BroadcastReceiver per gestire il pagamento di uno o più ordini direttamente dal
 * tasto PAY della notifica, nel caso i soldi presenti nel conto non siano sufficienti al pagamento
 * viene aggiornata la notifica (con un messaggio di fallito pagamento) e richiesto di pagare l'ordine
 * aprendo l'apposita activity (CheckOutActivity),
 * In caso di pagamento riuscito viene aggiornata la notifica con un messaggio di pagamento riuscito
 * e aggiornati i valori dell'applicazione (valore del conto, prezzo totale ordini in sospeso, lista elementi
 * non ancora pagati).
 */
public class PayReceiver extends BroadcastReceiver {
    private float billsTotal;
    private float myAccount;
    private String itemsReady;
    private int notificationIDCaller;
    private StatusBarNotification[] activeNotification;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationIDCaller = intent.getIntExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,FoodifyConstants.DEFAULT_ORDER_ID);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences billToPay = context.getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY,Context.MODE_PRIVATE);
        billsTotal = billToPay.getFloat(FoodifyTags.SHARED_BILL_TO_PAY, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        itemsReady = billToPay.getString(FoodifyTags.SHARED_ORDERS_LIST_READY,FoodifyConstants.DEFAULT_ITEMS_READY);
        SharedPreferences sharedPref = context.getSharedPreferences(FoodifyTags.BILL_VALUE, Context.MODE_PRIVATE);
        myAccount = sharedPref.getFloat(FoodifyTags.BILL_VALUE, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);

        SharedPreferences.Editor editorAcc = sharedPref.edit();
        SharedPreferences.Editor editor = billToPay.edit();

        Log.d("PayReceiver","Broadcast receiver attivato");
        Log.d("Soldi account",""+myAccount);
        Log.d("Totale ordine",""+billsTotal);

        final Notification.Builder mNotifyBuilder = new Notification.Builder(context);
        activeNotification = mNotificationManager.getActiveNotifications();
        for(int x = 0; x<activeNotification.length;x++)
        {
            if(activeNotification[x].getId() != notificationIDCaller) mNotificationManager.cancel(activeNotification[x].getId());
        }

        if(myAccount >= billsTotal) {
            myAccount -= billsTotal;

            billsTotal = FoodifyConstants.DEFAULT_ACCOUNT_VALUE;
            itemsReady = FoodifyConstants.DEFAULT_ITEMS_READY;


            Intent ok = new Intent(context, MainActivity.class);
            ok.putExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,notificationIDCaller);
            PendingIntent pendOk = PendingIntent.getActivity(context,0,ok,PendingIntent.FLAG_UPDATE_CURRENT);

            mNotifyBuilder.setContentTitle(context.getResources().getString(R.string.notification_payment_success_title))
                    .setContentText(context.getResources().getString(R.string.notification_payment_success_text))
                    .setColor(Color.GREEN)
                    .setSmallIcon(R.drawable.foodify_notification)
                    .setContentIntent(pendOk);
            mNotificationManager.notify(notificationIDCaller,
                    mNotifyBuilder.build());


            editor.putFloat(FoodifyTags.SHARED_BILL_TO_PAY, billsTotal);
            editor.putString(FoodifyTags.SHARED_ORDERS_LIST_READY, itemsReady);
            editor.apply();

            editorAcc.putFloat(FoodifyTags.BILL_VALUE, myAccount);
            editorAcc.apply();

        } else {

            Intent i = new Intent(context, CheckOutActivity.class);
            PendingIntent pendI = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            mNotifyBuilder.setContentTitle(context.getResources().getString(R.string.notification_payment_failed_title))
                    .setContentText(context.getResources().getString(R.string.notification_payment_failed_text))
                    .setColor(Color.GREEN)
                    .setSmallIcon(R.drawable.foodify_notification)
                    .setContentIntent(pendI);

            mNotificationManager.notify(notificationIDCaller,
                    mNotifyBuilder.build());

        }
    }
}
