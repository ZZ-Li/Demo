package com.example.lzz.tablayouttest.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lzz.tablayouttest.ImageFragment;
import com.example.lzz.tablayouttest.db.BDImage;
import com.example.lzz.tablayouttest.until.Utility;

import java.util.List;

/**
 * Created by ASUS on 2017/12/7.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private List<String> list;

    public ViewPagerAdapter(FragmentManager fm, List<String> list, Context context) {
        super(fm);
        this.context = context;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
