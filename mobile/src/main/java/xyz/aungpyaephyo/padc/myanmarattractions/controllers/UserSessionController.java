package xyz.aungpyaephyo.padc.myanmarattractions.controllers;

import xyz.aungpyaephyo.padc.myanmarattractions.activities.AccountControlActivity;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;

/**
 * Created by aung on 7/15/16.
 */
public interface UserSessionController extends SocialMediaController {
    void onRegister(String name, String email, String password, String dateOfBirth, String country);

    void onLogin(String email, String password);

    void connectToGoogle(AccountControlActivity.SocialMediaInfoDelegate socialMediaInfoDelegate);

    void connectToFacebook(AccountControlActivity.SocialMediaInfoDelegate socialMediaInfoDelegate);

    void onRegisterWithGoogle(UserVO registeringUser, String password);

    void onRegisterWithFacebook(UserVO registeringUser, String password);
}

