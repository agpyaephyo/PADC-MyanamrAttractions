package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * Created by aung on 8/11/16.
 */
public class ContactUsFragment extends BaseFragment {

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
