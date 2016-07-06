package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

public class AttractionDetailActivity extends AppCompatActivity {

    private static final String IE_ATTRACTION_NAME = "IE_ATTRACTION_NAME";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.tv_attraction_desc)
    TextView tvAttractionDesc;

    @BindView(R.id.iv_attraction)
    ImageView ivAttraction;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private AttractionVO mAttraction;

    public static Intent newIntent(String attractionName) {
        Intent intent = new Intent(MyanmarAttractionsApp.getContext(), AttractionDetailActivity.class);
        intent.putExtra(IE_ATTRACTION_NAME, attractionName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(AttractionDetailActivity.this)
                        .setType("text/plain")
                        .setText(mAttraction.getTitle() + " - " + mAttraction.getImages()[0])
                        .getIntent(), getString(R.string.action_share)));
            }
        });

        String attractionName = getIntent().getStringExtra(IE_ATTRACTION_NAME);
        mAttraction = AttractionModel.getInstance().getAttractionByName(attractionName);

        if (mAttraction == null) {
            throw new RuntimeException("No attraction found with name : " + attractionName);
        } else {
            tvAttractionDesc.setText(mAttraction.getDesc() + "\n\n"
                    + mAttraction.getDesc());

            String imageUrl = MyanmarAttractionsConstants.IMAGE_ROOT_DIR + mAttraction.getImages()[0];

            Glide.with(ivAttraction.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.stock_photo_placeholder)
                    .error(R.drawable.stock_photo_placeholder)
                    .into(ivAttraction);

            collapsingToolbar.setTitle(attractionName);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Context context = MyanmarAttractionsApp.getContext();
            String transitionName = context.getResources().getString(R.string.attraction_list_detail_transition_name);
            ivAttraction.setTransitionName(transitionName);
        }
    }

}
