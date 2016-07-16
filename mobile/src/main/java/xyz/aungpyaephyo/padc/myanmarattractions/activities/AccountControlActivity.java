package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserSessionController;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.UserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.dialogs.SharedDialog;
import xyz.aungpyaephyo.padc.myanmarattractions.events.UserEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.LoginFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.RegisterFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.ScreenUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.SecurityUtils;

/**
 * Created by aung on 7/15/16.
 */
public class AccountControlActivity extends AppCompatActivity
        implements UserSessionController {

    public static final int NAVIGATE_TO_REGISTER = 1;
    public static final int NAVIGATE_TO_LOGIN = 2;

    public static final int RC_ACCOUNT_CONTROL_REGISTER = 100;
    public static final int RC_ACCOUNT_CONTROL_LOGIN = 100;

    private static final String IE_NAVIGATE_TO = "IE_NAVIGATE_TO";

    public static final String IR_IS_REGISTER_SUCCESS = "IR_IS_REGISTER_SUCCESS";
    public static final String IR_IS_LOGIN_SUCCESS = "IR_IS_LOGIN_SUCCESS";

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    private int mNavigateTo;
    private ProgressDialog mProgressDialog;

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
            switch (mNavigateTo) {
                case NAVIGATE_TO_REGISTER:
                    fragment = RegisterFragment.newInstance();
                    break;
                case NAVIGATE_TO_LOGIN:
                    fragment = LoginFragment.newInstance();
                    break;
                default:
                    throw new RuntimeException("Unsupported Account Control Type : " + mNavigateTo);
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    @Override
    public void onRegister(String name, String email, String password, String dateOfBirth, String country) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }

        mProgressDialog.setMessage("Registering. Please wait.");
        mProgressDialog.show();

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().register(name, email, password, dateOfBirth, country);
    }

    @Override
    public void onLogin(String email, String password) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }

        mProgressDialog.setMessage("Logging In. Please wait.");
        mProgressDialog.show();

        password = SecurityUtils.encryptMD5(password);
        UserModel.getInstance().login(email, password);
    }

    //Success Register
    public void onEventMainThread(UserEvent.SuccessRegistrationEvent event) {
        Intent returningIntent = new Intent();
        returningIntent.putExtra(IR_IS_REGISTER_SUCCESS, true);
        setResult(Activity.RESULT_OK, returningIntent);
        finish();

        if(mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    //Failed to Register
    public void onEventMainThread(UserEvent.FailedRegistrationEvent event) {
        SharedDialog.promptMsgWithTheme(this, event.getMessage());
    }

    //Success Login
    public void onEventMainThread(UserEvent.SuccessLoginEvent event) {
        Intent returningIntent = new Intent();
        returningIntent.putExtra(IR_IS_LOGIN_SUCCESS, true);
        setResult(Activity.RESULT_OK, returningIntent);
        finish();

        if(mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    //Failed to Login
    public void onEventMainThread(UserEvent.FailedLoginEvent event) {
        SharedDialog.promptMsgWithTheme(this, event.getMessage());
    }
}
