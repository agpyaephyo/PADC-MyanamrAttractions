package xyz.aungpyaephyo.padc.myanmarattractions.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/20/16.
 */
public class AttractionSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static AttractionSyncAdapter sAttractionSyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MyanmarAttractionsApp.TAG, "onCreate - AttractionSyncService");
        synchronized (sSyncAdapterLock) {
            if(sAttractionSyncAdapter == null) {
                sAttractionSyncAdapter = new AttractionSyncAdapter(getApplicationContext(), true); //auto-initialize -> true.
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sAttractionSyncAdapter.getSyncAdapterBinder();
    }
}
