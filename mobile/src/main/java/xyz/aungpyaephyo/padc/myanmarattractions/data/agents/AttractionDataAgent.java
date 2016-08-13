package xyz.aungpyaephyo.padc.myanmarattractions.data.agents;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;

/**
 * Created by aung on 7/9/16.
 */
public interface AttractionDataAgent {
    void loadAttractions();
    void register(String name, String email, String password, String dateOfBirth, String countryOfOrigin);
    void registerWithFacebook(UserVO registeringUser, String password);
    void login(String email, String password);
    void loginWithFacebook(UserVO loginUser);
}
