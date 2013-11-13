package com.bnx.subsa.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bnx.subsa.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SAAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> data;
    private Context context;

    public SAAdapter(Context c, ArrayList<HashMap<String, Object>> d) {
        context = c;
        data = d;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    private class ViewHolder {
        // TextView tvName;
        TextView tvStart;
        TextView tvEnd;
        CheckBox cbSW;
    }
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (arg1 == null) {
            arg1 = LayoutInflater.from(context).inflate(R.layout.sa_item, null);

            holder = new ViewHolder();
            holder.cbSW = (CheckBox) arg1.findViewById(R.id.cb_name);
            holder.tvStart = (TextView) arg1.findViewById(R.id.tv_start);
            holder.tvEnd = (TextView) arg1.findViewById(R.id.tv_end);

            arg1.setTag(holder);

        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        holder.cbSW.setChecked((Boolean) data.get(arg0).get(Const.SP_KEY_CHECK));
        holder.cbSW.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_NAME));
        holder.tvStart.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_START));
        holder.tvEnd.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_END));
        return arg1;
    }

}
