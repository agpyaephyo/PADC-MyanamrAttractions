package xyz.aungpyaephyo.padc.myanmarattractions.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.components.mmfont.MMFontUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserController;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.UserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.dialogs.SharedDialog;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.events.SearchButtonClickedEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.AttractionListFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.AttractionPagerFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.GridViewAttractionListFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.ListViewAttractionListFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.NotificationFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.fragments.TouropiaFragment;
import xyz.aungpyaephyo.padc.myanmarattractions.services.RandomNumberGeneratorService;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.GAUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;
import xyz.aungpyaephyo.padc.myanmarattractions.views.pods.ViewPodAccountControl;

public class HomeActivity extends BaseActivity
        implements AttractionViewHolder.ControllerAttractionItem,
        UserController,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.fab_search)
    FloatingActionButton fabSearch;

    private ViewPodAccountControl vpAccountControl;

    private RandomNumberGeneratorService mBindingService;
    private boolean isServiceBound = false;

    private ServiceConnection mBindingServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RandomNumberGeneratorService.LocalBinder localBinder = (RandomNumberGeneratorService.LocalBinder) iBinder;
            mBindingService = localBinder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_attraction_launcher_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Menu leftMenu = navigationView.getMenu();
        MMFontUtils.applyMMFontToMenu(leftMenu);
        navigationView.setNavigationItemSelectedListener(this);

        vpAccountControl = (ViewPodAccountControl) navigationView.getHeaderView(0);
        vpAccountControl.setUserController(this);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sorry. Search on Attractions is not being supported yet.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                GAUtils.getInstance().sendAppAction(GAUtils.ACTION_TAP_SEARCH);

                EventBus.getDefault().post(new SearchButtonClickedEvent(12345, 67890));
            }
        });

        if (savedInstanceState == null) {
            navigateToRecyclerView();
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
                GAUtils.getInstance().sendAppAction(GAUtils.ACTION_TAP_SETTINGS);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == AccountControlActivity.RC_ACCOUNT_CONTROL_REGISTER) {
                boolean isRegisterSuccess = data.getBooleanExtra(AccountControlActivity.IR_IS_REGISTER_SUCCESS, false);
                if (isRegisterSuccess) {
                    SharedDialog.promptMsgWithTheme(this, getString(R.string.msg_welcome_new_user));
                }
            } else if (requestCode == AccountControlActivity.RC_ACCOUNT_CONTROL_LOGIN) {
                boolean isLoginSuccess = data.getBooleanExtra(AccountControlActivity.IR_IS_LOGIN_SUCCESS, false);
                if (isLoginSuccess) {
                    SharedDialog.promptMsgWithTheme(this, getString(R.string.msg_welcome_returning_user));
                }
            }

            DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
            EventBus.getDefault().post(event);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = RandomNumberGeneratorService.newIntent();
        bindService(intent, mBindingServiceConnection, Context.BIND_AUTO_CREATE);

        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isServiceBound) {
            unbindService(mBindingServiceConnection);
            isServiceBound = false;
        }

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onTapAttraction(AttractionVO attraction, ImageView ivAttraction) {
        GAUtils.getInstance().sendAppAction(GAUtils.ACTION_TAP_ATTRACTION, attraction.getTitle());

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
        Intent intent = AccountControlActivity.newIntent(AccountControlActivity.NAVIGATE_TO_LOGIN);
        startActivityForResult(intent, AccountControlActivity.RC_ACCOUNT_CONTROL_LOGIN);
    }

    @Override
    public void onTapRegister() {
        Intent intent = AccountControlActivity.newIntent(AccountControlActivity.NAVIGATE_TO_REGISTER);
        startActivityForResult(intent, AccountControlActivity.RC_ACCOUNT_CONTROL_LOGIN);
    }

    @Override
    public void onTapLogout() {
        drawerLayout.closeDrawer(GravityCompat.START);
        SharedDialog.confirmYesNoWithTheme(this, getString(R.string.msg_confirm_logout), new SharedDialog.YesNoConfirmDelegate() {
            @Override
            public void onConfirmYes() {
                GAUtils.getInstance().sendUserAccountAction(GAUtils.ACTION_LOGOUT);

                LoginManager.getInstance().logOut();
                UserModel.getInstance().logout();
            }

            @Override
            public void onConfirmNo() {

            }
        });
    }

    @Override
    public void onNavigateUserProfile() {
        Intent intent = UserProfileActivity.newIntent();
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        fabSearch.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.myanmar_attractions_recycler_view:
                navigateToRecyclerView();
                return true;
            case R.id.myanmar_attractions_list_view:
                navigateToListView();
                return true;
            case R.id.myanmar_attractions_grid_view:
                navigateToGridView();
                return true;
            case R.id.myanmar_attractions_tab_layout:
                navigateToTabLayout();
                return true;
            case R.id.myanmar_attractions_notification:
                fabSearch.setVisibility(View.GONE);
                navigateToNotification();
                return true;
            case R.id.myanmar_attractions_touropia:
                fabSearch.setVisibility(View.GONE);
                navigateToTouropia();
                return true;
        }
        return false;
    }

    private void navigateToListView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, ListViewAttractionListFragment.newInstance())
                .commit();
    }

    private void navigateToRecyclerView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, AttractionListFragment.newInstance())
                .commit();
    }

    private void navigateToGridView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, GridViewAttractionListFragment.newInstance())
                .commit();
    }

    private void navigateToTabLayout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, AttractionPagerFragment.newInstance())
                .commit();
    }

    private void navigateToNotification() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, NotificationFragment.newInstance())
                .commit();
    }

    private void navigateToTouropia() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, TouropiaFragment.newInstance())
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedSearchButtonClickedEvent(SearchButtonClickedEvent event) {
        Log.d(MyanmarAttractionsApp.TAG, "onReceivedSearchButtonClickedEvent");
    }










}
