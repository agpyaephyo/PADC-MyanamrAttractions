package xyz.aungpyaephyo.padc.myanmarattractions.controllers;

import org.json.JSONObject;

/**
 * Created by aung on 7/15/16.
 */
public interface UserSessionController extends SocialMediaController {
    void onRegister(String name, String email, String password, String dateOfBirth, String country);

    void onLogin(String email, String password);

    void onLoginWithFacebook(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl);
}

