package com.mobilonetech.farmasiuyeol;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private Context mContext;
    private List<RssFeedModel> mRssFeedModels;

    Fragment fragment = null;
    FragmentManager fragmentManager;
    Bundle bundle = new Bundle();

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        //Button mButton;
        TextView textViewFeed;
        ImageView imageViewFeed;
        RelativeLayout ly;

        public ViewHolder(View v) {
            super(v);
            this.textViewFeed = (TextView) v.findViewById(R.id.textView_feed);
            this.imageViewFeed = (ImageView) v.findViewById(R.id.imageView_feed);
            this.ly = (RelativeLayout) v.findViewById(R.id.layout_feed);
        }
    }

    public FeedAdapter(Context context, List<RssFeedModel> dataSet, FragmentManager fragmentManager) {
        this.mRssFeedModels = dataSet;
        this.mContext = context;
        this.fragmentManager = fragmentManager;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.custom_view_feed, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final RssFeedModel rssFeedModel = (RssFeedModel) this.mRssFeedModels.get(position);

        holder.textViewFeed.setText(rssFeedModel.title);
        Glide.with(mContext)
                .load(rssFeedModel.description)
                .asBitmap()
                .error(R.drawable.farmasi_love)
                .override(175, 175)
                .centerCrop()
                .into(new BitmapImageViewTarget(holder.imageViewFeed) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });

        holder.ly.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                //downloadTask.cancel(true);
                fragment = new WebViewFragment();
                bundle.putString("Title", rssFeedModel.title);
                bundle.putString("URL", rssFeedModel.link);
                bundle.putString("From", "Blog");

                if (fragment != null) {
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = fragmentManager.beginTransaction().addToBackStack(null);
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            }
        });
    }

    public int getItemCount() {
        return this.mRssFeedModels.size();
    }
} 
