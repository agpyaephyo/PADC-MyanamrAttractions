package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.FacebookUtils;

/**
 * Created by aung on 8/11/16.
 */
public abstract class BaseFragment extends Fragment {

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //Logout from Facebook.
                    Log.d(MyanmarAttractionsApp.TAG, "Logout from Facebook");
                }
            }
        };

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(MyanmarAttractionsApp.TAG, "Login with Facebook Account - onSuccess");
                        processFacebookLoginResult(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(MyanmarAttractionsApp.TAG, "Login with Facebook Account - onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(MyanmarAttractionsApp.TAG, "Login with Facebook Account - onError");
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAccessTokenTracker.startTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void processFacebookLoginResult(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();
        FacebookUtils.getInstance().requestFacebookLoginUser(accessToken, new FacebookUtils.FacebookGetLoginUserCallback() {
            @Override
            public void onSuccess(final JSONObject facebookLoginUser) {
                FacebookUtils.getInstance().requestFacebookProfilePhoto(accessToken, new FacebookUtils.FacebookGetPictureCallback() {
                    @Override
                    public void onSuccess(final String profilePhotoUrl) {
                        FacebookUtils.getInstance().requestFacebookCoverPhoto(accessToken, new FacebookUtils.FacebookGetPictureCallback() {
                            @Override
                            public void onSuccess(final String coverPhotoUrl) {
                                onRetrieveFacebookInfo(facebookLoginUser, profilePhotoUrl, coverPhotoUrl);
                            }
                        });
                    }
                });
            }
        });
    }

    protected void onRetrieveFacebookInfo(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl) {

    }
}
