package xyz.aungpyaephyo.padc.myanmarattractions.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

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

    private String registeredDateText; //YYYY-MM-dd
    private String lastUsedDate;

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

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private void setDateOfBirthText(String dateOfBirthText) {
        this.dateOfBirthText = dateOfBirthText;
    }

    private void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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
        return cv;
    }

    public void clearData() {
        Context context = MyanmarAttractionsApp.getContext();
        int deletedRowCount = context.getContentResolver().delete(AttractionsContract.LoginUserEntry.CONTENT_URI, null, null);

        Log.d(MyanmarAttractionsApp.TAG, "User clearData - deletedRowCount : " + deletedRowCount);
    }
}
