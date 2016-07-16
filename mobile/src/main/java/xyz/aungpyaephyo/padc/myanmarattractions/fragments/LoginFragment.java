package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserSessionController;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonUtils;

/**
 * Created by aung on 7/15/16.
 */
public class LoginFragment extends Fragment {

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
}
