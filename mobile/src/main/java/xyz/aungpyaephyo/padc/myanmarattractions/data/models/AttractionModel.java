package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.AttractionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.HttpUrlConnectionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OfflineDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OkHttpDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit.RetrofitDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by aung on 7/6/16.
 */
public class AttractionModel extends BaseModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static AttractionModel objInstance;

    private List<AttractionVO> mAttractionList;

    private AttractionModel() {
        super();
        mAttractionList = new ArrayList<>();
        loadAttractions();
    }

    public static AttractionModel getInstance() {
        if (objInstance == null) {
            objInstance = new AttractionModel();
        }
        return objInstance;
    }

    public void loadAttractions() {
        dataAgent.loadAttractions();
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
        //Notify that the data is ready - using LocalBroadcast
        mAttractionList = attractionList;

        //keep the data in persistent layer.
        AttractionVO.saveAttractions(mAttractionList);

        //broadcastAttractionLoadedWithEventBus();
        //broadcastAttractionLoadedWithLocalBroadcastManager();
    }

    public void notifyErrorInLoadingAttractions(String message) {

    }

    public String getRandomAttractionImage() {
        if (mAttractionList == null || mAttractionList.size() == 0) {
            return null;
        }

        Random random = new Random();
        int randomInt = random.nextInt(mAttractionList.size());

        AttractionVO attraction = mAttractionList.get(randomInt);
        return MyanmarAttractionsConstants.IMAGE_ROOT_DIR + attraction.getImages()[attraction.getImages().length - 1];
    }

    private void broadcastAttractionLoadedWithLocalBroadcastManager() {
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(MyanmarAttractionsApp.getContext()).sendBroadcast(intent);
    }

    private void broadcastAttractionLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.AttractionDataLoadedEvent("extra-in-broadcast", mAttractionList));
    }

    public void setStoredData(List<AttractionVO> attractionList) {
        mAttractionList = attractionList;
    }
}
