package com.stratazima.instaviewer.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.stratazima.instaviewer.DetailActivity;
import com.stratazima.instaviewer.R;
import com.stratazima.instaviewer.processes.DataStorage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esau on 7/23/2014.
 */
public class WidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new GridRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static int mCount = 10;
    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;
    private int mAppWidgetId;
    public int color;
    public boolean trans;
    public boolean allData;

    public GridRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
    }

    public void onDestroy() {
        mWidgetItems.clear();
    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_single);

        AQuery aq =  new AQuery(mContext);
        String url = mWidgetItems.get(position).text;

        int alpha;
        int red;
        int green;
        int blue;
        switch (color) {
            case 0:
                red = 225;
                blue = 0;
                green = 0;
            break;

            case 1:
                red = 0;
                blue = 0;
                green = 225;
                break;

            case 2:
                red = 0;
                blue = 225;
                green = 0;
                break;
            default:
                red = 0;
                blue = 0;
                green = 0;
            break;
        }

        if (trans) {
            alpha = 25;
        } else {
            alpha = 100;
        }

        rv.setInt(R.id.widget_da_single, "setBackgroundColor", android.graphics.Color.argb(alpha,red,green,blue));

        aq.ajax(url, Bitmap.class, new AjaxCallback<Bitmap>(){
            @Override
            public void callback(String url, Bitmap object, AjaxStatus status) {
                rv.setImageViewBitmap(R.id.widget_single, object);
            }
        });

        Bundle extras = new Bundle();
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent(mContext, DetailActivity.class);
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_single, fillInIntent);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        DataStorage dataStorage = DataStorage.getInstance(mContext);
        dataStorage.onGetJSON();

        SharedPreferences setupPrefs = mContext.getSharedPreferences("WidgetSetup", Context.MODE_PRIVATE);
        color = setupPrefs.getInt("color" , 1);
        trans = setupPrefs.getBoolean("transparency", false);
        if (setupPrefs.getBoolean("all", false)) {
            mCount = 20;
        }

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = dataStorage.onReadFile();
        for (int i = 0; i < mCount; i++) {
            try {
                mWidgetItems.add(new WidgetItem(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("thumbnail").getString("url")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
