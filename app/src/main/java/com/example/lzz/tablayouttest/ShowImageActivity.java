package com.example.lzz.tablayouttest;

import android.content.Intent;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class ShowImageActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        ImageView imageView = (ImageView)findViewById(R.id.show_image);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(imageView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
