package com.mobilonetech.farmasiuyeol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , MainFragment.OnFragmentInteractionListener
        , UyeGirisWebFragment.OnFragmentInteractionListener {

    Fragment fragment = null;
    Bundle bundle = new Bundle();
    FragmentTransaction transaction;

    ImageView menuBadgeForum;
    ImageView menuBadgeEtkinlikler;
    ImageView menuBadgeAkademi;

    String m_URL;
    String m_Title;
    String m_From;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*menuBadgeForum = (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_forum));*/
        menuBadgeEtkinlikler = (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_etkinlikler));
        /*menuBadgeAkademi = (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_akademi));*/

        //menuBadgeForum.setImageResource(R.drawable.star);
        menuBadgeEtkinlikler.setImageResource(R.drawable.star);
        //menuBadgeAkademi.setImageResource(R.drawable.star);


        if (getIntent().getExtras() != null) {
            String url = getIntent().getStringExtra("url");
            if (url != null) {
                if (!url.equals("")) {
                    String title = getIntent().getStringExtra("title");

                    fragment = new WebViewFragment();
                    bundle.putString("Title", title);
                    bundle.putString("URL", url);
                    bundle.putString("From", "A");
                    bundle.putString("Kampanya", "E");

                    if (fragment != null) {
                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }

                }
            }
        } else {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, MainFragment.newInstance());
            transaction.commit();
        }
    }


    @Override
    public void onBackPressed() {
        /*if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {*/
        super.onBackPressed();
        //}
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anasayfa) {
            fragment = new MainFragment();
        } else if (id == R.id.nav_uyeol) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Farmasi Üyelik Formu");
            bundle.putString("URL", "https://www.farmasi.email/bize-katil");
            bundle.putString("From", "A");

        } else if (id == R.id.nav_uyegirisi) {
            fragment = new UyeGirisWebFragment();
            bundle.putString("Title", "Farmasi Giriş");
            bundle.putString("URL", "https://www.farmasiint.com/girisimci-girisi");
            bundle.putString("From", "A");
        } else if (id == R.id.nav_katalog) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Farmasi Katalog");
            bundle.putString("URL", "https://www.farmasi.email/farmasi-katalog");
            bundle.putString("From", "A");
        } else if (id == R.id.nav_alisveris) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "İndrimli Alışveriş");
            bundle.putString("URL", "https://www.farmasi.email/alisveris");
            bundle.putString("From", "A");
        } /*else if (id == R.id.nav_kazancsistemi) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Kazanç Sistemi");
            bundle.putString("URL", "https://www.farmasilove.com/kazancsistemi");
            bundle.putString("From", "A");
        }*/ /*else if (id == R.id.nav_drctuna) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Dr.C.Tuna(Ürün Tavsiyesi)");
            bundle.putString("URL", "https://www.farmasilove.com/drctunauruntavsiyeleri");
            bundle.putString("From", "A");
        }*/ /*else if (id == R.id.nav_blog) {
            fragment = new FeedFragment();
        }*/ /*else if (id == R.id.nav_forum) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Forum");
            bundle.putString("URL", "https://www.farmasilove.com/forum");
            bundle.putString("From", "A");
        }*/ else if (id == R.id.nav_kampanyalar) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Kampanyalar");
            bundle.putString("URL", "https://www.farmasi.email/firsatlar");
            bundle.putString("From", "A");
        } else if (id == R.id.nav_etkinlikler) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Etkinlikler");
            bundle.putString("URL", "https://www.farmasi.email/etkinlikler");
            bundle.putString("From", "A");
        } else if (id == R.id.nav_iletisim) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "İletişim");
            bundle.putString("URL", "https://www.farmasi.email/iletisim");
            bundle.putString("From", "A");
        } else if (id == R.id.nav_paylas) {
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.TEXT",
                    "https://play.google.com/store/apps/details?id=com.mobilonetech.farmasiuyeol");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_sss) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Sıkça Sorulan Sorular");
            bundle.putString("URL", "https://www.farmasi.email/sikca-sorulan-sorular");
            bundle.putString("From", "A");
        } /*else if (id == R.id.nav_akademi) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Akademi");
            bundle.putString("URL", "https://www.farmasilove.com/akademi");
            bundle.putString("From", "A");
        }*/

        if (fragment != null) {
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
