package xyz.aungpyaephyo.padc.myanmarattractions.data.agents;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.JsonUtils;

/**
 * Created by aung on 7/9/16.
 */
public class OfflineDataAgent implements AttractionDataAgent {

    private static final String OFFLINE_ATTRACTION_LIST = "myanmar_attractions.json";

    private static OfflineDataAgent objInstance;

    private OfflineDataAgent() {

    }

    public static OfflineDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new OfflineDataAgent();
        }

        return objInstance;
    }

    @Override
    public void loadAttractions() {
        try {
            String attractions = JsonUtils.getInstance().loadDummyData(OFFLINE_ATTRACTION_LIST);
            Type listType = new TypeToken<List<AttractionVO>>() {
            }.getType();
            List<AttractionVO> attractionList = CommonInstances.getGsonInstance().fromJson(attractions, listType);

            AttractionModel.getInstance().notifyAttractionsLoaded(attractionList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String name, String email, String password, String dateOfBirth, String countryOfOrigin) {

    }

    @Override
    public void login(String email, String password) {

    }
}
