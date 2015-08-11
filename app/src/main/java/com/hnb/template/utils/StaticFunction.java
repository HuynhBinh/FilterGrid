package com.hnb.template.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.DisplayMetrics;
import android.view.View;

import com.hnb.template.R;
import com.hnb.template.activity.HomeActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by USER on 4/22/2015.
 */
public class StaticFunction
{
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static ImageLoader getImageLoader()
    {
        return ImageLoader.getInstance();
    }


    public static DisplayImageOptions getImageLoaderOptions()
    {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.load_fail).cacheInMemory(true).cacheOnDisk(true).build();
        return options;
    }

    public static int dpToPx(Activity activity, int dp)
    {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private static void sendNotificationBigNoImage(final Context ctx, final int notifyId, final String title, final String subTitle, final String bigTitleforNewAPI)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx).setAutoCancel(true).setContentTitle(title).setSmallIcon(R.drawable.ic_launcher).setContentText(subTitle);

        NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
        bigPicStyle.setBigContentTitle(bigTitleforNewAPI);
        bigPicStyle.setSummaryText(subTitle);
        mBuilder.setStyle(bigPicStyle);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ctx, HomeActivity.class);

        // The stack builder object will contain an artificial back stack
        // for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out
        // of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);

        // Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(testActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    private static void sendNotificationBigWithImage(final Context ctx, final int notifyId, final String title, final String subTitle, final String bigTitleforNewAPI, Bitmap bitmap)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx).setAutoCancel(true).setContentTitle(title).setSmallIcon(R.drawable.ic_launcher).setContentText(subTitle);

        NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
        bigPicStyle.bigPicture(bitmap);
        bigPicStyle.setBigContentTitle(bigTitleforNewAPI);
        bigPicStyle.setSummaryText(subTitle);
        mBuilder.setStyle(bigPicStyle);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ctx, HomeActivity.class);

        // The stack builder object will contain an artificial back stack
        // for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out
        // of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);

        // Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(testActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    public static void sendNotificationBigStyle(final Context ctx, final int notifyId, final String title, final String subTitle, final String bigTitleforNewAPI, String imageURL)
    {
        if (imageURL.isEmpty() || imageURL == null)
        {
            sendNotificationBigNoImage(ctx, notifyId, title, subTitle, bigTitleforNewAPI);
        }
        else
        {
            ImageLoader.getInstance().loadImage(imageURL, new ImageLoadingListener()
            {
                @Override
                public void onLoadingStarted(String s, View view)
                {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason)
                {
                    sendNotificationBigNoImage(ctx, notifyId, title, subTitle, bigTitleforNewAPI);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    sendNotificationBigWithImage(ctx, notifyId, title, subTitle, bigTitleforNewAPI, bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view)
                {
                    sendNotificationBigNoImage(ctx, notifyId, title, subTitle, bigTitleforNewAPI);
                }
            });
        }


    }

    public static int getScreenHeight(Activity activity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getScreenWidth(Activity activity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static float getDensity(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static boolean checkFbInstalled(Activity activity)
    {
        PackageManager pm = activity.getPackageManager();
        boolean flag = false;
        try
        {
            pm.getPackageInfo("com.facebook.katana", PackageManager.GET_ACTIVITIES);
            flag = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            flag = false;
        }
        return flag;
    }

    public static void callPhone(Activity activity, String number)
    {
        String uri = "tel:" + number.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        activity.startActivity(intent);

    }

    public static void saveBitmapToSDCard(String imageName, Bitmap bitmap)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AppName");

        if (!myDir.exists())
        {
            myDir.mkdirs();
        }

        String fname = myDir + "/" + imageName + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(fname);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            /*if (!bitmap.isRecycled())
            {
                bitmap.recycle();
            }*/


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String isFileExist(String imageName)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AppName");

        if (!myDir.exists())
        {
            myDir.mkdirs();
        }

        String fname = imageName + ".png";
        File file = new File(myDir, fname);
        if (file.exists())
        {
            return file.getPath();
        }
        else
        {
            return "";
        }
    }
}
