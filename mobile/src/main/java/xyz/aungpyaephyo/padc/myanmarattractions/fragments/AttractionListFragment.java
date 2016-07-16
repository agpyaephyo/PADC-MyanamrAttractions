package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.adapters.AttractionAdapter;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;

/**
 * Created by aung on 7/15/16.
 */
public class AttractionListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_attractions)
    RecyclerView rvAttractions;

    private AttractionAdapter mAttractionAdapter;
    private AttractionViewHolder.ControllerAttractionItem controllerAttractionItem;

    private BroadcastReceiver mDataLoadedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO instructions when the new data is ready.
            String extra = intent.getStringExtra("key-for-extra");
            Toast.makeText(getContext(), "Extra : " + extra, Toast.LENGTH_SHORT).show();

            List<AttractionVO> newAttractionList = AttractionModel.getInstance().getAttractionList();
            mAttractionAdapter.setNewData(newAttractionList);
        }
    };

    public static AttractionListFragment newInstance() {
        AttractionListFragment fragment = new AttractionListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerAttractionItem = (AttractionViewHolder.ControllerAttractionItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attraction_list, container, false);
        ButterKnife.bind(this, rootView);

        List<AttractionVO> attractionList = AttractionModel.getInstance().getAttractionList();
        mAttractionAdapter = new AttractionAdapter(attractionList, controllerAttractionItem);
        rvAttractions.setAdapter(mAttractionAdapter);

        int gridColumnSpanCount = getResources().getInteger(R.integer.attraction_list_grid);
        rvAttractions.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(MyanmarAttractionsConstants.ATTRACTION_LIST_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDataLoadedBroadcastReceiver, new IntentFilter(AttractionModel.BROADCAST_DATA_LOADED));

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mDataLoadedBroadcastReceiver);

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                AttractionsContract.AttractionEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<AttractionVO> attractionList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                AttractionVO attraction = AttractionVO.parseFromCursor(data);
                attraction.setImages(AttractionVO.loadAttractionImagesByTitle(attraction.getTitle()));
                attractionList.add(attraction);
            } while (data.moveToNext());
        }

        Log.d(MyanmarAttractionsApp.TAG, "Retrieved attractions : " + attractionList.size());
        mAttractionAdapter.setNewData(attractionList);

        AttractionModel.getInstance().setStoredData(attractionList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onEventMainThread(DataEvent.AttractionDataLoadedEvent event) {
        String extra = event.getExtraMessage();
        Toast.makeText(getContext(), "Extra : " + extra, Toast.LENGTH_SHORT).show();

        //List<AttractionVO> newAttractionList = AttractionModel.getInstance().getAttractionList();
        List<AttractionVO> newAttractionList = event.getAttractionList();
        mAttractionAdapter.setNewData(newAttractionList);
    }
}
