package com.esp1617.albertomoretto.foodify;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {

    private static final long DEFAULT_PREPARATION_TIME=3000;

    private int orderDim;
    private long preparation_time;
    private long orderPrep;
    private long minutes;
    private long seconds;
    private int notifyID;
    private float totalP;
    private String selectedItems;

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.esp1617.albertomoretto.foodify.action.FOO";
    private static final String ACTION_BAZ = "com.esp1617.albertomoretto.foodify.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.esp1617.albertomoretto.foodify.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.esp1617.albertomoretto.foodify.extra.PARAM2";

    public NotificationService() {
        super("NotificationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
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
                handleActionStartTimer(preparation_time);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action startTimer in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStartTimer(float time) {

        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final Notification.Builder mNotifyBuilder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_order)+" "+notifyID+" "+getResources().getString(R.string.notification_order_preparation_title))
                .setContentText(getResources().getString(R.string.notification_order_preparation_text))
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.foodify_notification)
                .setUsesChronometer(true)
                .setChronometerCountDown(true)
                .setWhen(orderPrep=System.currentTimeMillis()+preparation_time)
                .setShowWhen(true)
                .setOngoing(true);


        mNotificationManager.notify(notifyID,
                mNotifyBuilder.build());

        Log.d("WHILE","comincia il ciclo");
        while((System.currentTimeMillis()-orderPrep)<0) {Log.d("WAIT","Non ancora");}

        mNotifyBuilder.setContentTitle(getResources().getString(R.string.notification_order_ready))
                .setContentText(getResources().getString(R.string.notification_order_done))
                .setUsesChronometer(false)
                .setOngoing(false);
        mNotificationManager.notify(notifyID,
                mNotifyBuilder.build());


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
