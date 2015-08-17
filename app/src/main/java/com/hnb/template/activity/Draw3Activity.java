package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;
import com.hnb.template.custom.CustomMaskView;

/**
 * Created by USER on 8/17/2015.
 */
public class Draw3Activity extends BaseActivity
{
    ImageView imageView;
    CustomMaskView mask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw3);
        initView();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
        Bitmap gray = toGrayscale(bitmap);
        imageView.setImageBitmap(gray);
        bitmap.recycle();

    }

    private void initView()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
        mask = (CustomMaskView) findViewById(R.id.mask);
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
}
