package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserSessionController;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.UserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;
import xyz.aungpyaephyo.padc.myanmarattractions.dialogs.SharedDialog;
import xyz.aungpyaephyo.padc.myanmarattractions.events.UserEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.LoginFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.RegisterFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.FacebookUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.GAUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.ScreenUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.SecurityUtils;

/**
 * Created by aung on 7/15/16.
 */
public class AccountControlActivity extends BaseActivity
        implements UserSessionController,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int NAVIGATE_TO_REGISTER = 1;
    public static final int NAVIGATE_TO_LOGIN = 2;

    public static final int RC_ACCOUNT_CONTROL_REGISTER = 100;
    public static final int RC_ACCOUNT_CONTROL_LOGIN = 100;

    private static final String IE_NAVIGATE_TO = "IE_NAVIGATE_TO";

    public static final String IR_IS_REGISTER_SUCCESS = "IR_IS_REGISTER_SUCCESS";
    public static final String IR_IS_LOGIN_SUCCESS = "IR_IS_LOGIN_SUCCESS";

    protected static final int RC_GOOGLE_SIGN_IN = 1236;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    protected GoogleApiClient mGoogleApiClient;

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    private int mNavigateTo;
    private SocialMediaInfoDelegate mSocialMediaInfoDelegate;

    public static Intent newIntent(int navigateTo) {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AccountControlActivity.class);
        intent.putExtra(IE_NAVIGATE_TO, navigateTo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_control);
        ScreenUtils.setStatusbarColor(R.color.primary_dark, this);
        ButterKnife.bind(this, this);

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
                        processFacebookInfo(loginResult);
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

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API)
                .build();

        mNavigateTo = getIntent().getIntExtra(IE_NAVIGATE_TO, NAVIGATE_TO_LOGIN);

        String randomBackgroundImgUrl = AttractionModel.getInstance().getRandomAttractionImage();
        if (randomBackgroundImgUrl != null) {
            Glide.with(ivBackground.getContext())
                    .load(randomBackgroundImgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.drawer_background)
                    .error(R.drawable.drawer_background)
                    .into(ivBackground);
        }

        if (savedInstanceState == null) {
            Fragment fragment;
            String fragmentTransitionTag = null;
            switch (mNavigateTo) {
                case NAVIGATE_TO_REGISTER:
                    fragment = RegisterFragment.newInstance();
                    fragmentTransitionTag = RegisterFragment.FRAGMENT_TRANSITION_TAG;
                    break;
                case NAVIGATE_TO_LOGIN:
                    fragment = LoginFragment.newInstance();
                    fragmentTransitionTag = LoginFragment.FRAGMENT_TRANSITION_TAG;
                    break;
                default:
                    throw new RuntimeException("Unsupported Account Control Type : " + mNavigateTo);
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment, fragmentTransitionTag)
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Person registeringUser = null;
            if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                registeringUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            }

            processGoogleInfo(result, registeringUser);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        mAccessTokenTracker.startTracking();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);

        mAccessTokenTracker.stopTracking();
    }

    @Override
    public void onRegister(String name, String email, String password, String dateOfBirth, String country) {
        GAUtils.getInstance().sendUserAccountAction(GAUtils.ACTION_REGISTER);

        showProgressDialog("Registering. Please wait.");

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().register(name, email, password, dateOfBirth, country);
    }

    @Override
    public void connectToGoogle(SocialMediaInfoDelegate socialMediaInfoDelegate) {
        mSocialMediaInfoDelegate = socialMediaInfoDelegate;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onRegisterWithGoogle(UserVO registeringUser, String password) {
        showProgressDialog("Registering with Google Info. Please wait.");

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().registerWithGoogle(registeringUser, password);
    }

    @Override
    public void connectToFacebook(SocialMediaInfoDelegate socialMediaInfoDelegate) {
        mSocialMediaInfoDelegate = socialMediaInfoDelegate;
        if (AccessToken.getCurrentAccessToken() == null) {
            //Haven't login
            Toast.makeText(getApplicationContext(), "Logging In ...", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(FacebookUtils.FACEBOOK_LOGIN_PERMISSIONS));
        } else {
            //Logout - just to test it.
            Toast.makeText(getApplicationContext(), "Logging Out ...", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void onRegisterWithFacebook(UserVO registeringUser, String password) {
        showProgressDialog("Registering with Facebook Info. Please wait.");

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().registerWithFacebook(registeringUser, password);
    }

    @Override
    public void onLogin(String email, String password) {
        GAUtils.getInstance().sendUserAccountAction(GAUtils.ACTION_LOGIN);

        showProgressDialog("Logging In. Please wait.");

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().login(email, password);
    }

    private void onLoginWithFacebook(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl) {
        showProgressDialog("Logging In with Facebook Info. Please wait.");
        UserModel.getInstance().loginWithFacebook(facebookLoginUser, imageUrl, coverImageUrl);
    }

    private void onLoginWithGoogle(GoogleSignInAccount signInAccount, Person registeringUser) {
        showProgressDialog("Logging In with Google Info. Please wait.");
        UserModel.getInstance().loginWithGoogle(signInAccount, registeringUser);
    }

    //Success Register
    public void onEventMainThread(UserEvent.SuccessRegistrationEvent event) {
        Intent returningIntent = new Intent();
        returningIntent.putExtra(IR_IS_REGISTER_SUCCESS, true);
        setResult(Activity.RESULT_OK, returningIntent);
        finish();

        dismissProgressDialog();
    }

    //Failed to Register
    public void onEventMainThread(UserEvent.FailedRegistrationEvent event) {
        dismissProgressDialog();
        SharedDialog.promptMsgWithTheme(this, event.getMessage());
    }

    //Success Login
    public void onEventMainThread(UserEvent.SuccessLoginEvent event) {
        Intent returningIntent = new Intent();
        returningIntent.putExtra(IR_IS_LOGIN_SUCCESS, true);
        setResult(Activity.RESULT_OK, returningIntent);
        finish();

        dismissProgressDialog();
    }

    //Failed to Login
    public void onEventMainThread(UserEvent.FailedLoginEvent event) {
        dismissProgressDialog();
        SharedDialog.promptMsgWithTheme(this, event.getMessage());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void processFacebookInfo(LoginResult loginResult) {
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
                                if (mSocialMediaInfoDelegate.isRegistering()) {
                                    RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                                            .findFragmentByTag(RegisterFragment.FRAGMENT_TRANSITION_TAG);
                                    registerFragment.onRetrieveFacebookInfo(facebookLoginUser, profilePhotoUrl, coverPhotoUrl);
                                } else {
                                    onLoginWithFacebook(facebookLoginUser, profilePhotoUrl, coverPhotoUrl);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void processGoogleInfo(GoogleSignInResult signInResult, Person registeringUser) {
        Log.d(MyanmarAttractionsApp.TAG, "Google handleSignInResult:" + signInResult.isSuccess());
        if (signInResult.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
            if (mSocialMediaInfoDelegate.isRegistering()) {
                RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                        .findFragmentByTag(RegisterFragment.FRAGMENT_TRANSITION_TAG);
                registerFragment.onRetrieveGoogleInfo(signInAccount, registeringUser);
            } else {
                onLoginWithGoogle(signInAccount, registeringUser);
            }
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    public interface SocialMediaInfoDelegate {
        boolean isRegistering();
    }
}
