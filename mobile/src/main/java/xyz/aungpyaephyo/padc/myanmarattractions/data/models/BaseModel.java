package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.AttractionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.HttpUrlConnectionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OfflineDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.OkHttpDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit.RetrofitDataAgent;

/**
 * Created by aung on 7/15/16.
 */
public abstract class BaseModel {

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    protected AttractionDataAgent dataAgent;

    public BaseModel() {
        //initDataAgent(INIT_DATA_AGENT_RETROFIT);
        //dataAgent = OfflineDataAgent.getInstance();
        dataAgent = RetrofitDataAgent.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    /*
    private void initDataAgent(int initType) {
        switch (initType) {
            case INIT_DATA_AGENT_OFFLINE:
                dataAgent = OfflineDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_HTTP_URL_CONNECTION:
                dataAgent = HttpUrlConnectionDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_OK_HTTP:
                dataAgent = OkHttpDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_RETROFIT:
                dataAgent = RetrofitDataAgent.getInstance();
                break;
        }
    }
    */

    public void onEventMainThread(Object obj) {

    }
}
