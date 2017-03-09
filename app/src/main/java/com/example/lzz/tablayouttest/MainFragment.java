package com.example.lzz.tablayouttest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.tablayouttest.Adapter.MainPagerAdapter;

/**
 * Created by ASUS on 2017/3/6.
 */

public class MainFragment extends Fragment {

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;
    private FloatingActionButton fab;

    private AnimationFragment animationFragment;
    private SceneryFragment sceneryFragment;
    private GirlsFragment girlsFragment;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (animationFragment == null){
            animationFragment = AnimationFragment.newInstance();
        }
        if (sceneryFragment == null){
            sceneryFragment = SceneryFragment.newInstance();
        }
        if (girlsFragment == null){
            girlsFragment = GirlsFragment.newInstance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new MainPagerAdapter(getChildFragmentManager(), getActivity(),
                animationFragment,
                sceneryFragment,
                girlsFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
