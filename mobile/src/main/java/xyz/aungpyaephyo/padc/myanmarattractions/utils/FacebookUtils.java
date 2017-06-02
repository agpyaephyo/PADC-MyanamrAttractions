package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 12/17/15.
 */
public class FacebookUtils {

    public static final String[] FACEBOOK_LOGIN_PERMISSIONS = {"public_profile", "email"};

    private static FacebookUtils objInstance;

    private Context context;

    private FacebookUtils() {
        context = MyanmarAttractionsApp.getContext();
    }

    public static FacebookUtils getInstance() {
        if (objInstance == null) {
            objInstance = new FacebookUtils();
        }

        return objInstance;
    }

    public void requestFacebookLoginUser(final AccessToken accessToken, final FacebookGetLoginUserCallback callback) {
        if (NetworkUtils.isOnline(MyanmarAttractionsApp.getContext())) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            //UserModel.getInstance().initUserByFacebook(jsonObject);
                            callback.onSuccess(jsonObject);
                        }
                    });

            Bundle params = new Bundle();
            params.putString("fields", "id, name, email");
            request.setParameters(params);

            request.executeAsync();
        }
    }

    public void requestFacebookCoverPhoto(AccessToken accessToken, final FacebookGetPictureCallback callback) {
        if (NetworkUtils.isOnline(MyanmarAttractionsApp.getContext())) {
            Bundle params = new Bundle();
            params.putString("fields", "cover");
            GraphRequest request = new GraphRequest(
                    accessToken,
                    "me",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONObject coverResponse = response.getJSONObject().getJSONObject("cover");
                                String coverUrl = coverResponse.getString("source");
                                //UserModel.getInstance().addFacebookCoverUrl(coverUrl);
                                callback.onSuccess(coverUrl);

                            } catch (JSONException e) {
                                Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
                            }
                        }
                    }
            );
            request.executeAsync();
        }
    }

    public void requestFacebookProfilePhoto(AccessToken accessToken, final FacebookGetPictureCallback callback) {
        if (NetworkUtils.isOnline(MyanmarAttractionsApp.getContext())) {
            Bundle params = new Bundle();
            params.putString("redirect", "false");
            params.putString("type", "large");
            GraphRequest request = new GraphRequest(
                    accessToken,
                    "me/picture",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                String profilePhotoUrl = response.getJSONObject().getJSONObject("data").getString("url");
                                //UserModel.getInstance().addFacebookProfileUrl(profilePhotoUrl);
                                callback.onSuccess(profilePhotoUrl);

                            } catch (JSONException e) {
                                Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
                            }
                        }
                    }
            );
            request.executeAsync();
        }
    }

    public interface FacebookGetLoginUserCallback {
        void onSuccess(JSONObject facebookLoginUser);
    }

    public interface FacebookGetPictureCallback {
        void onSuccess(String imageUrl);
    }
}
