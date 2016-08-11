package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.NotificationUtils;

/**
 * Created by aung on 8/11/16.
 */
public class MyanmarAttractionsFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(MyanmarAttractionsApp.TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(MyanmarAttractionsApp.TAG, "FCM Notification Message: " + remoteMessage.getNotification().getBody());
        Log.d(MyanmarAttractionsApp.TAG, "FCM Data Message: " + remoteMessage.getData());

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        NotificationUtils.showDynamicNotification(title, message);
    }
}
