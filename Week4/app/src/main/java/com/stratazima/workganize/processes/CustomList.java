package com.stratazima.workganize.processes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.stratazima.workganize.R;

import java.util.HashMap;

/**
 * Created by esaurubio on 7/31/14.
 */

public class CustomList extends ArrayAdapter {
    private final Activity context;
    private final HashMap<String, String> daList;
    private final LayoutInflater inflater;

    public CustomList(Activity context, String[] key , HashMap<String, String> daList) {
        super(context, R.layout.item_work_view, key);

        this.context = context;
        this.daList = daList;
        inflater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;

        if (rowView == null) rowView = inflater.inflate(R.layout.item_work_view, null, true);

//        TextView mainTitle = (TextView) rowView.findViewById(R.id.health_rating);
//
//        mainTitle.setText(onGetMainText(position));

        return rowView;
    }
}