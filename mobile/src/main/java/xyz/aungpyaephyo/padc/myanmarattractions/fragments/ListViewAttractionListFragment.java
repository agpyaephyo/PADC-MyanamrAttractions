package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.adapters.ListViewAttractionAdapter;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.GAUtils;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;

/**
 * Created by aung on 7/16/16.
 */
public class ListViewAttractionListFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.lv_attractions)
    ListView lvAttractions;

    private ListViewAttractionAdapter attractionAdapter;
    private AttractionViewHolder.ControllerAttractionItem controllerAttractionItem;

    public static Fragment newInstance() {
        ListViewAttractionListFragment fragment = new ListViewAttractionListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerAttractionItem = (AttractionViewHolder.ControllerAttractionItem) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attractionAdapter = new ListViewAttractionAdapter(null, controllerAttractionItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview_attraction_list, container, false);
        ButterKnife.bind(this, rootView);

        lvAttractions.setAdapter(attractionAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(MyanmarAttractionsConstants.ATTRACTION_LIST_LOADER_LISTVIEW, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                AttractionsContract.AttractionEntry.CONTENT_URI,
                null,
                null,
                null,
                AttractionsContract.AttractionEntry.COLUMN_TITLE + " ASC");
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

        Log.d(MyanmarAttractionsApp.TAG, "Retrieved attractions ASC : " + attractionList.size());
        attractionAdapter.setNewData(attractionList);

        AttractionModel.getInstance().setStoredData(attractionList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    protected void onSendScreenHit() {
        GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_ATTRACTION_LIST_LIST_VIEW);
    }
}
