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

    private AttractionModel() {
        mAttractionList = new ArrayList<>();
        initializeAttractions(INIT_TYPE_HTTP_URL_CONNECTION);
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
                initializeAttractionsOffline();
                break;
            case INIT_TYPE_HTTP_URL_CONNECTION:
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        initializeAttractionsHttpUrlConnection();
                        return null;
                    }
                }.execute();
                break;
        }
    }

    private List<AttractionVO> initializeAttractionsOffline() {
        mAttractionList = new ArrayList<>();

        try {
            String attractions = JsonUtils.getInstance().loadDummyData(ATTRACTION_LIST);
            Type listType = new TypeToken<List<AttractionVO>>() {
            }.getType();
            mAttractionList = CommonInstances.getGsonInstance().fromJson(attractions, listType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAttractionList;
    }

    private void initializeAttractionsHttpUrlConnection() {
        URL url;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(ATTRCTION_LIST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP POST here
            connection.setRequestMethod("POST");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            //put the request parameter into NameValuePair list.
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));

            //write the parameters from NameValuePair list into connection obj.
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            String responseString = stringBuilder.toString();
            AttractionListResponse response = CommonInstances.getGsonInstance().fromJson(responseString, AttractionListResponse.class);
            mAttractionList = response.getAttractionList();

            if(mAttractionList != null || mAttractionList.size() > 0) {
                //TODO Notify that the data is ready.
                Intent intent = new Intent(BROADCAST_DATA_LOADED);
                intent.putExtra("key-for-extra", "extra-in-broadcast");
                LocalBroadcastManager.getInstance(MyanmarAttractionsApp.getContext()).sendBroadcast(intent);
            }

        } catch (Exception e) {
            Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
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
}
