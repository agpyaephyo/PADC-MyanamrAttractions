package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.Date;

import org.greenrobot.eventbus.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.events.UserEvent;

/**
 * Created by aung on 7/15/16.
 */
public class UserModel extends BaseModel {

    private static UserModel objInstance;

    private UserVO loginUser;

    private UserModel() {
        super();
    }

    public void init() {
        loginUser = UserVO.loadLoginUser();

        if (loginUser != null) {
            DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
            EventBus.getDefault().postSticky(event);
        }
    }

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }
        return objInstance;
    }

    public boolean isUserLogin() {
        return loginUser != null;
    }

    public UserVO getLoginUser() {
        return loginUser;
    }

    public void register(String name, String email, String password, String dateOfBirth, String country) {
        dataAgent.register(name, email, password, dateOfBirth, country);
    }

    public void registerWithFacebook(UserVO registeringUser, String password) {
        loginUser = registeringUser;
        dataAgent.registerWithFacebook(registeringUser, password);
    }

    public void registerWithGoogle(UserVO registeringUser, String password) {
        loginUser = registeringUser;
        dataAgent.registerWithFacebook(registeringUser, password);
    }

    public void logout() {
        loginUser.clearData();
        loginUser = null;

        DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
        EventBus.getDefault().postSticky(event);
    }

    public void login(String email, String password) {
        dataAgent.login(email, password);
    }

    public void loginWithFacebook(JSONObject facebookLoginUser, String profilePic, String coverPic) {
        loginUser = UserVO.initFromFacebookInfo(facebookLoginUser, profilePic, coverPic);
        dataAgent.loginWithFacebook(loginUser);
    }

    public void loginWithGoogle(GoogleSignInAccount signInAccount, Person registeringUser) {
        loginUser = UserVO.initFromGoogleInfo(signInAccount, registeringUser);
        dataAgent.loginWithGoogle(loginUser);
    }

    //Success Register
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserEvent.SuccessRegistrationEvent event) {
        if (loginUser == null) {
            loginUser = event.getLoginUser();
        } else {
            loginUser.setAccessToken(event.getLoginUser().getAccessToken());
            loginUser.setDateOfBirthText(event.getLoginUser().getDateOfBirthText());
            loginUser.setCountryOfOrigin(event.getLoginUser().getCountryOfOrigin());
        }

        loginUser.setRegisteredDate(new Date());

        //Persist login user object.
        loginUser.saveLoginUser();
    }

    //Failed to Register
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserEvent.FailedRegistrationEvent event) {
        //Do nothing on persistent layer.
    }

    //Success Login
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserEvent.SuccessLoginEvent event) {
        if (loginUser == null) {
            loginUser = event.getLoginUser();
        } else {
            loginUser.setAccessToken(event.getLoginUser().getAccessToken());
            loginUser.setDateOfBirthText(event.getLoginUser().getDateOfBirthText());
            loginUser.setCountryOfOrigin(event.getLoginUser().getCountryOfOrigin());
            loginUser.setRegisteredDate(event.getLoginUser().getRegisteredDate());
        }

        //Persist login user object.
        loginUser.saveLoginUser();
    }

    //Failed to Login
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserEvent.FailedLoginEvent event) {
        //Do nothing on persistent layer.
    }

    public void saveProfilePicture(String localPath) {
        loginUser.setProfilePicture(localPath);
        loginUser.saveLoginUser();

        DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
        EventBus.getDefault().post(event);
    }
}
