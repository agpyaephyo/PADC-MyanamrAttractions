package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 8/11/16.
 */
public class MyanmarAttractionsFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String GENERAL_ENGAGEMENT_TOPIC = "ma-general-engagement-topic";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(MyanmarAttractionsApp.TAG, "FCM InstanceId Token : "+token);

        FirebaseMessaging.getInstance()
                .subscribeToTopic(GENERAL_ENGAGEMENT_TOPIC);
    }
}
