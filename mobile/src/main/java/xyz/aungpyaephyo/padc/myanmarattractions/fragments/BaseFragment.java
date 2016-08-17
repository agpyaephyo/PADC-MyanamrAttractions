package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.FacebookUtils;

/**
 * Created by aung on 8/11/16.
 */
public abstract class BaseFragment extends Fragment
        implements GoogleApiClient.OnConnectionFailedListener {

    protected static final int RC_LOGIN_WITH_GOOGLE = 1235;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    protected GoogleApiClient mGoogleApiClient;

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

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity() /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API)
                .build();
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

        if (requestCode == RC_LOGIN_WITH_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Person registeringUser = null;
            if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                registeringUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            }

            processGoogleLoginResult(result, registeringUser);
        }
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

    private void processGoogleLoginResult(GoogleSignInResult signInResult, Person registeringUser) {
        Log.d(MyanmarAttractionsApp.TAG, "Google handleSignInResult:" + signInResult.isSuccess());
        if (signInResult.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
            onRetrieveGoogleInfo(signInAccount, registeringUser);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    protected void onRetrieveFacebookInfo(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl) {

    }

    protected void onRetrieveGoogleInfo(GoogleSignInAccount signInAccount, Person registeringUser) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
