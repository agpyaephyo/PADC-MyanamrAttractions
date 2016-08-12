package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.UserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;
import xyz.aungpyaephyo.padc.myanmarattractions.dialogs.SharedDialog;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.ScreenUtils;

/**
 * Created by aung on 8/11/16.
 */
public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    public static Intent newIntent() {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), UserProfileActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ScreenUtils.setStatusbarColor(R.color.primary_dark, this);
        ButterKnife.bind(this, this);

        bindData(UserModel.getInstance().getLoginUser());
    }

    @OnClick(R.id.iv_change_profile)
    public void onTapChangeProfile(View view) {
        SharedDialog.confirmYesNoWithTheme(this, getString(R.string.confirm_change_profile_take_or_select),
                getString(R.string.change_profile_take_pic), getString(R.string.change_profile_select), new SharedDialog.YesNoConfirmDelegate() {
                    @Override
                    public void onConfirmYes() {
                        takePicture();
                    }

                    @Override
                    public void onConfirmNo() {

                    }
                });
    }

    private void bindData(UserVO loginUser) {
        tvName.setText(loginUser.getName());
        tvEmail.setText(loginUser.getEmail());
    }

    @Override
    public void onPictureTaken(Bitmap takenPicture) {
        super.onPictureTaken(takenPicture);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(ivProfile.getResources(), takenPicture);
        circularBitmapDrawable.setCircular(true);
        ivProfile.setImageDrawable(circularBitmapDrawable);
    }
}
