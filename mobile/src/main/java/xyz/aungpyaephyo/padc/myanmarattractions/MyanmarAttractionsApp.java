package xyz.aungpyaephyo.padc.myanmarattractions;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import org.w3c.dom.Attr;

import java.util.concurrent.ExecutionException;

import xyz.aungpyaephyo.padc.myanmarattractions.sync.AttractionSyncAdapter;

/**
 * Created by aung on 7/6/16.
 */
public class MyanmarAttractionsApp extends Application {

    public static final String TAG = "MyanmarAttractionsApp";

    private static Context context;
    private static Bitmap largeIcon;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //AttractionSyncAdapter.syncImmediately(getContext());
        AttractionSyncAdapter.initializeSyncAdapter(getContext());

        encodeAppIcon();
    }

    public static Context getContext() {
        return context;
    }

    public static Bitmap getAppIcon() {
        return largeIcon;
    }

    private void encodeAppIcon() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                //Encode bitmap for large notification icon
                Context context = MyanmarAttractionsApp.getContext();
                int largeIconWidth = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width);
                int largeIconHeight = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);

                try {
                    largeIcon = Glide.with(context)
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
}
