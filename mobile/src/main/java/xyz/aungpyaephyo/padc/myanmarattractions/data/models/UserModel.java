package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import java.util.Date;

import de.greenrobot.event.EventBus;
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

    public void logout() {
        loginUser.clearData();
        loginUser = null;

        DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
        EventBus.getDefault().postSticky(event);
    }

    public void login(String email, String password) {
        dataAgent.login(email, password);
    }

    //Success Register
    public void onEventMainThread(UserEvent.SuccessRegistrationEvent event) {
        loginUser = event.getLoginUser();
        loginUser.setRegisteredDate(new Date());

        //Persist login user object.
        loginUser.saveLoginUser();
    }

    //Failed to Register
    public void onEventMainThread(UserEvent.FailedRegistrationEvent event) {
        //Do nothing on persistent layer.
    }

    //Success Login
    public void onEventMainThread(UserEvent.SuccessLoginEvent event) {
        loginUser = event.getLoginUser();

        //Persist login user object.
        loginUser.saveLoginUser();
    }

    //Failed to Login
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
