package com.example.lzz.tablayouttest;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.lzz.tablayouttest.Interface.FragmentInterface;
import com.example.lzz.tablayouttest.db.BDImage;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by ASUS on 2017/3/6.
 */

public class AnimationFragment extends Fragment implements FragmentInterface{

    private List<BDImage> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private ImageAdapter adapter;
    private FragmentPresenter presenter;


    public static AnimationFragment newInstance(){
        return new AnimationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FragmentPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int totalItemCount = layoutManager.getItemCount();
                    int[] lastPositions = layoutManager.findLastCompletelyVisibleItemPositions(
                            new int[layoutManager.getSpanCount()]);
                    int max = lastPositions[0];
                    for (int value : lastPositions) {
                        if (value > max) {
                            max = value;
                        }
                    }
                    int lastVisibleItem = max;
                    if (lastVisibleItem >= totalItemCount - 1 && isSlidingToLast){
                        //getLoadMoreImage();
                        presenter.loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });

        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        presenter.start();
        return view;
    }

    @Override
    public void showError() {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
        Log.d("AnimationFragment","showError");
    }

    @Override
    public void stopLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void showResults(List<BDImage> list) {
        if (!imageList.isEmpty()){
            imageList.clear();
            /* 这里不能直接使用 imageList = list更新数据，
            否则会造成页面不会更新显示而空白。(原因 (可能)list没有指向同一个地址)*/
            for (BDImage image : list){
                imageList.add(image);
            }
        }else {
            imageList = list;
        }
        if (adapter == null) {
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
            Log.d("AnimationFragment","adapter.notifyDataSetChanged");
            adapter.notifyDataSetChanged();
        }
//        adapter = new ImageAdapter(imageList);
//        recyclerView.setAdapter(adapter);
    }

}
