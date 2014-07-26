package com.stratazima.instaviewer;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.stratazima.instaviewer.processes.DataStorage;


public class SetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        DataStorage dataStorage = DataStorage.getInstance(this);
        dataStorage.onGetJSON();

        int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_main);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        Button button = (Button) findViewById(R.id.button);
        final int finalMAppWidgetId = mAppWidgetId;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, finalMAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}
