package xyz.aungpyaephyo.padc.myanmarattractions.data.vos;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.annotations.SerializedName;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/15/16.
 */
public class UserVO {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("date_of_birth")
    private String dateOfBirthText;

    @SerializedName("country_of_origin")
    private String countryOfOrigin;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getDateOfBirthText() {
        return dateOfBirthText;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
}
