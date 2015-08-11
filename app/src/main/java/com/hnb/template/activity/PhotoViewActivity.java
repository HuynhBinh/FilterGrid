package com.hnb.template.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.hnb.template.R;
import com.hnb.template.utils.StaticFunction;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.polites.android.GestureImageView;


public class PhotoViewActivity extends Activity
{

    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";
    private GestureImageView imgV;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        imgV = (GestureImageView) findViewById(R.id.imageView);
        imgV.setImageLevel(0);

        if (getIntent().hasExtra(PhotoViewActivity.EXTRA_IMAGE_URL))
        {
            String uri = getIntent().getExtras().getString(PhotoViewActivity.EXTRA_IMAGE_URL);
            loadImageToImageView(uri);
        }
        else
        {
            imgV.setImageResource(R.drawable.load_fail);
        }


    }

    private void loadImageToImageView(String uri)
    {
        StaticFunction.getImageLoader().loadImage(uri, StaticFunction.getImageLoaderOptions(), new ImageLoadingListener()
        {

            @Override
            public void onLoadingStarted(String arg0, View arg1)
            {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2)
            {
                imgV.setImageResource(R.drawable.load_fail);
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2)
            {

                imgV.setImageBitmap(arg2);
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1)
            {
                imgV.setImageResource(R.drawable.load_fail);
            }
        });
    }

}
