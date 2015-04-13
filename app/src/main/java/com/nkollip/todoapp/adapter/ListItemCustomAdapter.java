package com.nkollip.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nkollip.todoapp.R;

import java.util.List;

/*
 * Adapter to show star image and item name as list item
 * This is used by <code>TodoActivity</code>
 *
 * Created by nkollip on 4/8/2015.
 */
public class ListItemCustomAdapter extends ArrayAdapter<String>{

    public ListItemCustomAdapter(Context context, List<String> items) {
        super(context, R.layout.layout_custom, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.layout_custom, parent, false);
        String item = getItem(position);
        TextView tvCustom = (TextView) customView.findViewById(R.id.tvCustom);
        tvCustom.setText(item);
        return customView;
    }
}
