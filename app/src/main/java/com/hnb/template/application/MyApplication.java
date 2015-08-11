package com.hnb.template.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.parse.Parse;
import com.parse.ParseInstallation;

import greendao.DaoMaster;
import greendao.DaoSession;


/**
 * Created by USER on 4/22/2015.
 */
public class MyApplication extends Application
{
    public DaoSession daoSession;


    @Override
    public void onCreate()
    {
        super.onCreate();
        setupImageLoader();
        setupParse();
        setupDatabase();

    }

    private void setupParse()
    {
        Parse.initialize(this, "LLbkTtbCo2D9l7nj95zxFI1kXnGLtJMEUQbW0nAv", "UPNEvStvyIG60QpZLE93AQqmF1qanaxgI8woY0ex");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    private void setupDatabase()
    {
        // check version delete all before create all
        int current_version = getAppVersion();
        SharedPreferences preferences = getSharedPreferences("versioncode", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int previous_version = preferences.getInt("version", current_version);
        if (previous_version == current_version)
        {
            editor.putInt("version", current_version);
        }
        else
        {
            deleteDatabase();
            editor.putInt("version", current_version);
        }
        editor.commit();

        // get DAO session
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "diblydb", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    private void setupImageLoader()
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).memoryCache(new WeakMemoryCache()).tasksProcessingOrder(QueueProcessingType.LIFO)
                //.imageDecoder(new NutraBaseImageDecoder(true))
                // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    public DaoSession getDaoSession()
    {
        if (daoSession != null)
        {
            return daoSession;
        }
        else
        {
            setupDatabase();
            return daoSession;
        }
    }

    private void deleteDatabase()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "diblydb", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoMaster.dropAllTables(db, true);
        daoMaster.createAllTables(db, true);
    }

    public int getAppVersion()
    {
        try
        {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void clearDaoSession()
    {
        daoSession = null;
    }
}
