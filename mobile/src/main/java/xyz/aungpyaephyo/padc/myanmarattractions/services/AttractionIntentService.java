package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/19/16.
 */
public class AttractionIntentService extends IntentService {

    private static final String IE_TIMESTAMP = "IE_TIMESTAMP";

    public static Intent newIntent(String timeStamp) {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AttractionIntentService.class);
        intent.putExtra(IE_TIMESTAMP, timeStamp);
        return intent;
    }

    public AttractionIntentService() {
        super(AttractionIntentService.class.getSimpleName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MyanmarAttractionsApp.TAG, "started AttractionIntentService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String timeStamp = intent.getStringExtra(IE_TIMESTAMP);
        Log.d(MyanmarAttractionsApp.TAG, "executing heavy lifting of AttractionIntentService : " + timeStamp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MyanmarAttractionsApp.TAG, "destroyed AttractionIntentService");
    }
}
