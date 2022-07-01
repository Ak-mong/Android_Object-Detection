package com.flash21.For_wooSung.Common;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.flash21.For_wooSung.R;
import com.flash21.For_wooSung.Splash.SplashAct;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessaging extends FirebaseMessagingService {
    int Notification_id = (int) System.currentTimeMillis();

    public FireBaseMessaging() {
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        sendNotification(message.getData().get("title"), message.getData().get("body"));
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @SuppressLint("InvalidWakeLockTag")
    private void sendNotification(String title, String body) {

        Intent intent = new Intent(this, SplashAct.class);

        String channelId = getString(R.string.app_name);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Notification_id /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT );

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("알림이 도착했습니다.")
                .setContentTitle(title)   // 앱이 켜져있는 상태에서 받는 알림 메세지
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);
        wakelock.release();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(Notification_id /* ID of notification */, notificationBuilder.build());
    }
}