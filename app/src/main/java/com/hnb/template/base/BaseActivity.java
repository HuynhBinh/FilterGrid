package com.hnb.template.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hnb.template.R;

import java.util.List;


/**
 * Created by USER on 6/29/2015.
 */
public class BaseActivity extends AppCompatActivity
{

    private Dialog customProgressDialog = null;
    private ProgressDialog progressDialog = null;

    protected AdView adView;
    private InterstitialAd interstitial;
    protected AdRequest adRequest;

    public static final String ADMOB_BANNER_TOKEN = "";
    public static final String ADMOB_FULL_SCREEN_TOKEN = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void showSnackInfo(View root, String info, int length)
    {
        Snackbar snackbar;

        snackbar = Snackbar.make(root, info, Snackbar.LENGTH_LONG);
        snackbar.setDuration(length);

        /*snackbar.setAction("Dismiss", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            }
        });*/

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLUE);

        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        //snackbar.setActionTextColor(Color.RED);
        snackbar.show();

    }

    public void showSnackError(View root, String info, int length)
    {
        Snackbar snackbar;

        snackbar = Snackbar.make(root, info, Snackbar.LENGTH_LONG);
        snackbar.setDuration(length);
        snackbar.setAction("Dismiss", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            }
        });

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);

        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        //snackbar.setActionTextColor(Color.RED);
        snackbar.show();

    }


    private void shareFacebook(String urlToShare)
    {
        urlToShare = urlToShare;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches)
        {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana"))
            {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound)
        {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    private void shareTwitter(String urlToShare)
    {
        urlToShare = urlToShare;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches)
        {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter.android"))
            {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound)
        {
            String sharerUrl = "https://twitter.com/intent/tweet?text=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }


    public void initAdmob()
    {
        adRequest = new AdRequest.Builder().build();
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(ADMOB_BANNER_TOKEN);

        LinearLayout layout = (LinearLayout) findViewById(R.id.lnlAdmob);

        layout.addView(adView);
        adView.loadAd(adRequest);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ADMOB_FULL_SCREEN_TOKEN);

        interstitial.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded()
            {

                super.onAdLoaded();
                if (interstitial.isLoaded())
                {
                    interstitial.show();
                }
            }

            @Override
            public void onAdClosed()
            {

                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode)
            {

                super.onAdFailedToLoad(errorCode);
            }

        });
    }

    protected void iniIntershial()
    {
        adRequest = new AdRequest.Builder().build();
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ADMOB_FULL_SCREEN_TOKEN);

        interstitial.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded()
            {

                super.onAdLoaded();
                if (interstitial.isLoaded())
                {
                    interstitial.show();
                }
            }

            @Override
            public void onAdClosed()
            {

                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode)
            {

                super.onAdFailedToLoad(errorCode);
            }

        });
    }

    protected void showFullBannerEdit(boolean isTurn)
    {
        if (isTurn)
        {
            SharedPreferences prefs = getSharedPreferences("shareprefer", MODE_PRIVATE);
            int count = prefs.getInt("isTurneditCount", 0);
            SharedPreferences.Editor editor = prefs.edit();
            if (count == 0)
            {
                //interstitial.loadAd(adRequest);
                editor.putBoolean("isTurnedit", false);
                editor.putInt("isTurneditCount", ++count);
                editor.commit();
            }
            else if (count == 6)
            {
                editor.putInt("isTurneditCount", 0);
                editor.commit();
            }
            else
            {
                editor.putInt("isTurneditCount", ++count);
                editor.putBoolean("isTurnedit", true);
                editor.commit();
            }
        }
        else
        {
            //interstitial.loadAd(adRequest);
        }
    }

    public void showProgressDialog()
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(BaseActivity.this);
        }

        if (!progressDialog.isShowing())
        {
            progressDialog.setMessage("Progressing ... ");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    public void hideProgressDialog()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        hideCustomProgressDialog();
    }


    public void showCustomProgressDialog()
    {
        if (customProgressDialog == null)
        {
            customProgressDialog = new Dialog(BaseActivity.this);
            customProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customProgressDialog.setCanceledOnTouchOutside(false);
            customProgressDialog.setCancelable(false);
            customProgressDialog.setContentView(R.layout.popup_custom_progress_dialog);
            customProgressDialog.show();
        }
    }


    public void hideCustomProgressDialog()
    {

        if (customProgressDialog != null)
        {
            if (customProgressDialog.isShowing())
            {
                customProgressDialog.dismiss();
            }
            customProgressDialog = null;
        }

    }


}
