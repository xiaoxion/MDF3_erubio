package com.stratazima.foodtrack;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Esau on 7/16/2014.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> daList;

    public CustomList(Activity context, ArrayList<String> daList) {
        super(context, R.layout.item_food, daList);

        this.context = context;
        this.daList = daList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView;

        if (daList.get(position).contains(".png")) {
            rowView = inflater.inflate(R.layout.item_food, null, true);

            TextView mainTitle = (TextView) rowView.findViewById(R.id.health_rating);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.food_image);

            mainTitle.setText(onGetMainText(position));
            imageView.setImageURI(Uri.parse(daList.get(position)));
        } else {
            rowView = inflater.inflate(R.layout.item_header, null, true);
            rowView.setEnabled(false);

            TextView mainTitle = (TextView) rowView.findViewById(R.id.headerTextView);
            mainTitle.setText(daList.get(position).toUpperCase());
        }

        return rowView;
    }

    private String onGetMainText(int position) {
        String tempString = daList.get(position);
        if (tempString.contains("healthy")) {
            return "Healthy";
        } else if (tempString.contains("neutral")) {
            return "Neutral";
        } else if (tempString.contains("unhealthy")) {
            return "Unhealthy";
        } else {
            return "Error";
        }
    }
}
