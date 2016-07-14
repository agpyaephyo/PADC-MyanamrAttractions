package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.LoginFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.RegisterFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.ScreenUtils;

/**
 * Created by aung on 7/15/16.
 */
public class AccountControlActivity extends AppCompatActivity {

    public static final int NAVIGATE_TO_REGISTER = 1;
    public static final int NAVIGATE_TO_LOGIN = 2;

    private static final String IE_NAVIGATE_TO = "IE_NAVIGATE_TO";

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    private int mNavigateTo;

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

        mNavigateTo = getIntent().getIntExtra(IE_NAVIGATE_TO, NAVIGATE_TO_REGISTER);

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
}
