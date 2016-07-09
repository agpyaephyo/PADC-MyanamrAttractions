package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.AttractionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.HttpUrlConnectionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OfflineDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OkHttpDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.JsonUtils;

/**
 * Created by aung on 7/6/16.
 */
public class AttractionModel {

    private static final String ATTRACTION_LIST = "myanmar_attractions.json";
    private static final String ATTRCTION_LIST_URL = "http://www.aungpyaephyo.xyz/myanmar_attractions/getAttractionsList.php";
    private static final String ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_TYPE_OFFLINE = 1;
    private static final int INIT_TYPE_HTTP_URL_CONNECTION = 2;
    private static final int INIT_TYPE_OK_HTTP = 3;
    private static final int INIT_TYPE_RETROFIT = 4;

    private static AttractionModel objInstance;

    private List<AttractionVO> mAttractionList;
    private AttractionDataAgent dataAgent;

    private AttractionModel() {
        mAttractionList = new ArrayList<>();
        initializeAttractions(INIT_TYPE_OK_HTTP);
    }

    public static AttractionModel getInstance() {
        if (objInstance == null) {
            objInstance = new AttractionModel();
        }
        return objInstance;
    }

    private void initializeAttractions(int initType) {
        switch (initType) {
            case INIT_TYPE_OFFLINE:
                dataAgent = new OfflineDataAgent();
                dataAgent.loadAttractions();
                break;
            case INIT_TYPE_HTTP_URL_CONNECTION:
                dataAgent = new HttpUrlConnectionDataAgent();
                dataAgent.loadAttractions();
                break;
            case INIT_TYPE_OK_HTTP:
                dataAgent = new OkHttpDataAgent();
                dataAgent.loadAttractions();
                break;
        }
    }

    public List<AttractionVO> getAttractionList() {
        return mAttractionList;
    }

    public AttractionVO getAttractionByName(String attractionName) {
        for (AttractionVO attraction : mAttractionList) {
            if (attraction.getTitle().equals(attractionName))
                return attraction;
        }

        return null;
    }

    public void notifyAttractionsLoaded(List<AttractionVO> attractionList) {
        //Notify that the data is ready.
        mAttractionList = attractionList;
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(MyanmarAttractionsApp.getContext()).sendBroadcast(intent);
    }

    public void notifyErrorInLoadingAttractions(String message) {

    }
}
