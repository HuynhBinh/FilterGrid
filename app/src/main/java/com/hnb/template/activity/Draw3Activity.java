package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;
import com.hnb.template.custom.CustomImageView;
import com.hnb.template.custom.CustomMaskView;
import com.hnb.template.utils.ColorFilterUtils;

/**
 * Created by USER on 8/17/2015.
 */
public class Draw3Activity extends BaseActivity
{
    CustomImageView imageView;
    CustomMaskView mask;


    protected ImageView mOriginalImageView;
    protected ImageView mModifyImageView;

    protected Bitmap mFilterBitmap;

    private static final int HFACTOR_SEEKBAR_RESID = 21863;
    private static final int SFACTOR_SEEKBAR_RESID = 21864;
    private static final int BFACTOR_SEEKBAR_RESID = 21865;

    private int mHFactorValue = 150;
    private int mSFactorValue = 50;
    private int mBFactorValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw3);
        initView();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
        //Bitmap gray = toGrayscale(bitmap);
        //Bitmap yellow = ColorFilterUtils.doColorFilter(bitmap, 0, 1, 0.5);
        //Bitmap green = ColorFilterUtils.doColorFilter(bitmap, 1, 1, 0.5);
        //Bitmap lighter = ColorFilterUtils.bright(bitmap);
        imageView.setSrcBitmap(bitmap);
        mask.setSrcBitmap(bitmap);


        /*final int width = imageView.getDrawable().getIntrinsicWidth();
        final int height = imageView.getDrawable().getIntrinsicHeight();

        int[] colors = AndroidUtils.drawableToIntArray(imageView.getDrawable());

        HSBAdjustFilter filter = new HSBAdjustFilter();
        filter.setHFactor(getHFactor(mHFactorValue));
        filter.setSFactor(getSFactor(mSFactorValue));
        filter.setBFactor(getBFactor(mBFactorValue));
        colors = filter.filter(colors, width, height);

        setModifyView(colors, width, height);*/


    }

    private float getHFactor(int value)
    {
        float retValue = 0;
        retValue = (float) ((value - 100));
        return retValue;
    }

    private float getSFactor(int value)
    {
        float retValue = 0;
        retValue = (float) ((value - 100) / 100f);
        return retValue;
    }

    private float getBFactor(int value)
    {
        float retValue = 0;
        retValue = (float) ((value - 100) / 100f);
        return retValue;
    }

    protected void setModifyView(int[] colors, int width, int height)
    {
        imageView.setWillNotDraw(true);

        if (mFilterBitmap != null)
        {
            mFilterBitmap.recycle();
            mFilterBitmap = null;
        }

        mFilterBitmap = Bitmap.createBitmap(colors, 0, width, width, height, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(mFilterBitmap);

        imageView.setWillNotDraw(false);
        imageView.postInvalidate();
    }


    private void initView()
    {
        imageView = (CustomImageView) findViewById(R.id.imageView);
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
