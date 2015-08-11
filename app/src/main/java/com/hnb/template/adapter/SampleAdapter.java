package com.hnb.template.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hnb.template.R;
import com.hnb.template.utils.StaticFunction;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;


/**
 * Created by USER on 7/3/2015.
 */
public class SampleAdapter extends BaseAdapter
{
    private List<Object> listDiscovery;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ViewHolder holder;

    private DisplayImageOptions options;

    public SampleAdapter(Activity activity, List<Object> data)
    {
        this.listDiscovery = data;
        this.layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;

        initImageLoaderOption();
    }

    public void setDataSource(List<Object> data)
    {
        this.listDiscovery = data;
        this.notifyDataSetChanged();
    }

    public void notifyDataChange()
    {
        notifyDataSetChanged();
    }

    public void initImageLoaderOption()
    {
        options = StaticFunction.getImageLoaderOptions();
    }

    @Override
    public int getCount()
    {
        int count = (listDiscovery == null) ? 0 : listDiscovery.size();

        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return listDiscovery.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = this.layoutInflater.inflate(R.layout.row_sample, null);
            holder = new ViewHolder();

            holder.lnlRoot = (LinearLayout) convertView.findViewById(R.id.lnlRoot);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.imageViewDiscovery = (ImageView) convertView.findViewById(R.id.imageDiscovery);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        /* To Do: */

        return convertView;
    }

    public class ViewHolder
    {
        LinearLayout lnlRoot;
        TextView txtName;
        ImageView imageViewDiscovery;
    }
}
