package com.hnb.template.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnb.template.R;
import com.hnb.template.api.APIConst;
import com.hnb.template.api.APIService;
import com.hnb.template.base.BaseActivity;

/**
 * Created by USER on 8/7/2015.
 */
public class HomeActivity extends BaseActivity
{

    CoordinatorLayout root;
    Button btn;

    private BroadcastReceiver activityReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equalsIgnoreCase(APIConst.RECEIVER_FINISH_APPLY_VOUCHER))
            {
                String result = intent.getStringExtra(APIConst.EXTRA_RESULT);

                if (result.equals(APIConst.RESULT_OK))
                {

                }
                else if (result.equals(APIConst.RESULT_FAIL))
                {

                }
                else if (result.equals(APIConst.RESULT_NO_INTERNET))
                {

                }
                hideCustomProgressDialog();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registerBroadcastReceiver();




        showSnackInfo(root, "Hello, this is a snack!", 5000);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSnackError(root, "Hello, this is a snack!", 3000);
            }
        });


        //callServiceToLoadSomeData();
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unRegisterBroadcastReceiver();
    }

    private void initView()
    {
        root = (CoordinatorLayout) findViewById(R.id.snackbarPosition);
        btn = (Button)findViewById(R.id.btn);

    }


    private void callServiceToLoadSomeData()
    {
        showCustomProgressDialog();

        Intent intent = new Intent(APIConst.ACTION_APPLY_VOUCHER, null, HomeActivity.this, APIService.class);
        intent.putExtra(APIConst.EXTRA_USERNAME, "username");
        intent.putExtra(APIConst.EXTRA_PASSWORD, "pass");
        startService(intent);
    }

    private void registerBroadcastReceiver()
    {
        if (activityReceiver != null)
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(APIConst.RECEIVER_FINISH_APPLY_VOUCHER);
            // this is for online offline status
            //intentFilter.addAction(NetworkChangeReceiver.ON_NETWORK_CHANGE);
            registerReceiver(activityReceiver, intentFilter);
        }
    }

    private void unRegisterBroadcastReceiver()
    {
        if (activityReceiver != null)
        {
            unregisterReceiver(activityReceiver);
        }
    }

}
