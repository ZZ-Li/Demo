package com.example.lzz.tablayouttest.until;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.lzz.tablayouttest.db.BDImage;

/**
 * Created by ASUS on 2017/3/7.
 */

public class Utility {
    public static List<BDImage> handleImageResponse(String response, Context context){
        List<BDImage> imageList = new ArrayList<>();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;  // 屏幕宽度（像素）
        float density = dm.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕宽度算法:屏幕宽度（像素）/ 屏幕密度
        int screenWidth = (int)(width / 2);  // 屏幕宽度(dp)

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("imgs");
            for (int i = 0; i < jsonArray.length() - 1; i++){
                JSONObject imageObject = jsonArray.getJSONObject(i);
                BDImage image = new BDImage();
                image.setImageTitle(imageObject.getString("fromPageTitle"));
                image.setImageUrl(imageObject.getString("imageUrl"));
                image.setImageWidth(screenWidth);
                double db =  (double)screenWidth / (double)imageObject.getInt("imageWidth");
                int imageHeight = (int)(db * imageObject.getInt("imageHeight"));
                //Log.d("Utility", "imageHeight: " + imageHeight);
                image.setImageHeight(imageHeight);

                //image.setImageWidth(imageObject.getInt("imageWidth"));
                //image.setImageHeight(imageObject.getInt("imageHeight"));
                imageList.add(image);
            }
            return imageList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
