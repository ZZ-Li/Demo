package com.example.lzz.tablayouttest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.lzz.tablayouttest.R;
import com.example.lzz.tablayouttest.ShowImageActivity;
import com.example.lzz.tablayouttest.db.BDImage;

import java.util.List;

/**
 * Created by ASUS on 2017/3/7.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<BDImage> mList;

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_item);
        }
    }

    public ImageAdapter(List<BDImage> list){
        mList = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_image_item, parent,false);
        if (context == null){
            context = parent.getContext();
        }
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BDImage image = mList.get(position);
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("imageUrl", image.getImageUrl());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BDImage image = mList.get(position);
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.width = image.getImageWidth();
        params.height = image.getImageHeight();
        holder.imageView.setLayoutParams(params);
        Glide.with(context).load(image.getImageUrl())
                //.asBitmap()
                .error(R.drawable.nav_header_image)
                //.override(image.getImageWidth(), image.getImageHeight())
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
