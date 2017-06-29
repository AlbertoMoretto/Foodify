package com.esp1617.albertomoretto.foodify;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CountdownService extends Service {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static String ACTION_START = "TimerStart";
   // private static final String ACTION_BAZ = "com.example.aleci.provanotifiche.action.BAZ";

    // TODO: Rename parameters
    //private static final String EXTRA_PARAM1 = "com.example.aleci.provanotifiche.extra.PARAM1";
    //private static final String EXTRA_PARAM2 = "com.example.aleci.provanotifiche.extra.PARAM2";

    public static final long DEFAULT_PREPARATION_TIME=3000;

    private int orderDim;
    private long preparation_time;
    private long minutes;
    private long seconds;
    private int notifyID;
    private float totalP;
    private String selectedItems;

    public CountdownService() {
        System.out.println("SERVICE AVVIATO!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        if(intent.getBooleanExtra(ACTION_START, false)) {
            notifyID = intent.getIntExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,0);
            orderDim = intent.getIntExtra(FoodifyTags.EXTRA_SIZE_ORDER,0);
            selectedItems = intent.getStringExtra(FoodifyTags.EXTRA_ITEMS_ORDER);
            totalP = intent.getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);
            preparation_time = orderDim*DEFAULT_PREPARATION_TIME;
            handleActionStartTimer(preparation_time);
        }
        return Service.START_REDELIVER_INTENT;
    }


   /* @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                handleActionStartTimer(preparation_time);
            }
        }
    }*/

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStartTimer(float time) {
/*
        // Runs this service in the foreground,
        // supplying the ongoing notification to be shown to the user
        final Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Your order will be ready in:")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        final int notificationID = 5786423; // An ID for this notification unique within the app
        startForeground(notificationID, notification);

   */
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Sets an ID for the notification, so it can be updated
        //final int notifyID = 5786423;
        final android.support.v4.app.NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_order)+" "+notifyID+" "+getResources().getString(R.string.notification_order_preparation_title))
                .setContentText(getResources().getString(R.string.notification_order_preparation_text))
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.food_icon_button);


        //numMessages = 0;

        minutes=preparation_time/60000;
        seconds=preparation_time%60000;

        new CountDownTimer(preparation_time, 1000) {

            /*SharedPreferences sharedPrefNotify = getSharedPreferences(FoodifyTags.ORDER_NOTIFICATION, Context.MODE_PRIVATE);
            int notifyID = sharedPrefNotify.getInt(FoodifyTags.ORDER_NOTIFICATION, FoodifyConstants.DEFAULT_ORDER_ID);*/
            int currentNotifyID = notifyID;
            String currentBill = selectedItems;
            float currentTotal = totalP;
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished/60000>1){
                    mNotifyBuilder.setContentText(""+millisUntilFinished/60000+":"+(millisUntilFinished%60000)/1000);
                }else{
                    mNotifyBuilder.setContentText("00:"+(millisUntilFinished%60000)/1000)
                                  .setOngoing(true);

                }

                mNotificationManager.notify(currentNotifyID,
                        mNotifyBuilder.build());

            }

            public void onFinish() {
                Intent i = new Intent(getApplicationContext(), CheckOutActivity.class);
                i.putExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,currentNotifyID);
                i.putExtra(FoodifyTags.EXTRA_ITEMS_ORDER,currentBill);
                i.putExtra(FoodifyTags.EXTRA_PRICE_ORDER,currentTotal);
                PendingIntent pendI = PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);

                String replyLabel = getResources().getString(R.string.bill_pay_label);
                RemoteInput remoteInput = new RemoteInput.Builder(FoodifyTags.KEY_ORDER_PAY)
                        .setLabel(replyLabel)
                        .build();

                // Create the reply action and add the remote input.
                /*Notification.Action action =
                        new Notification.Action.Builder(R.drawable.order_icon_button,
                                getString(R.string.bill_pay_label), pendI)
                                .addRemoteInput(remoteInput)
                                .build();*/
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.order_icon_button, getString(R.string.bill_pay_label), pendI).build();


                mNotifyBuilder.setContentTitle(getResources().getString(R.string.notification_order_ready))
                              .setContentText(getResources().getString(R.string.notification_order_done))
                              .setOngoing(false)
                              .addAction(action);

                NotificationCompat.BigTextStyle bill = new NotificationCompat.BigTextStyle();
                bill.setBigContentTitle(getResources().getString(R.string.notification_order_ready));
                bill.bigText(currentBill);
                mNotifyBuilder.setStyle(bill)
                              .setContentIntent(pendI);
                mNotificationManager.notify(currentNotifyID, mNotifyBuilder.build());


                //TODO : lanciare notifica pagamento

            }
        }.start();

    }


    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
