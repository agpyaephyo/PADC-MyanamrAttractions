package xyz.aungpyaephyo.padc.myanmarattractions.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A bound Service that instantiates the authenticator
 * when started.
 * Created by aung on 7/20/16.
 */
public class AttractionAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private AttractionAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new AttractionAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
