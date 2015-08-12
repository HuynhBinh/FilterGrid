package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.hnb.template.R;

/**
 * Created by USER on 8/12/2015.
 */
public class CustomMaskView extends ImageView
{

    public float X = 0;
    public float Y = 0;
    public float radius = 300;

    Bitmap bmOut = null;


    public CustomMaskView(Context context)
    {
        super(context);
        bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask1);
    }

    public CustomMaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask1);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //Bitmap bmOut = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        //paint.setColor(Color.GREEN);
        //paint.setStyle(Paint.Style.STROKE);
        // paint.setAlpha(100);
        //canvas.drawCircle(X, Y, radius, paint);

        if (bmOut != null) canvas.drawBitmap(bmOut, X, Y, paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // do something
                break;
            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Y = event.getY();
                invalidate();
                // do something
                break;
            case MotionEvent.ACTION_UP:
                //do something
                break;
        }
        return true;

    }
}
