package com.esp1617.albertomoretto.foodify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ImageButton mBillImageButton;
    private ImageButton mFoodImageButton;
    private ImageButton mAlaSoftImageButton;
    private ImageButton mSettingsImageButton;
    private int pendingNotification;

    private Toast creditToast;
    private Intent billIntent, foodIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingNotification=getIntent().getIntExtra(FoodifyTags.EXTRA_NOTIFY_ID_ORDER,0);
        Log.d("Notification ID",""+pendingNotification);
        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean(FoodifyTags.EXTRA_EXIT_APP,false)) finish();
        else if(pendingNotification!=0)
        {
            Log.d("Notification ID",""+pendingNotification);
            final NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(pendingNotification);

        }
        setContentView(R.layout.activity_main);
        mBillImageButton = (ImageButton) findViewById(R.id.imageButtonBill);
        mFoodImageButton = (ImageButton) findViewById(R.id.imageButtonFood);
        mAlaSoftImageButton = (ImageButton) findViewById(R.id.imageButtonAlasoft);
        mSettingsImageButton = (ImageButton) findViewById(R.id.imageButtonSettings);

        mBillImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAccount();
            }
        });

        mFoodImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openFood();
            }
        });

        mAlaSoftImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creditToast = Toast.makeText(getApplicationContext(), R.string.foodify_credits, Toast.LENGTH_SHORT);
                creditToast.show();
            }
        });

        mSettingsImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openReset();
            }
        });
    }



    public void openAccount(){
        billIntent = new Intent(this, AccountActivity.class);
        startActivity(billIntent);
    }

    private void openFood(){
        foodIntent = new Intent(this, FoodActivity.class);
        startActivity(foodIntent);
    }
    private void openReset(){
        foodIntent = new Intent(this, ResetActivity.class);
        startActivity(foodIntent);
    }

}
