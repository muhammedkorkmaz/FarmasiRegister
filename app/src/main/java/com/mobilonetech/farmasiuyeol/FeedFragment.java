package com.mobilonetech.farmasiuyeol;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private Adapter mAdapter;
    private Context mContext;
    private String mFeedDescription;
    private String mFeedLink;
    private List<RssFeedModel> mFeedModelList;
    private String mFeedTitle;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    RelativeLayout mRelativeLayout;
    private MainFragment.OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }


    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mContext = getActivity().getApplicationContext();
        this.mRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rl);
        this.mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        this.mRecyclerView.setHasFixedSize(true);

        this.mLayoutManager = new StaggeredGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        new FetchFeedTask().execute(new Void[]{(Void) null});

        getActivity().setTitle("Blog");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            MainFragment nextFrag = new MainFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
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

    public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        List<RssFeedModel> items = new ArrayList();
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.nextTag();
            while (xmlPullParser.next() != 1) {
                int eventType = xmlPullParser.getEventType();
                String name = xmlPullParser.getName();
                if (name != null) {
                    if (eventType == 3) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                    } else if (eventType == 2 && name.equalsIgnoreCase("item")) {
                        isItem = true;
                    } else {
                        Log.d("MyXmlParser", "Parsing name ==> " + name);
                        String result = BuildConfig.FLAVOR;
                        if (xmlPullParser.next() == 4) {
                            result = xmlPullParser.getText();
                            xmlPullParser.nextTag();
                        }
                        if (name.equalsIgnoreCase("title")) {
                            title = result;
                        } else if (name.equalsIgnoreCase("link")) {
                            link = result;
                        } else if (name.equalsIgnoreCase("content:encoded")) {
                            if (!result.equals("farmasilove")) {
                                String img = result;
                                //String cleanUp = img.substring(0, img.indexOf(">")+1);
                                img = img.substring(img.indexOf("src=") + 5);
                                int indexOf = img.indexOf("'");
                                if (indexOf == -1) {
                                    indexOf = img.indexOf("\"");
                                }
                                img = img.substring(0, indexOf);

                                description = img;
                            }
                        }

                        if (!(title == null || link == null || description == null)) {
                            if (isItem) {
                                items.add(new RssFeedModel(title, link, description));
                            } else {
                                this.mFeedTitle = title;
                                this.mFeedLink = link;
                                this.mFeedDescription = description;
                            }
                            title = null;
                            link = null;
                            description = null;
                            isItem = false;
                        }
                    }
                }
            }
            return items;
        } finally {
            inputStream.close();
        }
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {
        private String urlLink;

        private FetchFeedTask() {
        }

        protected void onPreExecute() {
            this.urlLink = "https://www.farmasilove.com/feed.xml";
        }

        protected Boolean doInBackground(Void... voids) {
            try {
                if (!(this.urlLink.startsWith("http://") || this.urlLink.startsWith("https://"))) {
                    this.urlLink = "http://" + this.urlLink;
                }
                mFeedModelList = parseFeed(new URL(this.urlLink).openConnection().getInputStream());
                return Boolean.valueOf(true);
            } catch (IOException e) {
                return Boolean.valueOf(false);
            } catch (XmlPullParserException e2) {
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean success) {
            if (success.booleanValue()) {
                mRecyclerView.setAdapter(new FeedAdapter(mContext, mFeedModelList, getFragmentManager()));
            } else {
                Toast.makeText(getActivity(), "Enter a valid Rss feed url", Toast.LENGTH_SHORT).show();
            }
        }
    }
} 
