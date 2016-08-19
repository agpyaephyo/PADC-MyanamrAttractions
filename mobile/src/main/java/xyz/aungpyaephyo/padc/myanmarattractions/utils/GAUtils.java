package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * Created by aung on 8/19/16.
 */
public class GAUtils {

    //Screen Names
    public static final String SCREEN_ATTRACTION_LIST_RECYCLER_VIEW = "Attraction List - Recycler View";
    public static final String SCREEN_ATTRACTION_LIST_LIST_VIEW = "Attraction List - List View";
    public static final String SCREEN_ATTRACTION_LIST_GRID_VIEW = "Attraction List - Grid View";
    public static final String SCREEN_ATTRACTION_LIST_TAB_LAYOUT = "Attraction List - Tab Layout";
    public static final String SCREEN_NOTIFICATIONS = "Notifications";
    public static final String SCREEN_TOUROPIA = "Touropia";
    public static final String SCREEN_ATTRACTION_DETAILS = "Attraction Details";
    public static final String SCREEN_USER_PROFILE = "User Profile";
    public static final String SCREEN_REGISTRATION = "Registration";
    public static final String SCREEN_LOGIN = "Login";

    //Event Categories
    //public static final String CATEGORY_PRIMARY_ACTIONS = "Primary Actions";
    //public static final String CATEGORY_SECONDARY_ACTIONS = "Secondary Actions";
    //public static final String CATEGORY_CONVERSION_ACTIONS = "Conversion Actions";
    public static final String CATEGORY_APP_ACTIONS = "App Actions";
    public static final String CATEGORY_USER_ACCOUNT_ACTIONS = "User Account Actions";

    //App Actions
    public static final String ACTION_TAP_SEARCH = "Tap Search";
    public static final String ACTION_TAP_SETTINGS = "Tap Settings";
    public static final String ACTION_TAP_ATTRACTION = "Tap Attraction";
    public static final String ACTION_TAP_SHARE = "Tap Share";
    public static final String ACTION_SHOW_ATTRACTION_ON_MAP = "Show Attraction on Map";
    public static final String ACTION_TAP_BOOK_ATTRACTION = "Tap Book Attraction";
    public static final String ACTION_BOOK_ATTRACTION_BY_EMAIL = "Book Attraction by Email";
    public static final String ACTION_BOOK_ATTRACTION_OVER_PHONE = "Book Attraction over Phone";
    public static final String ACTION_SWIPE_IMAGE_VIEW_PAGER = "Swipe Image View Pager";

    //User Account Actions
    public static final String ACTION_TAP_LOGIN = "Tap Login";
    public static final String ACTION_TAP_REGISTER = "Tap Register";
    public static final String ACTION_TAP_LOGOUT = "Tap Logout";
    public static final String ACTION_REGISTER = "Register";
    public static final String ACTION_LOGIN = "Login";
    public static final String ACTION_LOGOUT = "Logout";

    private static GAUtils objInstance;

    private Tracker mTracker;

    private GAUtils() {
        initTracking();
    }

    public static GAUtils getInstance() {
        if (objInstance == null) {
            objInstance = new GAUtils();
        }

        return objInstance;
    }

    synchronized private void initTracking() {
        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(MyanmarAttractionsApp.getContext());
            mTracker = ga.newTracker(R.xml.ga_config);
            ga.enableAutoActivityReports(MyanmarAttractionsApp.INSTANCE);
        }
    }

    public void sendScreenHit(String screenName) {
        if (AppConfig.isEnableGATracking()) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public void sendAppAction(String action) {
        sendEventHit(CATEGORY_APP_ACTIONS, action);
    }

    public void sendAppAction(String action, String label) {
        sendEventHit(CATEGORY_APP_ACTIONS, action, label);
    }

    public void sendUserAccountAction(String action) {
        sendEventHit(CATEGORY_USER_ACCOUNT_ACTIONS, action);
    }

    public void sendUserAccountAction(String action, String label) {
        sendEventHit(CATEGORY_USER_ACCOUNT_ACTIONS, action, label);
    }

    private void sendEventHit(String category, String action, String label) {
        if (AppConfig.isEnableGATracking()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .build());
        }
    }

    private void sendEventHit(String category, String action) {
        if (AppConfig.isEnableGATracking()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .build());
        }
    }
}
