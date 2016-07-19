package xyz.aungpyaephyo.padc.myanmarattractions.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;
import xyz.aungpyaephyo.padc.myanmarattractions.views.holders.AttractionViewHolder;

/**
 * Created by aung on 7/16/16.
 */
public class ListViewAttractionAdapter extends BaseAdapter {

    private List<AttractionVO> attractionList;
    private LayoutInflater inflater;
    private AttractionViewHolder.ControllerAttractionItem controllerAttractionItem;

    public ListViewAttractionAdapter(List<AttractionVO> attractionList, AttractionViewHolder.ControllerAttractionItem controllerAttractionItem) {
        if (attractionList != null) {
            this.attractionList = attractionList;
        } else {
            this.attractionList = new ArrayList<>();
        }
        inflater = LayoutInflater.from(MyanmarAttractionsApp.getContext());
        this.controllerAttractionItem = controllerAttractionItem;
    }

    @Override
    public int getCount() {
        return attractionList.size();
    }

    @Override
    public AttractionVO getItem(int position) {
        return attractionList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AttractionViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.view_item_attraction, parent, false);
            viewHolder = new AttractionViewHolder(convertView, controllerAttractionItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AttractionViewHolder) convertView.getTag();
        }

        viewHolder.bindData(getItem(position));
        return convertView;
    }


    public void setNewData(List<AttractionVO> newAttractionList) {
        attractionList = newAttractionList;
        notifyDataSetChanged();
    }
}
