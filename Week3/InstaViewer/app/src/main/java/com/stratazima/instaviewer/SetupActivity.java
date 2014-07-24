package com.stratazima.instaviewer;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.stratazima.instaviewer.processes.DataStorage;
import com.stratazima.instaviewer.widget.WidgetProvider;


public class SetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        DataStorage dataStorage = DataStorage.getInstance(this);
        dataStorage.onGetJSON();
    }
}
