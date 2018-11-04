package com.mobilonetech.farmasiuyeol;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String cookies;
    //private ProgressBar m_PbProgress;
    String m_Title;
    WebView m_WvGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        this.m_Title = getIntent().getStringExtra("Title");
        getSupportActionBar().setTitle(this.m_Title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setStatusBarTranslucent(true);


        //this.mAdView = (AdView) findViewById(R.id.adView);
        //this.mAdView.loadAd(new AdRequest.Builder().addTestDevice("B85E3B305DFD350CBAEE82C5133FC392").build());

        this.m_WvGeneral = (WebView) findViewById(R.id.wv_Genel);
        this.m_WvGeneral.getSettings().setJavaScriptEnabled(true);
        this.m_WvGeneral.getSettings().setPluginState(WebSettings.PluginState.ON);

        WebView.setWebContentsDebuggingEnabled(true);
        this.m_WvGeneral.getSettings().setLoadWithOverviewMode(true);
        this.m_WvGeneral.getSettings().setUseWideViewPort(true);
        this.m_WvGeneral.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.m_WvGeneral.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.m_WvGeneral.getSettings().setDomStorageEnabled(true);
        this.m_WvGeneral.getSettings().setBuiltInZoomControls(true);
        this.m_WvGeneral.getSettings().setDisplayZoomControls(false);
        this.m_WvGeneral.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.m_WvGeneral.setLayerType(1, null);
        this.m_WvGeneral.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        this.m_WvGeneral.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        this.m_WvGeneral.getSettings().setUseWideViewPort(true);
        this.m_WvGeneral.getSettings().setSavePassword(true);
        this.m_WvGeneral.getSettings().setSaveFormData(true);
        this.m_WvGeneral.getSettings().setEnableSmoothTransition(true);
        if (Build.VERSION.SDK_INT >= 19) {
            this.m_WvGeneral.setLayerType(2, null);
        } else {
            this.m_WvGeneral.setLayerType(1, null);
        }
        String url = getIntent().getStringExtra("URL");
        //this.m_PbProgress = (ProgressBar) findViewById(R.id.pbDetail);
        //this.m_PbProgress.setMax(100);
        getWindow().setFeatureInt(2, -1);

        /*if (!url.equals("https://www.farmasiint.com/pages/customer/login") &&
                !url.equals("https://www.farmasiint.com/pages/customer/newcustomer?RefGuid=925de180-aa97-45d3-9288-5a2fe6026b84"))
            mAdView.setVisibility(View.INVISIBLE);*/

        this.m_WvGeneral.loadUrl(url);

        this.m_WvGeneral.setWebChromeClient(new WebChromeClient() {
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            public void onProgressChanged(WebView view, int progress) {
                WebViewActivity.this.setTitle("Loading...");
                //WebViewActivity.this.m_PbProgress.setProgress(progress);
                if (progress == 100) {
                    WebViewActivity.this.setTitle(R.string.app_name);
                    //WebViewActivity.this.m_PbProgress.setVisibility(View.INVISIBLE);
                    return;
                }
                //WebViewActivity.this.m_PbProgress.setVisibility(View.VISIBLE);
            }
        });
        this.m_WvGeneral.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*if (!url.equals("https://www.farmasiint.com/pages/customer/login"))
                    mAdView.setVisibility(View.INVISIBLE);*/
                WebViewActivity.this.m_WvGeneral.loadUrl(url);
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                Snackbar.make(view, "Sayfa yükleniyor...", 0).setAction("Action", null).show();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.m_WvGeneral.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.m_WvGeneral.goBack();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp) {
            Intent intent;
            if (getIntent().getStringExtra("From").equals("MainActivity")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            } else if (getIntent().getStringExtra("Kampanya").equals("Evet")) {
                //showTransitionAd();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_uyeol) {
            Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("URL", "https://www.farmasi.email/bize-katil");
            intent.putExtra("Title", "Farmasi Üyelik Formu");
            //intent.putExtra("Kampanya", "Hayır");
            WebViewActivity.this.startActivity(intent);
        } else if (id == R.id.nav_uyegirisi) {
            Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("URL", "https://www.farmasiint.com/girisimci-girisi");
            intent.putExtra("Title", "Farmasi Giriş");
            //intent.putExtra("Kampanya", "Hayır");
            WebViewActivity.this.startActivity(intent);
        } else if (id == R.id.nav_alisveris) {

        } /*else if (id == R.id.nav_blog) {
            WebViewActivity.this.startActivity(new Intent(WebViewActivity.this, FeedActivity.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

