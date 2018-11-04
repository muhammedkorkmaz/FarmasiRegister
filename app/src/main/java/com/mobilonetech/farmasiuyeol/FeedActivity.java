package com.mobilonetech.farmasiuyeol;

import android.arch.lifecycle.BuildConfig;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Xml;
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

public class FeedActivity extends AppCompatActivity {

    private Adapter mAdapter;
    private Context mContext;
    private String mFeedDescription;
    private String mFeedLink;
    private List<RssFeedModel> mFeedModelList;
    private String mFeedTitle;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getSupportActionBar().setTitle("Blog");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mContext = getApplicationContext();
        this.mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.mRecyclerView.setHasFixedSize(true);

        this.mLayoutManager = new StaggeredGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        new FetchFeedTask().execute(new Void[]{(Void) null});
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
                            description = result;
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
                FeedActivity.this.mFeedModelList = FeedActivity.this.parseFeed(new URL(this.urlLink).openConnection().getInputStream());
                return Boolean.valueOf(true);
            } catch (IOException e) {
                return Boolean.valueOf(false);
            } catch (XmlPullParserException e2) {
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean success) {
            if (success.booleanValue()) {
                //FeedActivity.this.mRecyclerView.setAdapter(new FeedAdapter(FeedActivity.this.mContext, FeedActivity.this.mFeedModelList,getFragmentManager()));
            } else {
                Toast.makeText(FeedActivity.this, "Enter a valid Rss feed url", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
