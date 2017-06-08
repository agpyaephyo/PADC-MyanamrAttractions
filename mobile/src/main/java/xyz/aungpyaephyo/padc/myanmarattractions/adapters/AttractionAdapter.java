package xyz.aungpyaephyo.padc.myanmarattractions.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;

/**
 * Created by aung on 7/6/16.
 */
public class AttractionAdapter extends BaseRecyclerAdapter<AttractionViewHolder, AttractionVO> {

    private AttractionViewHolder.ControllerAttractionItem mControllerAttractionItem;

    public AttractionAdapter(Context context, AttractionViewHolder.ControllerAttractionItem controllerAttractionItem) {
        super(context);
        mControllerAttractionItem = controllerAttractionItem;
    }

    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.view_item_attraction, parent, false);
        return new AttractionViewHolder(itemView, mControllerAttractionItem);
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }
}
