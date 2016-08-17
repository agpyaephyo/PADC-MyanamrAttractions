package xyz.aungpyaephyo.padc.myanmarattractions.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract;

/**
 * Created by aung on 7/15/16.
 */
public class UserVO {

    private static final SimpleDateFormat sdfRegisteredDate = new SimpleDateFormat("yyyy-MM-dd");

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

    @SerializedName("registered_date")
    private String registeredDateText; //YYYY-MM-dd

    private String lastUsedDate;

    private String profilePicture;

    private String coverPicture;

    private String facebookId;

    private String googleId;

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

    public Date getRegisteredDate() {
        try {
            return sdfRegisteredDate.parse(registeredDateText);
        } catch (ParseException e) {
            Log.d(MyanmarAttractionsApp.TAG, e.getMessage());
        }

        return null;
    }

    public void setRegisteredDate(Date date) {
        registeredDateText = sdfRegisteredDate.format(date);
    }

    public void setRegisteredDate(String date) {
        registeredDateText = date;
    }

    public String getLastUsedDate() {
        return lastUsedDate;
    }

    public void setLastUsedDate(String lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setDateOfBirthText(String dateOfBirthText) {
        this.dateOfBirthText = dateOfBirthText;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void saveLoginUser() {
        ContentValues cv = parseToContentValues();
        Context context = MyanmarAttractionsApp.getContext();
        Uri insertedUri = context.getContentResolver().insert(AttractionsContract.LoginUserEntry.CONTENT_URI, cv);

        Log.d(MyanmarAttractionsApp.TAG, "Login User Inserted Uri : " + insertedUri);
    }

    public static UserVO loadLoginUser() {
        Context context = MyanmarAttractionsApp.getContext();
        Cursor loginUserCursor = context.getContentResolver().query(AttractionsContract.LoginUserEntry.CONTENT_URI,
                null, null, null, null);

        if (loginUserCursor != null && loginUserCursor.moveToFirst()) {
            return UserVO.parseFromCursor(loginUserCursor);
        }

        return null;
    }

    private static UserVO parseFromCursor(Cursor cursor) {
        UserVO loginUser = new UserVO();
        loginUser.setName(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_NAME)));
        loginUser.setEmail(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_EMAIL)));
        loginUser.setAccessToken(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_ACCESS_TOKEN)));
        loginUser.setDateOfBirthText(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_DATE_OF_BIRTH)));
        loginUser.setCountryOfOrigin(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_COUNTRY)));
        loginUser.setRegisteredDate(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_REGISTERED_DATE)));
        loginUser.setLastUsedDate(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_LAST_USED_DATE)));
        loginUser.setProfilePicture(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_PROFILE_PICTURE)));
        loginUser.setCoverPicture(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_COVER_PICTURE)));
        loginUser.setFacebookId(cursor.getString(cursor.getColumnIndex(AttractionsContract.LoginUserEntry.COLUMN_FACEBOOK_ID)));
        return loginUser;
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_NAME, name);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_EMAIL, email);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_ACCESS_TOKEN, accessToken);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_DATE_OF_BIRTH, dateOfBirthText);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_COUNTRY, countryOfOrigin);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_REGISTERED_DATE, registeredDateText);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_LAST_USED_DATE, lastUsedDate);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_PROFILE_PICTURE, profilePicture);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_COVER_PICTURE, coverPicture);
        cv.put(AttractionsContract.LoginUserEntry.COLUMN_FACEBOOK_ID, facebookId);
        return cv;
    }

    public void clearData() {
        Context context = MyanmarAttractionsApp.getContext();
        int deletedRowCount = context.getContentResolver().delete(AttractionsContract.LoginUserEntry.CONTENT_URI, null, null);

        Log.d(MyanmarAttractionsApp.TAG, "User clearData - deletedRowCount : " + deletedRowCount);
    }

    public static UserVO initFromFacebookInfo(JSONObject facebookInfo, String profilePic, String coverPic) {
        UserVO loginUser = new UserVO();
        try {
            if (facebookInfo.has("id")) {
                loginUser.setFacebookId(facebookInfo.getString("id"));
            }

            if (facebookInfo.has("name")) {
                loginUser.setName(facebookInfo.getString("name"));
            }

            if (facebookInfo.has("email")) {
                loginUser.setEmail(facebookInfo.getString("email"));
            }
        } catch (JSONException e) {
            Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
        }

        loginUser.setProfilePicture(profilePic);
        loginUser.setCoverPicture(coverPic);

        return loginUser;
    }

    public static UserVO initFromGoogleInfo(GoogleSignInAccount googleSignInAccount, Person registeringUser) {
        UserVO loginUser = new UserVO();
        loginUser.setGoogleId(googleSignInAccount.getId());
        loginUser.setName(googleSignInAccount.getDisplayName());
        loginUser.setEmail(googleSignInAccount.getEmail());

        Uri imageUri = googleSignInAccount.getPhotoUrl();
        if (imageUri != null) {
            loginUser.setProfilePicture(imageUri.toString());
        }

        if(registeringUser != null) {
            loginUser.setCoverPicture(registeringUser.getCover().getCoverPhoto().getUrl());
        }

        return loginUser;
    }
}
