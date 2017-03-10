package com.example.lzz.tablayouttest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.example.lzz.tablayouttest.Interface.PresenterInterface;
import com.example.lzz.tablayouttest.db.BDImage;
import com.example.lzz.tablayouttest.until.API;
import com.example.lzz.tablayouttest.until.HttpUtil;
import com.example.lzz.tablayouttest.until.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/3/9.
 */

public class FragmentPresenter implements PresenterInterface{

    private List<BDImage> list;
    private Context context;
    private AnimationFragment animationFragment;
    private SceneryFragment sceneryFragment;
    private GirlsFragment girlsFragment;

    public FragmentPresenter() {

    }

    public FragmentPresenter(Context context, AnimationFragment animationFragment){
        this.context = context;
        this.animationFragment = animationFragment;
    }

    public FragmentPresenter(Context context,
                             AnimationFragment animationFragment,
                             SceneryFragment sceneryFragment,
                             GirlsFragment girlsFragment){
        this.context = context;
        this.animationFragment = animationFragment;
        this.sceneryFragment = sceneryFragment;
        this.girlsFragment = girlsFragment;
    }


    @Override
    public void start() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String imageString = pref.getString("animationImage", null);
        if (imageString != null){
            showImageInfo(imageString);
        }else {
            requestImageData();
        }
    }

    @Override
    public void refresh() {
        requestImageData();
    }

    private void requestImageData() {
        animationFragment.showLoading();
        HttpUtil.sendOKHttpRequest(API.animationImageUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animationFragment.showError();
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String imageResponse = response.body().string();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(context).edit();
                        editor.putString("animationImage", imageResponse);
                        editor.apply();
                        showImageInfo(imageResponse);
                    }
                });
            }

        });
    }

    private void showImageInfo(String imageData){
        list = Utility.handleImageResponse(imageData, context);
        if (list != null){
            animationFragment.showResults(list);
            animationFragment.stopLoading();
        }else {
            animationFragment.showError();
        }
    }
}
