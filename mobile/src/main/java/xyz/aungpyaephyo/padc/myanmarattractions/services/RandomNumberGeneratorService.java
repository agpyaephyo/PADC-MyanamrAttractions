package xyz.aungpyaephyo.padc.myanmarattractions.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/20/16.
 */
public class RandomNumberGeneratorService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    public static Intent newIntent() {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), RandomNumberGeneratorService.class);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public class LocalBinder extends Binder {

        public RandomNumberGeneratorService getService() {
            return RandomNumberGeneratorService.this;
        }
    }
}
