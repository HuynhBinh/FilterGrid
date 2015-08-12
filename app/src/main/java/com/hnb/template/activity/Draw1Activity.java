package com.hnb.template.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;
import com.hnb.template.custom.CustomMaskView;

/**
 * Created by USER on 8/12/2015.
 */
public class Draw1Activity extends BaseActivity
{
    ImageView imageView;
    CustomMaskView mask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw1);
        initView();


    }

    private void initView()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
        mask = (CustomMaskView) findViewById(R.id.mask);
    }


}
