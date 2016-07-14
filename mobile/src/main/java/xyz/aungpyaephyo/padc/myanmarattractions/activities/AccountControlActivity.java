package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;

/**
 * Created by aung on 7/15/16.
 */
public class AccountControlActivity extends AppCompatActivity {

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    public static Intent newIntent() {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AccountControlActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_control);
        ButterKnife.bind(this, this);

        String randomBackgroundImgUrl = AttractionModel.getInstance().getRandomAttractionImage();
        if (randomBackgroundImgUrl != null) {
            Glide.with(ivBackground.getContext())
                    .load(randomBackgroundImgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.drawer_background)
                    .error(R.drawable.drawer_background)
                    .into(ivBackground);
        }
    }
}
