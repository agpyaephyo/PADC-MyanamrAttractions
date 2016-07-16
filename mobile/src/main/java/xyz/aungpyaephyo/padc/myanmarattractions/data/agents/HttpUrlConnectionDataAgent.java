package xyz.aungpyaephyo.padc.myanmarattractions.data.agents;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by aung on 7/9/16.
 */
public class HttpUrlConnectionDataAgent implements AttractionDataAgent {

    private static HttpUrlConnectionDataAgent objInstance;

    private HttpUrlConnectionDataAgent() {

    }

    public static HttpUrlConnectionDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new HttpUrlConnectionDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadAttractions() {
        new AsyncTask<Void, Void, List<AttractionVO>>() {

            @Override
            protected List<AttractionVO> doInBackground(Void... voids) {
                URL url;
                BufferedReader reader = null;
                StringBuilder stringBuilder;

                try {
                    // create the HttpURLConnection
                    //http://www.aungpyaephyo.xyz/myanmar_attractions/getAttractionsList.php
                    url = new URL(MyanmarAttractionsConstants.ATTRACTION_BASE_URL + MyanmarAttractionsConstants.API_GET_ATTRACTION_LIST); //1.
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //2.

                    // just want to do an HTTP POST here
                    connection.setRequestMethod("POST"); //3.

                    // uncomment this if you want to write output to this url
                    //connection.setDoOutput(true);

                    // give it 15 seconds to respond
                    connection.setReadTimeout(15 * 1000); //4. ms

                    connection.setDoInput(true); //5.
                    connection.setDoOutput(true);

                    //put the request parameter into NameValuePair list.
                    List<NameValuePair> params = new ArrayList<>(); //6.
                    params.add(new BasicNameValuePair(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN, MyanmarAttractionsConstants.ACCESS_TOKEN));

                    //write the parameters from NameValuePair list into connection obj.
                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(getQuery(params));
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    connection.connect(); //7.

                    // read the output from the server
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //8.
                    stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    String responseString = stringBuilder.toString(); //9.
                    AttractionListResponse response = CommonInstances.getGsonInstance().fromJson(responseString, AttractionListResponse.class);
                    List<AttractionVO> attractionList = response.getAttractionList();

                    return attractionList;

                } catch (Exception e) {
                    Log.e(MyanmarAttractionsApp.TAG, e.getMessage());
                    AttractionModel.getInstance().notifyErrorInLoadingAttractions(e.getMessage());
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

                return null;
            }

            @Override
            protected void onPostExecute(List<AttractionVO> attractionList) {
                super.onPostExecute(attractionList);
                if (attractionList != null || attractionList.size() > 0) {
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
}
