package xyz.aungpyaephyo.padc.myanmarattractions.data.agents;

/**
 * Created by aung on 7/9/16.
 */
public interface AttractionDataAgent {
    void loadAttractions();
    void register(String name, String email, String password, String dateOfBirth, String countryOfOrigin);
    void login(String email, String password);
}
