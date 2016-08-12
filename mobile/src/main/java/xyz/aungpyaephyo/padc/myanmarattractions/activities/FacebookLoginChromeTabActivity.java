package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.FacebookActivity;

/**
 * Login from a custom tab redirects here. Pass the url on to FacebookActivity so it can return the
 * result.
 * Created by aung on 7/27/16.
 */
public class FacebookLoginChromeTabActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, FacebookActivity.class);
        intent.putExtra("url", getIntent().getDataString());

        // these flags will open FacebookActivity from the back stack as well as closing this
        // activity and the custom tab opened by FacebookActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }
}
