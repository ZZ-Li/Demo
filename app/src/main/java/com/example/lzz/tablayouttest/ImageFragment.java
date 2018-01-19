package com.example.lzz.tablayouttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

/**
 * Created by ASUS on 2017/12/7.
 */

public class ImageFragment extends Fragment {

    private String imageUrl;

    public static ImageFragment newInstance(String imageUrl){
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString("imageUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_image, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.show_image);
        Glide.with(this).load(imageUrl).into(imageView);
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
}
