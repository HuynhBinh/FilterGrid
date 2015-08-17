package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
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
    Bitmap srcBitmap;
    Bitmap shapeBitmap;

    private Canvas temp;
    Bitmap drawBitmap;
    Paint paint = new Paint();
    Paint transparentPaint = new Paint();

    public void setSrcBitmap(Bitmap bitmap)
    {
        this.srcBitmap = bitmap;
        X  = srcBitmap.getWidth()/2;
        Y = srcBitmap.getHeight()/2;
        init();
    }

    public CustomMaskView(Context context)
    {
        super(context);
        //init();

    }

    public CustomMaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //init();

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //int aaa = canvas.getWidth();
        //int bbb = srcBitmap.getWidth();

        temp.drawBitmap(srcBitmap, 0, 0, paint);

        //temp.drawCircle(X, Y, 200, transparentPaint);

        {
            Bitmap bmp = Bitmap.createBitmap(shapeBitmap.getWidth(), shapeBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bmp);
            canvas1.draw
        }

        temp.drawBitmap(shapeBitmap, X/2, Y/2, transparentPaint);


        canvas.drawBitmap(drawBitmap, 0, 0, new Paint());


    }

    public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        //get pixels
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
        }
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        //set pixels
        result.setPixels(pixels, 0, width, 0, 0, width, height);

        return result;
    }


    private void init()
    {
        //srcBitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
        //bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask2);
        //srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample);
        drawBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        temp = new Canvas(drawBitmap);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        shapeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask2);
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
                break;

            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Y = event.getY();
                invalidate();
                break;

        }

        return true;

    }


}

