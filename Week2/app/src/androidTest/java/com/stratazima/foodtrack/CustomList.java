package com.stratazima.foodtrack;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Esau on 7/16/2014.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final boolean isConnected;
    private final ArrayList<HashMap<String, String>> daArrayList;

    public CustomList(Activity context, String[] length, boolean isConnected, ArrayList<HashMap<String, String>> daArrayList) {
        super(context, R.layout.item_food, length);

        this.context = context;
        this.isConnected = isConnected;
        this.daArrayList = daArrayList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_food, null, true);

        TextView mainTitle = (TextView) rowView.findViewById(R.id.health_rating);
        TextView subTitle = (TextView) rowView.findViewById(R.id.plate_description);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.food_image);

        mainTitle.setText(daArrayList.get(position).get("username"));
        subTitle.setText(daArrayList.get(position).get("title"));
        return rowView;
    }
}
