package xyz.aungpyaephyo.padc.myanmarattractions;

import android.app.Application;
import android.content.Context;

/**
 * Created by aung on 7/6/16.
 */
public class MyanmarAttractionsApp extends Application {

    public static final String TAG = "MyanmarAttractionsApp";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
