package com.esp1617.albertomoretto.foodify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("PayReceiver","Broadcast receiver attivato");
        float total = intent.getFloatExtra(FoodifyTags.EXTRA_PRICE_ORDER,0.0f);
        if(total==6) Log.d("tutto","apposto");
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
