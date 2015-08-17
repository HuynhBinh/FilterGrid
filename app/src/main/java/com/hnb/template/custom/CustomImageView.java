package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by USER on 8/12/2015.
 */
public class CustomImageView extends ImageView
{

    Bitmap srcBitmap;

    public void setSrcBitmap(Bitmap bitmap)
    {
        this.srcBitmap = bitmap;
    }

    public CustomImageView(Context context)
    {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(srcBitmap, 0, 0, new Paint());

    }


}

