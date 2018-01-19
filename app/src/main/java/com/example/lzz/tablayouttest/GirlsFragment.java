package com.example.lzz.tablayouttest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.tablayouttest.Adapter.ImageAdapter;
import com.example.lzz.tablayouttest.db.BDImage;
import com.example.lzz.tablayouttest.until.API;
import com.example.lzz.tablayouttest.until.HttpUtil;
import com.example.lzz.tablayouttest.until.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/3/6.
 */

public class GirlsFragment extends Fragment{

    private List<BDImage> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private ImageAdapter adapter;

    public static GirlsFragment newInstance(){
        return new GirlsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GirlsFragment","onCreate" + getActivity().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestImageData();
            }
        });

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String imageString = pref.getString("girlsImage", null);
        if (imageString != null){
            showImageInfo(imageString);
        }else {
            requestImageData();
        }
        showImageInfo(imageString);
        return view;
    }

    private void requestImageData(){
        swipeRefresh.setRefreshing(true);
        HttpUtil.sendOKHttpRequest(API.girlsImageUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                final String imageResponse = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString("girlsImage", imageResponse);
                        editor.apply();
                        showImageInfo(imageResponse);
                    }
                });
            }
        });
    }

    private void showImageInfo(String imageData){
        List<BDImage> list = Utility.handleImageResponse(imageData, getActivity());
        if (list != null){
            imageList.clear();
            for (BDImage image : list){
                imageList.add(image);
            }
            if (adapter == null){
                adapter = new ImageAdapter(imageList);
                adapter.setImageItemOnClickListener(new ImageAdapter.ImageItemClickListener() {
                    @Override
                    public void imageItemOnClick(String imageUrl) {
                        Intent intent = new Intent(getActivity(), ShowImageActivity.class);
                        intent.putExtra("imageUrl", imageUrl);
                        ArrayList<String> urlList = new ArrayList<String>();
                        for (BDImage image : imageList){
                            urlList.add(image.getImageUrl());
                        }
                        intent.putStringArrayListExtra("urlList", urlList);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }else {
            Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
        }
        swipeRefresh.setRefreshing(false);
    }
}
