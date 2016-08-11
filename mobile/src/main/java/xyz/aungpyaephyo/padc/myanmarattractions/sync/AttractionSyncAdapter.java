package xyz.aungpyaephyo.padc.myanmarattractions.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;

/**
 * Created by aung on 7/20/16.
 */
public class AttractionSyncAdapter extends AbstractThreadedSyncAdapter {

    //private static final int SYNC_INTERVAL = 60 * 180; //3 hour interval.
    private static final int SYNC_INTERVAL = 3;
    private static final int SYNC_FLEXTIME = 1;

    public AttractionSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public AttractionSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d(MyanmarAttractionsApp.TAG, "Performing Sync.");
        AttractionModel.getInstance().loadAttractions();
    }

    /**
     * Helper method to get fake account to be used with SyncAdapter.
     *
     * @param context
     * @return
     */
    public static Account getSyncAccount(Context context) {
        //Get an instance of Android Account Manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        //Create the account type & default account
        final String ACCOUNT_TYPE = context.getString(R.string.sync_account_type);
        final String ACCOUNT_NAME = "dummy_account";

        Account newAccount = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);

        if (!accountManager.addAccountExplicitly(newAccount, null, null)) {
            //maybe teh account is already exists. return null.
        }

        return newAccount;
    }

    /**
     * To sync immediately & test our SyncAdapter.
     *
     * @param context
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        onAccountCreated(getSyncAccount(context), context);
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        if (newAccount != null) {
            String authority = context.getString(R.string.content_authority);

            //Since we've created an account
            configurePeriodicSync(newAccount, authority, SYNC_INTERVAL, SYNC_FLEXTIME);

            //Without calling setSyncAutomatically, our periodic sync will not be enabled.
            ContentResolver.setSyncAutomatically(newAccount, authority, true);

            //Finally, let's do a sync to get things started.
            syncImmediately(context);
        }
    }

    /**
     * Taking advantage of flexible time to do inexact repeating alarm.
     * @param account
     * @param authority
     * @param syncInterval
     * @param flexTime
     */
    private static void configurePeriodicSync(Account account, String authority, int syncInterval, int flexTime) {
        if (account != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //taking advantage of flexible time to do in-exact repeating alarm.
                SyncRequest request = new SyncRequest.Builder()
                        .syncPeriodic(syncInterval, flexTime)
                        .setSyncAdapter(account, authority)
                        .setExtras(new Bundle())
                        .build();

                ContentResolver.requestSync(request);
            } else {
                ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
            }
        }
    }
}
