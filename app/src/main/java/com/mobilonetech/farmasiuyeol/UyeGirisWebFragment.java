package com.mobilonetech.farmasiuyeol;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UyeGirisWebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UyeGirisWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UyeGirisWebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public String URL = "";
    public String TITLE = "Farmasi Mobil";
    public String FROM;

    WebView webViewKayit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ProgressBar m_PbProgress;

    public UyeGirisWebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UyeGirisWebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UyeGirisWebFragment newInstance() {
        UyeGirisWebFragment fragment = new UyeGirisWebFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        URL = getArguments().getString("URL");
        TITLE = getArguments().getString("Title");
        FROM = getArguments().getString("From");

        return inflater.inflate(R.layout.fragment_uye_giris_web, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

       /* this.transitionAd = new InterstitialAd(getActivity());
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

        this.webViewKayit = (WebView) getActivity().findViewById(R.id.webView_uyegiris);
        this.webViewKayit.getSettings().setJavaScriptEnabled(true);
        this.webViewKayit.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebView webView = this.webViewKayit;
        WebView.setWebContentsDebuggingEnabled(true);
        this.webViewKayit.getSettings().setBuiltInZoomControls(true);
        this.webViewKayit.getSettings().setDisplayZoomControls(false);
        this.webViewKayit.setPadding(10, 10, 10, 10);
        this.webViewKayit.setInitialScale(getScale());

        this.m_PbProgress = (ProgressBar) getActivity().findViewById(R.id.pbDetail1);
        this.m_PbProgress.setMax(100);

        //this.mAdView = (AdView) getView().findViewById(R.id.adView);
        //this.mAdView.loadAd(new AdRequest.Builder().addTestDevice("B85E3B305DFD350CBAEE82C5133FC392").build());


        String url = URL;
        this.webViewKayit.loadUrl(url);

        this.webViewKayit.setWebChromeClient(new WebChromeClient() {
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
        this.webViewKayit.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url1) {
                webViewKayit.loadUrl(url1);
                return false;
            }

        });

        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webViewKayit.getSettings().setUserAgentString(newUA);

        this.webViewKayit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //This is the filter
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (i == KeyEvent.KEYCODE_BACK) {
                    if (webViewKayit.canGoBack()) {
                        webViewKayit.goBack();
                        return true;
                    } else if (FROM.equals("A")) {
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private int getScale() {
        return Double.valueOf(Double.valueOf(new Double((double) ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth()).doubleValue() / new Double(1000.0d).doubleValue()).doubleValue() * 100.0d).intValue();
    }

}
