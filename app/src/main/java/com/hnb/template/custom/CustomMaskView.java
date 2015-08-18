package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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
    Context context;

    public void setSrcBitmap(Bitmap bitmap)
    {
        this.srcBitmap = bitmap;
        X = srcBitmap.getWidth() / 2;
        Y = srcBitmap.getHeight() / 2;
        init();
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


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;


        shapeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart3, options);
        // shapeBitmap1 = Bitmap.createBitmap(shapeBitmap.getWidth(), shapeBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // shapeCanvas = new Canvas(shapeBitmap1);
        // shapeCanvas.drawBitmap(shapeBitmap, 0, 0, new Paint());

    }

    public CustomMaskView(Context context)
    {
        super(context);
        this.context = context;
        //init();

    }

    public CustomMaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        //init();

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //int aaa = canvas.getWidth();
        //int bbb = srcBitmap.getWidth();

        temp.drawBitmap(srcBitmap, 0, 0, paint);

        //temp.drawCircle(X, Y, 400, transparentPaint);

        temp.drawBitmap(shapeBitmap, X - shapeBitmap.getWidth() / 2, Y - shapeBitmap.getHeight() / 2, paint);

        Bitmap result = replaceColor(drawBitmap, Color.WHITE, Color.TRANSPARENT);

       /* Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));*/

        Bitmap bmp2 = blur(this.context, result);
        canvas.drawBitmap(bmp2, 0, 0, paint);

    }


    public Bitmap replaceColor(Bitmap src, int fromColor, int toColor)
    {
        if (src == null)
        {
            return null;
        }
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        //get pixels
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int x = 0; x < pixels.length; ++x)
        {
            pixels[x] = (pixels[x] == fromColor) ? toColor : pixels[x];
        }
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        //set pixels
        result.setPixels(pixels, 0, width, 0, 0, width, height);

        return result;
    }

    private static final float BITMAP_SCALE = 1f;
    private static final float BLUR_RADIUS = 7.5f;

    public static Bitmap blur(Context context, Bitmap image)
    {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
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

