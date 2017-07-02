package com.esp1617.albertomoretto.foodify;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Sottoclasse di IntentService per gestire richieste di task asincroni
 * in un servizio su un thread separato
 */
public class NotificationService extends IntentService {

    private static final long DEFAULT_PREPARATION_TIME=3000;

    private int orderDim;
    private long preparation_time;
    private int notifyID;
    private float totalP;
    private String selectedItems;
    private Uri alarmSound;
    private float billsTotal;
    private String itemsReady;


    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (FoodifyTags.ACTION_START.equals(action)) {
                notifyID = intent.getIntExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,0);
                orderDim = intent.getIntExtra(FoodifyTags.EXTRA_SIZE_ORDER,0);
                selectedItems = intent.getStringExtra(FoodifyTags.EXTRA_ITEMS_ORDER);
                totalP = intent.getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);
                preparation_time = orderDim*DEFAULT_PREPARATION_TIME;
                handleActionStartTimer(preparation_time,notifyID,selectedItems,totalP);
            }
        }
    }

    /**
     * Metodo per gestire la creazione della notifica di un'ordine in preparazione,
     * impostando nella notifica il cronometro il rispettivo tempo di preparazione,
     * e l'aggiornamento della notifica quando finisce il timer.
     * @param time tempo di preparazione dell'ordine
     * @param notifyIDN ID della notifica da creare
     * @param selectedItemsN stringa contenente gli elementi dell'ordinazione
     * @param totalPN prezzo totale dell'ordine
     */
    private void handleActionStartTimer(long time, int notifyIDN, String selectedItemsN, float totalPN) {

        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final Notification.Builder mNotifyBuilder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_order)+" "+notifyIDN)
                .setContentText(getResources().getString(R.string.notification_order_preparation_text))
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.foodify_notification)
                .setUsesChronometer(true)
                .setChronometerCountDown(true)
                .setWhen(System.currentTimeMillis()+time)
                .setShowWhen(true)
                .setOngoing(true);


        mNotificationManager.notify(notifyIDN,
                mNotifyBuilder.build());

        try {
            Thread.sleep(time);
        }catch(InterruptedException error) {
            Log.d("Thread","ERROR SLEEP");
        }


        Intent i = new Intent(getApplicationContext(), CheckOutActivity.class);
        i.putExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,notifyIDN);
        i.putExtra(FoodifyTags.EXTRA_ITEMS_ORDER,selectedItemsN);
        i.putExtra(FoodifyTags.EXTRA_PRICE_ORDER,totalPN);
        PendingIntent pendI = PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPay = new Intent();
        intentPay.setAction(FoodifyTags.CUSTOM_INTENT_PAYMENT);

        intentPay.putExtra(FoodifyTags.EXTRA_PRICE_ORDER,totalPN);
        intentPay.putExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,notifyIDN);
        PendingIntent pendIntentPay = PendingIntent.getBroadcast(getApplicationContext(),FoodifyTags.BROADCAST_PAYMENT_REQUEST_CODE,intentPay,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Action action = new Notification.Action.Builder(R.drawable.order_icon_button, getString(R.string.bill_pay_label), pendIntentPay).build();

        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mNotifyBuilder.setContentTitle(getResources().getString(R.string.notification_order_ready))
                .setContentText(getResources().getString(R.string.notification_order_done))
                .setOngoing(false)
                .setUsesChronometer(false)
                .addAction(action)
                .setSound(alarmSound);

        Notification.BigTextStyle bill = new Notification.BigTextStyle();
        bill.setBigContentTitle(getResources().getString(R.string.notification_order_ready));
        bill.bigText(selectedItemsN);
        mNotifyBuilder.setStyle(bill)
                .setContentIntent(pendI);

        SharedPreferences billToPay = getSharedPreferences(FoodifyTags.SHARED_PREF_ORDER_READY,Context.MODE_PRIVATE);
        billsTotal = billToPay.getFloat(FoodifyTags.SHARED_BILL_TO_PAY, FoodifyConstants.DEFAULT_ACCOUNT_VALUE);
        itemsReady = billToPay.getString(FoodifyTags.SHARED_ORDERS_LIST_READY,FoodifyConstants.DEFAULT_ITEMS_READY);

        billsTotal += totalPN;
        itemsReady = itemsReady + selectedItemsN;

        SharedPreferences.Editor editor = billToPay.edit();
        editor.putFloat(FoodifyTags.SHARED_BILL_TO_PAY, billsTotal);
        editor.putString(FoodifyTags.SHARED_ORDERS_LIST_READY, itemsReady);
        editor.apply();


        mNotificationManager.notify(notifyIDN,
                mNotifyBuilder.build());
    }

}
