


package com.example.zerohungerlogin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;



public class myFirebaseMessaging extends FirebaseMessagingService {
    int Notificationcheck;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(Notificationcheck==1) {
            if (remoteMessage.getData().isEmpty()) {
                shownotification1(remoteMessage.getNotification().getTitle().toString(), remoteMessage.getNotification().getBody().toString());
            } else {
                showNotification(remoteMessage.getData());
            }
        }
    }

    private void showNotification(Map<String, String>data){
        String title = data.get("title").toString();
        String body = data.get("body").toString();

        NotificationManager notificationmanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NotificationchannelID = "example.zerohungerlogin.test";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NotificationchannelID,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Hey there");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationmanager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,NotificationchannelID);

        notificationbuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.facebook).setContentTitle(title).setContentText(body).setContentInfo("info");
        notificationmanager.notify(new Random().nextInt(),notificationbuilder.build());
    }
    private void shownotification1(String title,String body){
        NotificationManager notificationmanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NotificationchannelID = "example.zerohungerlogin.test";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NotificationchannelID,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Hey there");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationmanager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,NotificationchannelID);

        notificationbuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.facebook).setContentTitle(title).setContentText(body).setContentInfo("info");
        notificationmanager.notify(new Random().nextInt(),notificationbuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);

    }
}