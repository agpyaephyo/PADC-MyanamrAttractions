package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.JsonUtils;

/**
 * Created by aung on 7/6/16.
 */
public class AttractionModel {

    private static final String ATTRACTION_LIST = "myanmar_attractions.json";

    private static AttractionModel objInstance;

    private List<AttractionVO> attractionList;

    private AttractionModel() {
        attractionList = initializeAttractions();
    }

    public static AttractionModel getInstance() {
        if (objInstance == null) {
            objInstance = new AttractionModel();
        }
        return objInstance;
    }

    private List<AttractionVO> initializeAttractions() {
        List<AttractionVO> attractionList = new ArrayList<>();

        try {
            String attractions = JsonUtils.getInstance().loadDummyData(ATTRACTION_LIST);
            Type listType = new TypeToken<List<AttractionVO>>() {
            }.getType();
            attractionList = CommonInstances.getGsonInstance().fromJson(attractions, listType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attractionList;
    }

    public List<AttractionVO> getAttractionList() {
        return attractionList;
    }

    public AttractionVO getAttractionByName(String attractionName) {
        for(AttractionVO attraction : attractionList) {
            if(attraction.getTitle().equals(attractionName))
                return attraction;
        }

        return null;
    }
}
