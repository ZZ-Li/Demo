package com.example.lzz.tablayouttest;

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


import java.util.List;


/**
 * Created by ASUS on 2017/3/6.
 */

public class AnimationFragment extends Fragment implements FragmentInterface{

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
        Log.d("AnimationFragment","onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        presenter.start();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        return view;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void showError() {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
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
        if (adapter == null) {
            adapter = new ImageAdapter(list);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    //    private void requestImageData() {
//        HttpUtil.sendOKHttpRequest(API.animationImageUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefresh.setRefreshing(false);
//                        Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                list = Utility.handleImageResponse(response.body().string());
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefresh.setRefreshing(true);
//                        if (list != null){
//                            if (adapter == null){
//                                adapter = new ImageAdapter(list);
//                                recyclerView.setAdapter(adapter);
//                                swipeRefresh.setRefreshing(false);
//                            }else {
//                                adapter.notifyDataSetChanged();
//                                swipeRefresh.setRefreshing(false);
//                            }
//                        }else {
//                            swipeRefresh.setRefreshing(false);
//                            Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//
//        });
//    }

}
