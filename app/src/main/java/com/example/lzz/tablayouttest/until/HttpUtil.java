package com.example.lzz.tablayouttest.until;


import com.example.lzz.tablayouttest.db.BDImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/3/7.
 */

public class HttpUtil {

    public static void sendOKHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    // 这种方法需要手动开启线程
    public static List<BDImage> sendOKHttpRequestTest(String address){
        List<BDImage> imageList = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(address).build();
            Response response = client.newCall(request).execute();
            if (response != null){
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONArray jsonArray = jsonObject.getJSONArray("imgs");
                for (int i = 0; i < jsonArray.length() - 1; i++){
                    JSONObject imageObject = jsonArray.getJSONObject(i);
                    BDImage image = new BDImage();
                    image.setImageTitle(imageObject.getString("fromPageTitle"));
                    image.setImageUrl(imageObject.getString("thumbLargeUrl"));
                    image.setImageWidth(imageObject.getInt("thumbLargeTnWidth"));
                    image.setImageHeight(imageObject.getInt("thumbLargeTnHeight"));
                    imageList.add(image);
                }
                return imageList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
