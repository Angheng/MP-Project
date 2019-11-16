/*
 * Author: 20181683 임중혁
 * Last Modification date: 19.11.16
 * Function: Pub item List's Adapter
 */


package com.bungae1112.final_proj.mainActivity.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bungae1112.final_proj.R;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter
{

    Context mContext = null;
    LayoutInflater inflater = null;
    ArrayList<ItemData> items;

    public ItemAdapter(Context context, ArrayList<ItemData> items)
    {
        mContext = context;
        this.items = items;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemData getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if ( convertView == null )
            convertView = inflater.inflate( R.layout.list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.item_imageView_iv);
        TextView nameView = convertView.findViewById(R.id.item_name_tv);
        TextView categoryView = convertView.findViewById(R.id.item_categoty_tv);
        ProgressBar progressBar = convertView.findViewById(R.id.item_progressBar_pb);
        TextView remainView = convertView.findViewById(R.id.item_remain_tv);

        nameView.setText( items.get(position).getName() );
        categoryView.setText( items.get(position).getCategory() );
        progressBar.setMax( Integer.parseInt( items.get(position).getMax() ) );
        progressBar.setProgress( Integer.parseInt( items.get(position).getRemain() ) );
        remainView.setText( items.get(position).getRemain() + " / " + items.get(position).getMax() );

        return convertView;
    }
}
