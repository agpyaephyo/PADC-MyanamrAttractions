package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserController;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.UserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.dialogs.SharedDialog;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.AttractionListFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MMFontUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;
import xyz.aungpyaephyo.padc.myanmarattractions.views.pods.ViewPodAccountControl;

public class HomeActivity extends AppCompatActivity
        implements AttractionViewHolder.ControllerAttractionItem,
        UserController {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private ViewPodAccountControl vpAccountControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_attraction_launcher_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Menu leftMenu = navigationView.getMenu();
        MMFontUtils.applyMMFontToMenu(leftMenu);

        vpAccountControl = (ViewPodAccountControl) navigationView.getHeaderView(0);
        vpAccountControl.setUserController(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, AttractionListFragment.newInstance())
                    .commit();
        }

        UserModel.getInstance().init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AccountControlActivity.RC_ACCOUNT_CONTROL_REGISTER) {
            if (resultCode == RESULT_OK) {
                boolean isRegisterSuccess = data.getBooleanExtra(AccountControlActivity.IR_IS_REGISTER_SUCCESS, false);
                if (isRegisterSuccess) {
                    SharedDialog.promptMsgWithTheme(this, getString(R.string.msg_welcome_new_user));

                    DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
                    EventBus.getDefault().postSticky(event);
                }
            }
        }
    }

    @Override
    public void onTapAttraction(AttractionVO attraction, ImageView ivAttraction) {
        Intent intent = AttractionDetailActivity.newIntent(attraction.getTitle());
        startActivity(intent);
        //overridePendingTransition(R.anim.enter, R.anim.exit);

        /*
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair(ivAttraction, getString(R.string.attraction_list_detail_transition_name)));
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        */
    }

    @Override
    public void onTapLogin() {

    }

    @Override
    public void onTapRegister() {
        Intent intent = AccountControlActivity.newIntent(AccountControlActivity.NAVIGATE_TO_REGISTER);
        startActivityForResult(intent, AccountControlActivity.RC_ACCOUNT_CONTROL_REGISTER);
    }

    @Override
    public void onTapLogout() {
        SharedDialog.confirmYesNoWithTheme(this, getString(R.string.msg_confirm_logout), new SharedDialog.YesNoConfirmDelegate() {
            @Override
            public void onConfirmYes() {
                UserModel.getInstance().logout();
            }

            @Override
            public void onConfirmNo() {

            }
        });
    }
}
