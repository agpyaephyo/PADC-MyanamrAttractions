package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.adapters.AttractionPagerAdapter;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MMFontUtils;

/**
 * Created by aung on 7/20/16.
 */
public class AttractionPagerFragment extends BaseFragment {

    @BindView(R.id.tl_attractions)
    TabLayout tlAttractions;

    @BindView(R.id.pager_attractions)
    ViewPager pagerAttractions;

    private AttractionPagerAdapter mAttractionPagerAdapter;

    public static AttractionPagerFragment newInstance() {
        AttractionPagerFragment fragment = new AttractionPagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttractionPagerAdapter = new AttractionPagerAdapter(getActivity().getSupportFragmentManager());
        mAttractionPagerAdapter.addTab(AttractionListFragment.newInstance(), getString(R.string.myanmar_attractions_recycler_view));
        mAttractionPagerAdapter.addTab(ListViewAttractionListFragment.newInstance(), getString(R.string.myanmar_attractions_list_view));
        mAttractionPagerAdapter.addTab(GridViewAttractionListFragment.newInstance(), getString(R.string.myanmar_attractions_grid_view));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager_attractions, container, false);
        ButterKnife.bind(this, rootView);

        pagerAttractions.setAdapter(mAttractionPagerAdapter);
        pagerAttractions.setOffscreenPageLimit(mAttractionPagerAdapter.getCount());

        tlAttractions.setupWithViewPager(pagerAttractions);

        MMFontUtils.applyMMFontToTabLayout(tlAttractions);

        return rootView;
    }
}
