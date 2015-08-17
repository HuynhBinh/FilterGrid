package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;

/**
 * Created by USER on 8/14/2015.
 */
public class Draw2Activity extends BaseActivity
{
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw2);
        initView();
    }

    private void initView()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setColorFilter(ColorFilterGenerator.adjustHue(1));
    }


    private void set()
    {
        // The matrix is stored in a single array, and its treated as follows: [ a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t ]
        // When applied to a color [r, g, b, a], the resulting color is computed as (after clamping) ;
        //   R' = a*R + b*G + c*B + d*A + e;
        //   G' = f*R + g*G + h*B + i*A + j;
        //   B' = k*R + l*G + m*B + n*A + o;
        //   A' = p*R + q*G + r*B + s*A + t;

        Paint paint = new Paint();
        float[] matrix = {1, 1, 1, 1, 1, //red
                0, 0, 0, 0, 0, //green
                0, 0, 0, 0, 0, //blue
                1, 1, 1, 1, 1 //alpha
        };
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));

        Rect source = new Rect(0, 0, 100, 100);
        Rect dest = new Rect(0, 0, 100, 100);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sample);
        Canvas canvas = new Canvas();
        canvas.drawBitmap(bitmap, source, dest, paint);
    }

}
