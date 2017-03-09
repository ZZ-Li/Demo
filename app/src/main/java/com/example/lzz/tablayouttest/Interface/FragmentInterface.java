package com.example.lzz.tablayouttest.Interface;

import android.app.Fragment;

import com.example.lzz.tablayouttest.FragmentPresenter;
import com.example.lzz.tablayouttest.db.BDImage;

import java.util.List;

/**
 * Created by ASUS on 2017/3/9.
 */

public interface FragmentInterface {

    void setPresenter(FragmentPresenter presenter);

    void showError();

    void showLoading();

    void stopLoading();

    void showResults(List<BDImage> list);
}
