package com.stratazima.instaviewer.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.stratazima.instaviewer.DetailActivity;
import com.stratazima.instaviewer.R;


/**
 * Created by Esau on 7/23/2014.
 */

public class WidgetProvider extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);

            Intent newIntent = new Intent(context, DetailActivity.class);
            newIntent.putExtra("position", viewIndex);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);

            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.gridView, intent);

            rv.setEmptyView(R.id.gridView, R.id.empty_view);

            Intent toastIntent = new Intent(context, WidgetProvider.class);
            toastIntent.setAction(WidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.gridView, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ACTION_WIDGET_FORWARD)) {
//            Log.i("onReceive", ACTION_WIDGET_FORWARD);
//        } else if (intent.getAction().equals(ACTION_WIDGET_BACK)) {
//            Log.i("onReceive", ACTION_WIDGET_BACK);
//        } else if (intent.getAction().equals(ACTION_WIDGET_CELL)) {
//            Log.i("onReceive", ACTION_WIDGET_CELL);
//        } else {
//            super.onReceive(context, intent);
//        }
//        super.onReceive(context, intent);
//    }
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
//
//            Intent active = new Intent(context, WidgetProvider.class);
//            active.setAction(ACTION_WIDGET_FORWARD);
//            PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
//            //remoteViews.setOnClickPendingIntent(R.id.button_refresh, actionPendingIntent);
//
//            active = new Intent(context, WidgetProvider.class);
//            active.setAction(ACTION_WIDGET_BACK);
//            actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
//            //remoteViews.setOnClickPendingIntent(R.id.button_settings, actionPendingIntent);
//
//            active = new Intent(context, DetailActivity.class);
//            active.setAction(ACTION_WIDGET_CELL);
//            actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
//            remoteViews.setPendingIntentTemplate(R.id.widget_single, actionPendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
//        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
//    }
//
//    public static final String ACTION_WIDGET_FORWARD = "com.stratazima.instaviewer.FORWARD";
//    public static final String ACTION_WIDGET_BACK = "com.stratazima.instaviewer.BACK";
//    public static final String ACTION_WIDGET_CELL = "com.stratazima.instaviewer.CELL";
//    public static final String EXTRA_POSITION = "com.stratazima.instaviewer.POSITION";
}
