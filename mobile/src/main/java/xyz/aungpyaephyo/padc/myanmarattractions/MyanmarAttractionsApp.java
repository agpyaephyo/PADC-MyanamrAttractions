package xyz.aungpyaephyo.padc.myanmarattractions;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.concurrent.ExecutionException;

import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.sync.AttractionSyncAdapter;

/**
 * Created by aung on 7/6/16.
 */
public class MyanmarAttractionsApp extends Application {

    public static final String TAG = "MyanmarAttractionsApp";

    private static Context context;
    private static Bitmap appIcon;
    private static Bitmap attractionSight;

    public static MyanmarAttractionsApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        INSTANCE = this;

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        AttractionModel.getInstance().loadAttractions();
        //AttractionSyncAdapter.syncImmediately(getContext());
        AttractionSyncAdapter.initializeSyncAdapter(getContext());

        //encodeAppIcon();
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                encodeAttractionSight();
            }
        }, 500);
        */
    }

    public static Context getContext() {
        return context;
    }

    public static Bitmap getAppIcon() {
        return appIcon;
    }

    public static Bitmap getAttractionSight() {
        return attractionSight;
    }

    private void encodeAppIcon() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                //Encode bitmap for large notification icon
                Context context = MyanmarAttractionsApp.getContext();
                int largeIconWidth = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width);
                int largeIconHeight = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);

                try {
                    appIcon = Glide.with(context)
                            .load(R.drawable.ic_attraction_launcher_icon)
                            .asBitmap()
                            .into(largeIconWidth, largeIconHeight)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void encodeAttractionSight() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                //Encode bitmap for large notification icon
                Context context = MyanmarAttractionsApp.getContext();
                int largeIconWidth = context.getResources().getDimensionPixelSize(R.dimen.attraction_sight_width);
                int largeIconHeight = context.getResources().getDimensionPixelSize(R.dimen.attraction_sight_height);

                try {
                    attractionSight = Glide.with(context)
                            .load(AttractionModel.getInstance().getRandomAttractionImage())
                            .asBitmap()
                            .into(largeIconWidth, largeIconHeight)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /*
    This is to test out the conflict resolution process.
     */
}
