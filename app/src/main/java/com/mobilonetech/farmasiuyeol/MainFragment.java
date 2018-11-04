package com.mobilonetech.farmasiuyeol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BottomNavigationView bottomNavigationView;
    Bundle bundle = new Bundle();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Fragment fragment = null;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_face:
                                /*selectedFragment = new WebViewFragment();
                                bundle.putString("Title", "Farmasi Facebook");
                                bundle.putString("URL", "https://www.facebook.com/farmasiloves/");
                                bundle.putString("From", "A");*/
                                startActivity(new Intent("android.intent.action.VIEW"
                                        , Uri.parse("https://www.facebook.com/farmasiloves/")));
                                break;
                            case R.id.action_ins:
                                /*selectedFragment = new WebViewFragment();
                                bundle.putString("Title", "Farmasi Instagram");
                                bundle.putString("URL", "https://www.instagram.com/farmasi_love/");
                                bundle.putString("From", "A");*/
                                startActivity(new Intent("android.intent.action.VIEW"
                                        , Uri.parse("https://www.instagram.com/farmasi_love/")));
                                break;
                            case R.id.action_twit:
                                /*selectedFragment = new WebViewFragment();
                                bundle.putString("Title", "Farmasi Twitter");
                                bundle.putString("URL", "https://twitter.com/farmasi_love");
                                bundle.putString("From", "A");*/
                                startActivity(new Intent("android.intent.action.VIEW"
                                        , Uri.parse("https://twitter.com/farmasi_love")));
                                break;
                            case R.id.action_plus:
                                /*selectedFragment = new WebViewFragment();
                                bundle.putString("Title", "Farmasi Plus");
                                bundle.putString("URL", "https://plus.google.com/u/0/111306979844784160358");
                                bundle.putString("From", "A");*/
                                startActivity(new Intent("android.intent.action.VIEW"
                                        , Uri.parse("https://plus.google.com/u/0/111306979844784160358")));
                                break;
                            case R.id.action_tube:
                               /* selectedFragment = new WebViewFragment();
                                bundle.putString("Title", "Farmasi YouTube");
                                bundle.putString("URL", "https://www.youtube.com/channel/UCY4zrzHKm-SSHFNPyoVX6mQ");
                                bundle.putString("From", "A");*/
                                startActivity(new Intent("android.intent.action.VIEW"
                                        , Uri.parse("https://www.youtube.com/channel/UCY4zrzHKm-SSHFNPyoVX6mQ")));
                                break;
                        }

                        if (selectedFragment != null) {
                            selectedFragment.setArguments(bundle);
                            FragmentTransaction ft = getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null);
                            ft.replace(R.id.content_frame, selectedFragment);
                            ft.commit();
                        }
                        return true;
                    }
                });

        getActivity().setTitle("Farmasi");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
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

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_uyegirisi) {
            fragment = new UyeGirisWebFragment();
            bundle.putString("Title", "Farmasi Giriş");
            bundle.putString("URL", "https://www.farmasiint.com/girisimci-girisi");
            bundle.putString("From", "A");
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            return true;
        } else if (id == R.id.action_uyeol) {
            fragment = new WebViewFragment();
            bundle.putString("Title", "Farmasi Üyelik Formu");
            bundle.putString("URL", "https://www.farmasi.email/bize-katil");
            bundle.putString("From", "A");
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
