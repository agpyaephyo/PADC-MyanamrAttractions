package xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.LoginResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.RegisterResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by aung on 7/9/16.
 */
public interface AttractionApi {

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_GET_ATTRACTION_LIST)
    Call<AttractionListResponse> loadAttractions(
            @Field(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN) String param);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_REGISTER)
    Call<RegisterResponse> register(
            @Field(MyanmarAttractionsConstants.PARAM_NAME) String name,
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password,
            @Field(MyanmarAttractionsConstants.PARAM_DATE_OF_BIRTH) String dateOfBirth,
            @Field(MyanmarAttractionsConstants.PARAM_COUNTRY_OF_ORIGIN) String countryOfOrigin);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_REGISTER_WITH_FACEBOOK)
    Call<RegisterResponse> registerWithFacebook(
            @Field(MyanmarAttractionsConstants.PARAM_FACEBOOK_ID) String facebookId,
            @Field(MyanmarAttractionsConstants.PARAM_PROFILE_IMAGE) String profileImage,
            @Field(MyanmarAttractionsConstants.PARAM_COVER_IMAGE) String coverImage,
            @Field(MyanmarAttractionsConstants.PARAM_NAME) String name,
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password,
            @Field(MyanmarAttractionsConstants.PARAM_DATE_OF_BIRTH) String dateOfBirth,
            @Field(MyanmarAttractionsConstants.PARAM_COUNTRY_OF_ORIGIN) String countryOfOrigin);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_REGISTER_WITH_GOOGLE)
    Call<RegisterResponse> registerWithGoogle(
            @Field(MyanmarAttractionsConstants.PARAM_GOOGLE_ID) String facebookId,
            @Field(MyanmarAttractionsConstants.PARAM_PROFILE_IMAGE) String profileImage,
            @Field(MyanmarAttractionsConstants.PARAM_COVER_IMAGE) String coverImage,
            @Field(MyanmarAttractionsConstants.PARAM_NAME) String name,
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password,
            @Field(MyanmarAttractionsConstants.PARAM_DATE_OF_BIRTH) String dateOfBirth,
            @Field(MyanmarAttractionsConstants.PARAM_COUNTRY_OF_ORIGIN) String countryOfOrigin);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_LOGIN)
    Call<LoginResponse> login(
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_LOGIN_WITH_FACEBOOK)
    Call<LoginResponse> loginWithFacebook(
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_FACEBOOK_ID) String facebookId,
            @Field(MyanmarAttractionsConstants.PARAM_PROFILE_IMAGE) String profileImage,
            @Field(MyanmarAttractionsConstants.PARAM_COVER_IMAGE) String coverImage);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_LOGIN_WITH_GOOGLE)
    Call<LoginResponse> loginWithGoogle(
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_GOOGLE_ID) String googleId,
            @Field(MyanmarAttractionsConstants.PARAM_PROFILE_IMAGE) String profileImage,
            @Field(MyanmarAttractionsConstants.PARAM_COVER_IMAGE) String coverImage);
}
