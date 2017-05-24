package com.bfgirl.administrator.bfgirl.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.bfgirl.administrator.bfgirl.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by Administrator。 on 2017/5/21.
 */

public class DetailActivity extends Activity {
    private PhotoView imageView;
    private  ImageView img_back;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);
        imageView =(PhotoView)findViewById(R.id.de_img);
        img_back= (ImageView) findViewById(R.id.img_back);
        ViewCompat.setTransitionName(imageView, "123");
        initViews();
    }
   //初始化数据
    private void initViews()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String Img_url = intent.getStringExtra("Img_url");
        Glide.with(DetailActivity.this)
                .load(Img_url).into(imageView);
    }
}
