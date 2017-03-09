package com.example.lzz.tablayouttest.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.lzz.tablayouttest.AnimationFragment;
import com.example.lzz.tablayouttest.FragmentPresenter;
import com.example.lzz.tablayouttest.GirlsFragment;
import com.example.lzz.tablayouttest.SceneryFragment;

/**
 * Created by ASUS on 2017/3/6.
 */

public class MainPagerAdapter extends FragmentPagerAdapter{

    private String[] titles = new String[]{"动漫", "风景", "Girls"};
    private Context context;

    private AnimationFragment animationFragment;
    private SceneryFragment sceneryFragment;
    private GirlsFragment girlsFragment;
    private FragmentPresenter presenter;

    public MainPagerAdapter(FragmentManager fm, Context context,
                            AnimationFragment animationFragment,
                            SceneryFragment sceneryFragment,
                            GirlsFragment girlsFragment) {
        super(fm);
        this.context = context;
        this.animationFragment = animationFragment;
        this.sceneryFragment = sceneryFragment;
        this.girlsFragment = girlsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return sceneryFragment;
        }else if (position == 2){
            return girlsFragment;
        }else {
            return animationFragment;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
