package com.example.lzz.tablayouttest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lzz.tablayouttest.Adapter.ViewPagerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowImageActivity extends AppCompatActivity {

    private ViewPager imageViewPager;

    private ImageView downloadImage;

    private LinearLayout downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imageViewPager = (ViewPager) findViewById(R.id.image_viewpager);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        final List<String> urlList = intent.getStringArrayListExtra("urlList");
        int position = 0;
        for (String string : urlList){
            if (!string.equals(imageUrl)){
                position = position + 1;
            }else {
                break;
            }
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), urlList, this);
        imageViewPager.setAdapter(adapter);
        imageViewPager.setOffscreenPageLimit(5);
        imageViewPager.setCurrentItem(position);

        downloadImage = (ImageView)findViewById(R.id.download_image);
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImage(urlList.get(imageViewPager.getCurrentItem()));
            }
        });
        downloadBtn = (LinearLayout)findViewById(R.id.download_layout);
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                downloadBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void DownloadImage(final String downloadUrl){
        if (ContextCompat.checkSelfPermission(ShowImageActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ShowImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        final String fileName = "/zz" + (new Random().nextInt(9000000) + 1000000) + ".jpg";
        final String directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                OutputStream os = null;
                try {
                    final File file = new File(directory + fileName);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(downloadUrl)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response != null){
                        is = response.body().byteStream();
                        os = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int len;
                        while ((len = is.read(b)) != -1){
                            os.write(b, 0, len);
                        }
                        response.body().close();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (file.exists()){
                                Toast.makeText(ShowImageActivity.this,
                                        "图片已保存,保存至:" + directory,  Toast.LENGTH_SHORT).show();
                                downloadImage.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(ShowImageActivity.this,
                                        "图片保存失败",  Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try{
                        if (is != null){
                            is.close();
                        }
                        if (os != null){
                            os.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "无法保存图片", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
