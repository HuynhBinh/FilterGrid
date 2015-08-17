package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    Bitmap bmOut;
    Bitmap srcBitmap;

    private Canvas temp;
    Bitmap drawBitmap;


    public CustomMaskView(Context context)
    {
        super(context);
        //bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask2);
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample);
        drawBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    }

    public CustomMaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask2);
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample);
        drawBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        temp = new Canvas(drawBitmap);

        Paint paint = new Paint();
        //paint.setColor(0xcc000000);

        //temp.drawRect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight(), paint);
        temp.drawBitmap(srcBitmap, 0, 0, paint);


        Paint transparentPaint = new Paint();
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(X, Y, 200, transparentPaint);

        canvas.drawBitmap(drawBitmap, 0, 0, new Paint());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getActionMasked();

        switch (action)
        {

            case MotionEvent.ACTION_DOWN:
                //X = event.getX();
                //Y = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Y = event.getY();
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                break;


        }

        return super.onTouchEvent(event);

    }


}

