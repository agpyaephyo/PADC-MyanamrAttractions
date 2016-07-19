package xyz.aungpyaephyo.padc.myanmarattractions.controllers;

/**
 * Created by aung on 7/15/16.
 */
public interface UserSessionController extends BaseController {
    void onRegister(String name, String email, String password, String dateOfBirth, String country);
    void onLogin(String email, String password);
}

