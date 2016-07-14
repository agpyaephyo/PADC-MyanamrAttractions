package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;
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

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }
        return objInstance;
    }

    public boolean isUserLogin() {
        return loginUser != null;
    }

    public void register(String name, String email, String password, String dateOfBirth, String country) {
        dataAgent.register(name, email, password, dateOfBirth, country);
    }

    //Success Register
    public void onEventMainThread(UserEvent.SuccessRegistrationEvent event) {
        loginUser = event.getLoginUser();

        //TODO Persist login user object.
    }

    //Failed to Register
    public void onEventMainThread(UserEvent.FailedRegistrationEvent event) {
        //Do nothing on persistent layer.
    }
}
