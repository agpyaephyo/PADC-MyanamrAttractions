package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;

/**
 * Created by aung on 7/15/16.
 */
public class UserModel {

    private static UserModel objInstance;

    private UserVO loginUser;

    private UserModel() {

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
}
