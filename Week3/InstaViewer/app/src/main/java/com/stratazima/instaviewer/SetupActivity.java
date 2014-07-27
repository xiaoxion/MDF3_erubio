package com.stratazima.instaviewer;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Switch;

public class SetupActivity extends Activity {
    public static final String PREFS_NAME = "WidgetSetup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(2);
        int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        final Intent intent = getIntent();
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

                Switch transSwitch = (Switch) findViewById(R.id.trans_switch);
                Switch loadSwitch = (Switch) findViewById(R.id.load_values);

                SharedPreferences setupPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = setupPrefs.edit();
                editor.putInt("color" ,seekBar.getProgress());
                editor.putBoolean("transparency", transSwitch.isChecked());
                editor.putBoolean("all", loadSwitch.isChecked());
                editor.apply();

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, finalMAppWidgetId);
                sendBroadcast(resultValue);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}
