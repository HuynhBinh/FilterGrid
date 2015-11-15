package com.hnb.template.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hnb.template.base.BaseActivity;

/**
 * Created by USER on 8/19/2015.
 */
public class EffectActivity extends BaseActivity
{

    private int[] mTextures = new int[2];
    private EffectContext mEffectContext;
    private Effect mEffect;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;


    final static String imageFileOut = "/data/local/out.png";
    final static String imageFileIn = "/data/local/lol.png";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    private int loadTextures()
    {
        // Generate textures
        GLES20.glGenTextures(2, mTextures, 0);

        // Load input bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imageFileIn);

        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();
        mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);

        // Upload to texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Set texture parameters
        GLToolbox.initTexParams();

        return mTextures[0];
    }
}
