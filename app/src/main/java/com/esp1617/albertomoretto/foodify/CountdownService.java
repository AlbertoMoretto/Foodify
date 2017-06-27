package com.esp1617.albertomoretto.foodify;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
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

    private long preparation_time=30000;
    private long minutes;
    private long seconds;

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

        if(intent.getBooleanExtra(ACTION_START, false)) handleActionStartTimer(preparation_time);
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
        final int notifyID = 5786423;
        final android.support.v4.app.NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_order_preparation_title))
                .setContentText(getResources().getString(R.string.notification_order_preparation_text))
                .setSmallIcon(R.drawable.ic_stat_simple);


        //numMessages = 0;

        minutes=preparation_time/60000;
        seconds=preparation_time%60000;

        new CountDownTimer(preparation_time, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished/60000>1){
                    mNotifyBuilder.setContentText(""+millisUntilFinished/60000+":"+(millisUntilFinished%60000)/1000);
                }else{
                    mNotifyBuilder.setContentText("00:"+(millisUntilFinished%60000)/1000);

                }

                mNotificationManager.notify(notifyID,
                        mNotifyBuilder.build());

            }

            public void onFinish() {
                mNotifyBuilder.setContentText("done!");
                mNotificationManager.notify(notifyID, mNotifyBuilder.build());

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
