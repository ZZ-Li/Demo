package com.example.lzz.tablayouttest.until;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.lzz.tablayouttest.db.BDImage;

/**
 * Created by ASUS on 2017/3/7.
 */

public class Utility {
    public static List<BDImage> handleImageResponse(String response){
        List<BDImage> imageList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
