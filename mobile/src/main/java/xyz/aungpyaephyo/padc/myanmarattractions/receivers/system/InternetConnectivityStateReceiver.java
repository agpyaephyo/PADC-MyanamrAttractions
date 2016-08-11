package xyz.aungpyaephyo.padc.myanmarattractions.receivers.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import xyz.aungpyaephyo.padc.myanmarattractions.utils.NetworkUtils;

/**
 * Created by aung on 7/19/16.
 */
public class InternetConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isOnline(context)) {
            Toast.makeText(context, "Device is connected with Internet.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Device is disconnected with Internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
