package com.hnb.template.utils;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import com.hnb.template.model.Circle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HuynhBinh on 8/11/15.
 */
public class ColorFilterUtils
{

    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

    public static Bitmap applyBlackFilter(Bitmap source)
    {
        // get image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 0;
        // iteration through pixels
        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                // get current index in 2D-matrix
                index = y * width + x;
                // get color
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(COLOR_MAX);
                if (R < thresHold && G < thresHold && B < thresHold)
                {
                    pixels[index] = Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
                }
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }

    public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // apply grayscale sample
                B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepid-toning on each channel
                R += (depth * red);
                if (R > 255)
                {
                    R = 255;
                }

                G += (depth * green);
                if (G > 255)
                {
                    G = 255;
                }

                B += (depth * blue);
                if (B > 255)
                {
                    B = 255;
                }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }


    public static Bitmap doColorFilterTest1(Bitmap src)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;


        // scan through all pixels
        for (int x = 0; x < width; ++x)
        {
            int h = height - x;
            for (int y = 0; y < h; ++y)
            {


                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * 0.7);
                G = (int) (Color.green(pixel) * 0.5);
                B = (int) (Color.blue(pixel) * 0.5);

                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        for (int x = (width - 1); x > 0; --x)
        {
            int h = height--;
            for (int y = 0; y < h; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * 1);
                G = (int) (Color.green(pixel) * 1);
                B = (int) (Color.blue(pixel) * 0.5);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }


        height = src.getHeight();
        int hh = height - 1;
        for (int x = 0; x < width; ++x)
        {

            if (x >= (width / 2))
            {
                hh = hh + 1;
                for (int y = (hh - 1); y < height; ++y)
                {


                    // get pixel color
                    pixel = src.getPixel(x, y);
                    // apply filtering on each channel R, G, B
                    A = Color.alpha(pixel);
                    R = (int) (Color.red(pixel) * 0.1);
                    G = (int) (Color.green(pixel) * 1);
                    B = (int) (Color.blue(pixel) * 1);

                    // set new color pixel to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }

            }
            else
            {
                hh = height - x;
                for (int y = height - 1; y > (hh - 1); --y)
                {


                    // get pixel color
                    pixel = src.getPixel(x, y);
                    // apply filtering on each channel R, G, B
                    A = Color.alpha(pixel);
                    R = (int) (Color.red(pixel) * 0.1);
                    G = (int) (Color.green(pixel) * 1);
                    B = (int) (Color.blue(pixel) * 1);

                    // set new color pixel to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }


        // return final image
        return bmOut;


    }


    public static int distance(int x1, int y1, int x2, int y2)
    {
        return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    public static Bitmap doColorFilterCircle(Bitmap src)
    {

        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        List<Circle> circleList = new ArrayList<>();


        Circle circle00 = new Circle();
        circle00.X = width / 2;
        circle00.Y = height / 2;
        circle00.radius = (width);
        circle00.red = 0.1;
        circle00.green = 0.5;
        circle00.blue = 1;

        circleList.add(circle00);


        Circle circle1 = new Circle();
        circle1.X = width / 2;
        circle1.Y = height / 2;
        circle1.radius = (width / 2) - 70;
        circle1.red = 1;
        circle1.green = 1;
        circle1.blue = 0.5;

        circleList.add(circle1);


        Circle circle2 = new Circle();
        circle2.X = (int) (width / 4);
        circle2.Y = (int) (height / 4);
        circle2.radius = (width / 3) - 100;
        circle2.red = 0.5;
        circle2.green = 0.1;
        circle2.blue = 1;
        circleList.add(circle2);

        Circle circle0 = new Circle();
        circle0.X = (int) (width / 1.25);
        circle0.Y = (int) (height / 1.25);
        circle0.radius = (width / 3) - 100;
        circle0.red = 0.8;
        circle0.green = 0.8;
        circle0.blue = 0.25;
        circleList.add(circle0);


       /* Circle circle3 = new Circle();
        circle3.X = width / 4;
        circle3.Y = height / 4;
        circle3.radius = (width / 4) - 50;
        circle3.red = 0.5;
        circle3.green = 1;
        circle3.blue = 0.5;
        circleList.add(circle3);


        Circle circle4 = new Circle();
        circle4.X = width / 5;
        circle4.Y = height / 5;
        circle4.radius = (width / 5) - 10;
        circle4.red = 1;
        circle4.green = 1;
        circle4.blue = 1;
        circleList.add(circle4);*/

        for (Circle circle : circleList)
        {

            // scan through all pixels
            for (int x = 0; x < width; ++x)
            {
                for (int y = 0; y < height; ++y)
                {

                    int distance = distance(x, y, circle.X, circle.Y);
                    if (distance >= circle.radius)
                    {
                       /* // get pixel color
                        pixel = src.getPixel(x, y);
                        // apply filtering on each channel R, G, B
                        A = Color.alpha(pixel);
                        R = (int) (Color.red(pixel));
                        G = (int) (Color.green(pixel));
                        B = (int) (Color.blue(pixel));

                        // set new color pixel to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));*/

                    }
                    else
                    {
                        // get pixel color
                        pixel = src.getPixel(x, y);
                        // apply filtering on each channel R, G, B
                        A = Color.alpha(pixel);
                        R = (int) (Color.red(pixel) * circle.red);
                        G = (int) (Color.green(pixel) * circle.green);
                        B = (int) (Color.blue(pixel) * circle.blue);

                        // set new color pixel to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }


                }
            }
        }
        // return final image
        return bmOut;


    }


    public static Bitmap doColorFilterCircle(Bitmap src, int circleX, int circleY, int radius)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;


        circleX = width / 2;
        circleY = height / 2;
        radius = (width / 2) - 150;

        /*int width1 = width / 6;
        int width2 = (width * 3) / 6;
        int width3 = width;
        int width4 = width;*/


        // scan through all pixels
        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                int distance = distance(x, y, circleX, circleY);
                if (distance >= radius)
                {
                    // get pixel color
                    pixel = src.getPixel(x, y);
                    // apply filtering on each channel R, G, B
                    A = Color.alpha(pixel);
                    R = (int) (Color.red(pixel) * 0.1);
                    G = (int) (Color.green(pixel) * 1);
                    B = (int) (Color.blue(pixel) * 1);

                    // set new color pixel to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));

                }
                else
                {
                    // get pixel color
                    pixel = src.getPixel(x, y);
                    // apply filtering on each channel R, G, B
                    A = Color.alpha(pixel);
                    R = (int) (Color.red(pixel) * 1);
                    G = (int) (Color.green(pixel) * 1);
                    B = (int) (Color.blue(pixel) * 0.5);

                    // set new color pixel to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }


            }
        }
        // return final image
        return bmOut;
    }


    public static Bitmap doColorFilterTest(Bitmap src)
    {


        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        int width1 = width / 6;
        int width2 = (width * 3) / 6;
        int width3 = width;
        int width4 = width;


        // scan through all pixels
        for (int x = 0; x < width1; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * 0.7);
                G = (int) (Color.green(pixel) * 0.3);
                B = (int) (Color.blue(pixel) * 0.7);

                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        for (int x = width1; x < width2; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * 0.5);
                G = (int) (Color.green(pixel) * 0.5);
                B = (int) (Color.blue(pixel) * 1);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }


        for (int x = width2; x < width3; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * 1);
                G = (int) (Color.green(pixel) * 1);
                B = (int) (Color.blue(pixel) * 0.5);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;


    }

    public static Bitmap bright(Bitmap bmp)
    {
        Bitmap operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++)
        {
            for (int j = 0; j < bmp.getHeight(); j++)
            {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 100 + r;
                g = 100 + g;
                b = 100 + b;
                alpha = 100 + alpha;
                operation.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        //im.setImageBitmap(operation);
        return operation;
    }


    public static Bitmap doColorFilter(Bitmap src, double red, double green, double blue)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B

                Color.red(pixel);

                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * red);
                G = (int) (Color.green(pixel) * green);
                B = (int) (Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    private Bitmap invertImage(Bitmap scrBitmap)
    {
        WeakReference<Bitmap> mViewReference;

        mViewReference = new WeakReference<Bitmap>(scrBitmap);

        Bitmap finalBitmap = Bitmap.createBitmap(scrBitmap.getWidth(), scrBitmap.getHeight(), scrBitmap.getConfig());

        int pixelColor;

        int A, R, B, G;

        int height = scrBitmap.getHeight();
        int width = scrBitmap.getWidth();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                pixelColor = scrBitmap.getPixel(x, y);

                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);

                finalBitmap.setPixel(x, y, Color.argb(A, R, G, B));

            }
        }

        return finalBitmap;

    }

    public static Bitmap grayScale(Bitmap scrBitmap)
    {

        float GSRed = 0.30f;
        float GSGreen = 0.59f;
        float GSBlue = 0.11f;

        Bitmap finalBitmap = Bitmap.createBitmap(scrBitmap.getWidth(), scrBitmap.getHeight(), scrBitmap.getConfig());

        int pixelColor;

        int A, R, B, G;

        int height = scrBitmap.getHeight();
        int width = scrBitmap.getWidth();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                pixelColor = scrBitmap.getPixel(x, y);

                A = Color.alpha(pixelColor);
                R = Color.red(pixelColor);
                G = Color.green(pixelColor);
                B = Color.blue(pixelColor);


                R = G = B = (int) (R * GSRed + G * GSGreen + B * GSBlue);

                finalBitmap.setPixel(x, y, Color.argb(A, R, G, B));

            }
        }

        return finalBitmap;
    }

    private Bitmap doHighlightImage(Bitmap src)
    {
        // create new bitmap, which will be painted and becomes result image
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth() + 96, src.getHeight() + 96, Bitmap.Config.ARGB_8888);
        // setup canvas for painting
        Canvas canvas = new Canvas(bmOut);
        // setup default color
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // create a blur paint for capturing alpha
        Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY = new int[2];
        // capture alpha into a bitmap
        Bitmap bmAlpha = src.extractAlpha(ptBlur, offsetXY);
        // create a color paint
        Paint ptAlphaColor = new Paint();
        ptAlphaColor.setColor(0xFFFFFFFF);
        // paint color for captured alpha region (bitmap)
        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
        // free memory
        bmAlpha.recycle();

        // paint the image source
        canvas.drawBitmap(src, 0, 0, null);

        // return out final image
        return bmOut;
    }


}
