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

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    private List<BDImage> mList;

    private Context context;

    private ImageItemClickListener clickListener;

    static class NormalViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public NormalViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_item);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View view){
            super(view);
        }
    }

    public ImageAdapter(List<BDImage> list){
        mList = list;
    }

    public interface ImageItemClickListener{
        void imageItemOnClick(String imageUrl);
    }

    public void setImageItemOnClickListener(ImageItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        switch (viewType){
            case TYPE_NORMAL:
                View view = LayoutInflater.from(context).inflate(R.layout.fragment_image_item, parent,false);
                final NormalViewHolder holder = new NormalViewHolder(view);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        BDImage image = mList.get(position);
                        clickListener.imageItemOnClick(image.getImageUrl());
    //                Intent intent = new Intent(context, ShowImageActivity.class);
    //                intent.putExtra("imageUrl", image.getImageUrl());
    //                context.startActivity(intent);
                    }
                });
                return holder;
            case TYPE_FOOTER:
                return new FootViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.recycler_footer, parent, false));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            NormalViewHolder normalHolder = (NormalViewHolder)holder;
            BDImage image = mList.get(position);
            ViewGroup.LayoutParams params = normalHolder.imageView.getLayoutParams();
            params.width = image.getImageWidth();
            params.height = image.getImageHeight();
            normalHolder.imageView.setLayoutParams(params);
            Glide.with(context).load(image.getImageUrl())
                    //.asBitmap()
                    .error(R.drawable.nav_header_image)
                    //.override(image.getImageWidth(), image.getImageHeight())
                    //.centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(normalHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
}
