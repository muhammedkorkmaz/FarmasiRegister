package com.mobilonetech.farmasiuyeol;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewFragment extends Fragment {

    public String URL = "";
    public String TITLE = "Farmasi Mobil";
    public String FROM;
    public String KAMPANYA = "N";
    Fragment fragment = null;

    private String cookies;
    private ProgressBar m_PbProgress;
    String m_Title;
    WebView m_WvGeneral;
    private MainFragment.OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(false);
        URL = getArguments().getString("URL");
        TITLE = getArguments().getString("Title");
        FROM = getArguments().getString("From");
        KAMPANYA = getArguments().getString("Kampanya");

        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragment.OnFragmentInteractionListener) {
            mListener = (MainFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles


        //this.mAdView = (AdView) getView().findViewById(R.id.adView);
        //this.mAdView.loadAd(new AdRequest.Builder().addTestDevice("B85E3B305DFD350CBAEE82C5133FC392").build());

        /*this.transitionAd = new InterstitialAd(getActivity());
        this.transitionAd.setAdUnitId(getString(R.string.transition_ad_unit_id));
        this.transitionAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
            }

            public void onAdFailedToLoad(int errorCode) {
            }

            public void onAdClosed() {
            }
        });
        loadTransitionAd();*/

        this.m_WvGeneral = (WebView) getView().findViewById(R.id.wv_Genel);


        this.m_WvGeneral.getSettings().setJavaScriptEnabled(true);
        //this.m_WvGeneral.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.m_WvGeneral.getSettings().setLoadWithOverviewMode(true);
        this.m_WvGeneral.getSettings().setUseWideViewPort(true);
        this.m_WvGeneral.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.m_WvGeneral.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.m_WvGeneral.getSettings().setDomStorageEnabled(true);
        this.m_WvGeneral.getSettings().setSupportZoom(true);
        this.m_WvGeneral.getSettings().setBuiltInZoomControls(true);
        this.m_WvGeneral.getSettings().setDisplayZoomControls(false);
        //this.m_WvGeneral.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.m_WvGeneral.setLayerType(1, null);
        this.m_WvGeneral.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        this.m_WvGeneral.setScrollbarFadingEnabled(false);
        this.m_WvGeneral.getSettings().setSavePassword(true);
        this.m_WvGeneral.getSettings().setSaveFormData(true);
        //this.m_WvGeneral.getSettings().setEnableSmoothTransition(true);

        if (Build.VERSION.SDK_INT >= 19) {
            this.m_WvGeneral.setLayerType(2, null);
        } else {
            this.m_WvGeneral.setLayerType(1, null);
        }
        String url = URL;
        this.m_PbProgress = (ProgressBar) getActivity().findViewById(R.id.pbDetail);
        this.m_PbProgress.setMax(100);
        getActivity().getWindow().setFeatureInt(2, -1);


        this.m_WvGeneral.loadUrl(url);

        this.m_WvGeneral.setWebChromeClient(new WebChromeClient() {
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            public void onProgressChanged(WebView view, int progress) {
                //getView().setTitle("Loading...");
                m_PbProgress.setProgress(progress);
                if (progress == 100) {
                    //WebViewActivity.this.setTitle(R.string.app_name);
                    m_PbProgress.setVisibility(View.INVISIBLE);
                    return;
                }
                m_PbProgress.setVisibility(View.VISIBLE);
            }
        });
        this.m_WvGeneral.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*if (!url.equals("https://www.farmasiint.com/pages/customer/login"))
                    mAdView.setVisibility(View.INVISIBLE);*/
                m_WvGeneral.loadUrl(url);
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                //Snackbar.make(view, "Sayfa yükleniyor...", 0).setAction("Action", null).show();
            }
        });

        /*if (!url.equals("https://www.farmasiint.com/pages/customer/login") &&
                !url.equals("https://www.farmasiint.com/pages/customer/newcustomer?RefGuid=925de180-aa97-45d3-9288-5a2fe6026b84")) {
            mAdView.setEnabled(false);
            mAdView.setVisibility(View.GONE);
        }*/

        this.m_WvGeneral.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //This is the filter
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (i == KeyEvent.KEYCODE_BACK) {
                    if (m_WvGeneral.canGoBack()) {
                        m_WvGeneral.goBack();
                        return true;
                    } else if (FROM.equals("Blog")) {
                        FeedFragment nextFrag = new FeedFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();

                        return true;
                    } else if (FROM.equals("A")) {

                        /*if (KAMPANYA != null && KAMPANYA.equals("E")) {
                            showTransitionAd();
                        }*/

                        MainFragment nextFrag = new MainFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                        return true;
                    }


                }
                return false;
            }

        });


        getActivity().setTitle(TITLE);

    }

    private int getScale() {
        return Double.valueOf(Double.valueOf(new Double((double) ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth()).doubleValue() / new Double(565.0d).doubleValue()).doubleValue() * 92.0d).intValue();
    }

    /*public void loadTransitionAd() {
        this.transitionAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B85E3B305DFD350CBAEE82C5133FC392")
                .build());
    }*/

    /*public void showTransitionAd() {
        if (this.transitionAd.isLoaded()) {
            this.transitionAd.show();
        }
    }*/
} 
