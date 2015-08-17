package com.hnb.template.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.hnb.template.R;

public class TouchImageView extends ImageView
{

    // SWIPE //
    float x;

    float y;

    float xx;

    float yy;

    float diffX;

    float diffY;

    // SWIPE //

    // com.gdroid.chandaitv.TouchImageView
    Matrix matrix;
    Matrix savedMatrix;

    // We can be in one of these 3 states
    static final int NONE = 0;

    static final int DRAG = 1;

    static final int ZOOM = 2;

    static final int SWIP = 3;

    int mode = NONE;

    // Remember some things for zooming
    PointF last = new PointF();

    PointF start = new PointF();

    float minScale = 1f;

    float maxScale = 3f;

    float[] m;

    int viewWidth, viewHeight;

    static final int CLICK = 3;

    float saveScale = 1f;

    protected float origWidth, origHeight;

    int oldMeasuredWidth, oldMeasuredHeight;

    ScaleGestureDetector mScaleDetector;

    Context context;

    Bitmap bmOut = null;


    public interface DoubleTapListener
    {

        void onDoubleTap();

        void onTap();

    }

    //private DoubleTapListener mDoubleTapListener;

    /*public void setOnDoubleTapListener(DoubleTapListener listener)
    {

        mDoubleTapListener = listener;
    }*/

    // ////////////////////////

    public interface LongPressListener
    {

        void onLongPress();

    }

    private LongPressListener mLongPressListener;

    public void setOnLongPressListener(LongPressListener listener)
    {

        mLongPressListener = listener;
    }

    // ////////////////////////////////////
    GestureDetector gestureDetector;

    public TouchImageView(Context context)
    {

        super(context);
        gestureDetector = new GestureDetector(context, new GestureListener());
        sharedConstructing(context);
        bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask1);
    }

    public TouchImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
        sharedConstructing(context);
        bmOut = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask1);
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {

        @Override
        public boolean onDown(MotionEvent e)
        {

            //mDoubleTapListener.onTap();
            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent event)
        {

            //mDoubleTapListener.onDoubleTap();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {

            mLongPressListener.onLongPress();
            super.onLongPress(e);
        }

    }

    private void sharedConstructing(Context context)
    {
        super.setClickable(true);
        this.context = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        matrix = new Matrix();
        savedMatrix = new Matrix();
        m = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                mScaleDetector.onTouchEvent(event);
                PointF curr = new PointF(event.getX(), event.getY());

                switch (event.getAction() & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        last.set(curr);
                        start.set(last);

                        x = event.getX();
                        y = event.getY();
                        mode = SWIP;
                        savedMatrix.set(matrix);
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG)
                        {
                            float deltaX = curr.x - last.x;
                            float deltaY = curr.y - last.y;
                            float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth * saveScale);
                            float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight * saveScale);
                            matrix.postTranslate(fixTransX, fixTransY);
                            fixTrans();
                            last.set(curr.x, curr.y);
                        }
                        else if (mode == SWIP)
                        {
                            x = event.getX();
                            y = event.getY();

                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        if (mode == DRAG)
                        {
                            int xDiff = (int) Math.abs(curr.x - start.x);
                            int yDiff = (int) Math.abs(curr.y - start.y);
                            if (xDiff < CLICK && yDiff < CLICK) performClick();
                        }
                        else if (mode == SWIP)
                        {
                            // set saveScale = 1 to restore the next picture to
                            // default scale
                            //saveScale = 1f;
                            // set saveScale = 1 to restore the next picture to
                            // default scale

                            //xx = event.getX();
                            //yy = event.getY();
                            //diffX = xx - x;
                            //diffY = yy - y;

                        }

                        mode = NONE;
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                }

                setImageMatrix(matrix);
                invalidate();
                return gestureDetector.onTouchEvent(event);
                // return true; // indicate event was handled
            }

        });
    }

    public void setMaxZoom(float x)
    {

        maxScale = x;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector)
        {

            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {

            float mScaleFactor = detector.getScaleFactor();
            float origScale = saveScale;
            saveScale *= mScaleFactor;
            if (saveScale > maxScale)
            {
                saveScale = maxScale;
                mScaleFactor = maxScale / origScale;
            }
            else if (saveScale < minScale)
            {
                saveScale = minScale;
                mScaleFactor = minScale / origScale;
            }

            if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight)
                matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
            else
                matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());

            fixTrans();
            return true;
        }
    }

    void fixTrans()
    {

        matrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];

        float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
        float fixTransY = getFixTrans(transY, viewHeight, origHeight * saveScale);

        if (fixTransX != 0 || fixTransY != 0) matrix.postTranslate(fixTransX, fixTransY);
    }

    float getFixTrans(float trans, float viewSize, float contentSize)
    {

        float minTrans, maxTrans;

        if (contentSize <= viewSize)
        {
            minTrans = 0;
            maxTrans = viewSize - contentSize;
        }
        else
        {
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if (trans < minTrans) return -trans + minTrans;
        if (trans > maxTrans) return -trans + maxTrans;
        return 0;
    }

    float getFixDragTrans(float delta, float viewSize, float contentSize)
    {

        if (contentSize <= viewSize)
        {
            return 0;
        }
        return delta;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        //
        // Rescales image on rotation
        //
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight || viewWidth == 0 || viewHeight == 0)
            return;
        oldMeasuredHeight = viewHeight;
        oldMeasuredWidth = viewWidth;

        if (saveScale == 1)
        {
            // Fit to screen.
            float scale;

            Drawable drawable = getDrawable();
            if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0)
                return;
            int bmWidth = drawable.getIntrinsicWidth();
            int bmHeight = drawable.getIntrinsicHeight();

            Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

            float scaleX = (float) viewWidth / (float) bmWidth;
            float scaleY = (float) viewHeight / (float) bmHeight;
            scale = Math.min(scaleX, scaleY);
            matrix.setScale(scale, scale);

            // Center the image
            float redundantYSpace = (float) viewHeight - (scale * (float) bmHeight);
            float redundantXSpace = (float) viewWidth - (scale * (float) bmWidth);
            redundantYSpace /= (float) 2;
            redundantXSpace /= (float) 2;

            matrix.postTranslate(redundantXSpace, redundantYSpace);

            origWidth = viewWidth - 2 * redundantXSpace;
            origHeight = viewHeight - 2 * redundantYSpace;
            setImageMatrix(matrix);
        }
        fixTrans();
    }

}
