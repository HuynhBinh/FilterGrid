package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ImageView;

import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;
import com.hnb.template.utils.ColorFilterUtils;

/**
 * Created by HuynhBinh on 8/11/15.
 */
public class DrawActivity extends BaseActivity
{

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initView();

        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.mia);
        Bitmap bmp = ColorFilterUtils.doColorFilterTest(original);

        imageView.setImageBitmap(bmp);
    }


    private void initView()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
