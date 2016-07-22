package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.activities.HomeActivity;

/**
 * Created by aung on 7/22/16.
 */
public class NotificationUtils {

    private static final int GENERAL_NOTIFICATION_ID = 4200;


    public static void showNotification() {
        Context context = MyanmarAttractionsApp.getContext();

        //Notification Title
        String title = context.getString(R.string.home_screen_title);

        //Notification Text
        String text = context.getString(R.string.msg_notification);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.primary))
                .setSmallIcon(R.drawable.ic_search_24dp)
                .setLargeIcon(MyanmarAttractionsApp.getAppIcon())
                .setContentTitle(title)
                .setContentText(text);

        //Open the app when user tap on notification
        Intent resultIntent = new Intent(context, HomeActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(GENERAL_NOTIFICATION_ID, builder.build());
    }
}
