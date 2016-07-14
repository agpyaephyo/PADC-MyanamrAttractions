package xyz.aungpyaephyo.padc.myanmarattractions.data.responses;

import com.google.gson.annotations.SerializedName;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;

/**
 * Created by aung on 7/15/16.
 */
public class RegisterResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("login_user")
    private UserVO loginUser;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public UserVO getLoginUser() {
        return loginUser;
    }
}
