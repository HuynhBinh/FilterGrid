package com.hnb.template.dao;

import android.content.Context;

import com.hnb.template.application.MyApplication;

import java.util.List;

import greendao.Customer;
import greendao.CustomerDao;

/**
 * Created by USER on 7/10/2015.
 */
public class SampleController
{

    private static CustomerDao getCommentDao(Context c)
    {
        return ((MyApplication) c.getApplicationContext()).getDaoSession().getCustomerDao();
    }

    public static void insert(Context context, Customer customer)
    {

        getCommentDao(context).insert(customer);
    }

    public static void update(Context context, Customer customer)
    {

        getCommentDao(context).update(customer);
    }

    public static List<Customer> getAll(Context context)
    {
        return getCommentDao(context).loadAll();
    }

    public static void deleteAll(Context context)
    {
        getCommentDao(context).deleteAll();
    }
}
