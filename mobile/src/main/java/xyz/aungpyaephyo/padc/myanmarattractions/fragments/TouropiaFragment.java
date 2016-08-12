package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * Created by aung on 8/12/16.
 */
public class TouropiaFragment extends BaseFragment {

    private static final String TOUROPIA_URL = "http://www.touropia.com/tourist-attractions-in-myanmar/";

    @BindView(R.id.wv_web)
    WebView wvWeb;

    public static TouropiaFragment newInstance() {
        TouropiaFragment fragment = new TouropiaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_touropia, container, false);
        ButterKnife.bind(this, rootView);

        // Websettings to setup the webview
        WebSettings webSettings = wvWeb.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        wvWeb.setWebChromeClient(new WebChromeClient());

        wvWeb.requestFocus(View.FOCUS_DOWN);
        wvWeb.setFocusable(true);
        
        wvWeb.loadUrl(TOUROPIA_URL);

        return rootView;
    }
}
