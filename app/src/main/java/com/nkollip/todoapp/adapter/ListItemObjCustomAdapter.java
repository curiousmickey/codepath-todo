package com.nkollip.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nkollip.todoapp.R;
import com.nkollip.todoapp.model.Item;

import java.util.List;

/**
 * Adapter to show star image and item name as list item
 * This is used by <code>TodoUsingSQLiteActivity</code>
 * Created by nkollip on 4/8/2015.
 */
public class ListItemObjCustomAdapter extends ArrayAdapter<Item>{

    public ListItemObjCustomAdapter(Context context, List<Item> items) {
        super(context, R.layout.layout_custom, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.layout_custom, parent, false);
        Item item = getItem(position);
        TextView tvCustom = (TextView) customView.findViewById(R.id.tvCustom);
        tvCustom.setText(item.getItemName());
        return customView;
    }
}
