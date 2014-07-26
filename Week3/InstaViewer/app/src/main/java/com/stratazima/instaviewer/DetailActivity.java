package com.stratazima.instaviewer;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.stratazima.instaviewer.processes.DataStorage;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Esau on 7/23/2014.
 */
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        DataStorage dataStorage = DataStorage.getInstance(this);
        AQuery aQuery = new AQuery(this);
        JSONArray jsonArray = dataStorage.onReadFile();

        try {
            aQuery.id(R.id.insta_user).text(jsonArray.getJSONObject(position).getJSONObject("user").getString("username"));
            aQuery.id(R.id.insta_likes).text(jsonArray.getJSONObject(position).getJSONObject("likes").getString("count"));
            aQuery.id(R.id.insta_comments).text(jsonArray.getJSONObject(position).getJSONObject("comments").getString("count"));
            aQuery.id(R.id.insta_image).image(jsonArray.getJSONObject(position).getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
