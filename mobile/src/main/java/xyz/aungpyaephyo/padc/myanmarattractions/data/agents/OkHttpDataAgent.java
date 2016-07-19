package xyz.aungpyaephyo.padc.myanmarattractions.data.agents;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by aung on 7/9/16.
 */
public class OkHttpDataAgent implements AttractionDataAgent {

    private static OkHttpDataAgent objInstance;

    private OkHttpClient mHttpClient;

    private OkHttpDataAgent() {
        mHttpClient = new OkHttpClient.Builder() //1.
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new OkHttpDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadAttractions() {
        new AsyncTask<Void, Void, List<AttractionVO>>() {

            @Override
            protected List<AttractionVO> doInBackground(Void... voids) {
                RequestBody formBody = new FormBody.Builder() //2.
                        .add(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN, MyanmarAttractionsConstants.ACCESS_TOKEN)
                        .build();

                Request request = new Request.Builder() //3
                        .url(MyanmarAttractionsConstants.ATTRACTION_BASE_URL + MyanmarAttractionsConstants.API_GET_ATTRACTION_LIST)
                        .post(formBody)
                        .build();

                try {
                    Response response = mHttpClient.newCall(request).execute(); //4.
                    if (response.isSuccessful()) {
                        String responseString = response.body().string();
                        AttractionListResponse responseAttractionList = CommonInstances.getGsonInstance().fromJson(responseString, AttractionListResponse.class);
                        List<AttractionVO> attractionList = responseAttractionList.getAttractionList();
                        return attractionList;
                    } else {
                        AttractionModel.getInstance().notifyErrorInLoadingAttractions(response.message());
                    }
                } catch (IOException e) {
                    Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
                    AttractionModel.getInstance().notifyErrorInLoadingAttractions(e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<AttractionVO> attractionList) {
                super.onPostExecute(attractionList);
                if (attractionList != null && attractionList.size() > 0) {
                    AttractionModel.getInstance().notifyAttractionsLoaded(attractionList);
                }
            }
        }.execute();
    }

    @Override
    public void register(String name, String email, String password, String dateOfBirth, String countryOfOrigin) {

    }

    @Override
    public void login(String email, String password) {

    }
}
