package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserSessionController;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.FacebookUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.views.PasswordVisibilityListener;

/**
 * Created by aung on 7/15/16.
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.lbl_recover_password)
    TextView lblRecoverPassword;

    @BindView(R.id.lbl_navigate_to_register)
    TextView lblNavigateToRegister;

    @BindView(R.id.lbl_login_title)
    TextView lblLoginTitle;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    private UserSessionController mController;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = (UserSessionController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        lblRecoverPassword.setText(Html.fromHtml(getString(R.string.lbl_recover_password)));
        lblNavigateToRegister.setText(Html.fromHtml(getString(R.string.lbl_navigate_to_register)));

        lblLoginTitle.setText(Html.fromHtml(getString(R.string.lbl_login_title)));

        etPassword.setOnTouchListener(new PasswordVisibilityListener());

        return rootView;
    }

    @OnClick(R.id.btn_login)
    public void onTapLogin(Button btnLogin) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                etEmail.setError(getString(R.string.error_missing_email_login));
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError(getString(R.string.error_missing_password_login));
            }
        } else if (!CommonUtils.isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_email_is_not_valid));
        } else {
            mController.onLogin(email, password);
        }
    }

    @OnClick(R.id.iv_login_with_facebook)
    public void onTapLoginWithFacebook(View view) {
        if (AccessToken.getCurrentAccessToken() == null) {
            //Haven't login
            Toast.makeText(getContext(), "Logging In ...", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(FacebookUtils.FACEBOOK_LOGIN_PERMISSIONS));
        } else {
            //Logout - just to test it.
            Toast.makeText(getContext(), "Logging Out ...", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    protected void onRetrieveFacebookInfo(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl) {
        super.onRetrieveFacebookInfo(facebookLoginUser, imageUrl, coverImageUrl);
        mController.onLoginWithFacebook(facebookLoginUser, imageUrl, coverImageUrl);
    }

    @OnClick(R.id.iv_login_with_google)
    public void onTapLoginWithGoogle(View view) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_LOGIN_WITH_GOOGLE);
    }

    @Override
    protected void onRetrieveGoogleInfo(GoogleSignInAccount signInAccount, Person registeringUser) {
        super.onRetrieveGoogleInfo(signInAccount, registeringUser);
        mController.onLoginWithGoogle(signInAccount, registeringUser);
    }
}
