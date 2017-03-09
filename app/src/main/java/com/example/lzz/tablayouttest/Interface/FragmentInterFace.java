package com.example.lzz.tablayouttest.Interface;

import com.example.lzz.tablayouttest.db.BDImage;

import java.util.List;

/**
 * Created by ASUS on 2017/3/9.
 */

public interface FragmentInterFace {

    void showError();

    void showLoading();

    void stopLoading();

    void showResults(List<BDImage> list);

    void refresh();
}
