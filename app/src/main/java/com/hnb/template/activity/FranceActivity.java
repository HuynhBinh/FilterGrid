package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.hnb.template.R;
import com.hnb.template.base.BaseActivity;
import com.hnb.template.utils.ColorFilterUtils;
import com.hnb.template.utils.StaticFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by HuynhBinh on 11/15/15.
 */
public class FranceActivity extends BaseActivity
{

    ImageView imageAvatar;
    //Button btnSave;
    FloatingActionButton btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_france);

        initView();


        // load image from sdcard
        File file = loadImageFromSdCard();

        // scale image to fit screen size

        Bitmap bitmap = decodeFileAndScaleImageFitScreenSize(file);


        //Test
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mia);
        // Bitmap scale = bitmap.createScaledBitmap(bitmap, 200, 200, true);
        //Bitmap gray = ColorFilterUtils.grayScale(bitmap);
        //Test

        // france color
        Bitmap resultBitmap = ColorFilterUtils.doColorFilterFrance(bitmap);


        // set image to imageview
        imageAvatar.setImageBitmap(resultBitmap);


    }

    private void initView()
    {
        imageAvatar = (ImageView) findViewById(R.id.imageAvatar);
        btnSave = (FloatingActionButton) findViewById(R.id.fab);
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Bitmap bitmap = convertDrawableToBitmap(imageAvatar.getDrawable(), imageAvatar.getWidth(), imageAvatar.getHeight());
                //StaticFunction.saveBitmapToSDCard("france_" + System.currentTimeMillis(), bitmap);
                Toast.makeText(FranceActivity.this, "Saved", Toast.LENGTH_LONG).show();
            }
        });
    }


    private Bitmap loadImageAndGreyscale()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mia);
        Bitmap gray = ColorFilterUtils.grayScale(bitmap);

        return gray;
    }

    private Bitmap decodeFileAndScaleImageFitScreenSize(File f)
    {
        try
        {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true)
            {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        }
        catch (FileNotFoundException e)
        {
        }
        return null;
    }

    private File loadImageFromSdCard()
    {
        File imgFile = new File("/sdcard/sample2.jpg");

        if (imgFile.exists())
        {

            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            return imgFile;


        }
        else
        {
            return null;
        }
    }

    private Bitmap convertDrawableToBitmap(Drawable drawable, int width, int height)
    {

        Drawable insideDrawable = drawable.getConstantState().newDrawable();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        insideDrawable.setBounds(0, 0, width, height);
        insideDrawable.draw(canvas);
        return bitmap;
    }


}
