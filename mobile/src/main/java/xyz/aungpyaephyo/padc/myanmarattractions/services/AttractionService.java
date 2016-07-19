package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/20/16.
 */
public class AttractionService extends Service {

    private static final String IE_TIMESTAMP = "IE_TIMESTAMP";

    private Looper mServiceLooper;
    private AttractionServiceHandler mServiceHandler;
    private String mTimeStamp;

    public static Intent newIntent(String timestamp) {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AttractionService.class);
        intent.putExtra(IE_TIMESTAMP, timestamp);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Create a separate thread.
        HandlerThread thread = new HandlerThread("HT_AttractionService", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        //Get the HandlerThread's looper and use it for our handler.
        mServiceLooper = thread.getLooper();
        mServiceHandler = new AttractionServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MyanmarAttractionsApp.TAG, "started AttractionService");

        mTimeStamp = intent.getStringExtra(IE_TIMESTAMP);

        // For each request, send a message to start a job and deliver the startId
        // so that we know which request we're stopping when we finish the job.
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        //If we get killed, restart.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MyanmarAttractionsApp.TAG, "destroyed AttractionService");
    }

    private final class AttractionServiceHandler extends Handler {

        public AttractionServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(MyanmarAttractionsApp.TAG, "executing heavy lifting of AttractionService : " + mTimeStamp);
            stopSelf(msg.arg1);
        }
    }
}
