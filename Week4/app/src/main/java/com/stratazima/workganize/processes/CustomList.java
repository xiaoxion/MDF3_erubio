package com.stratazima.workganize.processes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stratazima.workganize.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by esaurubio on 7/31/14.
 */

public class CustomList extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<HashMap<String, String>> daArrayList;

    public CustomList(Activity context, String[] length, ArrayList<HashMap<String, String>> daArrayList) {
        super(context, R.layout.item_work_view, length);

        this.context = context;
        this.daArrayList = daArrayList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_work_view, null, true);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView location = (TextView) rowView.findViewById(R.id.location);
        TextView type = (TextView) rowView.findViewById(R.id.type);
        TextView date = (TextView) rowView.findViewById(R.id.date);

        name.setText(daArrayList.get(position).get("name"));
        location.setText(daArrayList.get(position).get("location"));
        type.setText(daArrayList.get(position).get("type"));
        date.setText(daArrayList.get(position).get("date"));

        return rowView;
    }
}