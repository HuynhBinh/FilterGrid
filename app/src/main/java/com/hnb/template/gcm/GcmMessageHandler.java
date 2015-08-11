package com.hnb.template.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hnb.template.utils.StaticFunction;


public class GcmMessageHandler extends IntentService
{

//    String mes;
//    private Handler handler;

    public GcmMessageHandler()
    {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
//        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {


        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        String title = extras.getString("title");
        String subtitle = extras.getString("subtitle");

        StaticFunction.sendNotificationBigStyle(GcmMessageHandler.this, 1010, title, subtitle, title, "");


        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

//    public void showToast() {
//        handler.post(new Runnable() {
//            public void run() {
//                Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}
