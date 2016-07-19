package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/19/16.
 */
public class AttractionService extends IntentService {

    private static final String IE_TIMESTAMP = "IE_TIMESTAMP";

    public static Intent newIntent(String timeStamp) {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AttractionService.class);
        intent.putExtra(IE_TIMESTAMP, timeStamp);
        return intent;
    }

    public AttractionService() {
        super(AttractionService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String timeStamp = intent.getStringExtra(IE_TIMESTAMP);
        Log.d(MyanmarAttractionsApp.TAG, "started AttractionService : "+timeStamp);
    }
}
