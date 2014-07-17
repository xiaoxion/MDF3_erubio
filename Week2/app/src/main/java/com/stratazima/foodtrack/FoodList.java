package com.stratazima.foodtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;


public class FoodList extends Activity {
    public static final String PREFS_NAME = "BootPreferences";
    private File healthy;
    private File neutral;
    private File unhealthy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Restore preferences
        SharedPreferences bootSetting = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        healthy = new File(getFilesDir() + "/healthy");
        neutral = new File(getFilesDir() + "/neutral");
        unhealthy = new File(getFilesDir() + "/unhealthy");

        if (!bootSetting.getBoolean("firstBoot", true)) {
            onListCreate();
        } else {
            if (!bootSetting.contains("firstBoot")) {
                if (!healthy.isDirectory() && !healthy.exists()) healthy.mkdir();
                if (!neutral.isDirectory() && !neutral.exists()) neutral.mkdir();
                if (!unhealthy.isDirectory() && !unhealthy.exists()) unhealthy.mkdir();

                SharedPreferences.Editor editor = bootSetting.edit();
                editor.putBoolean("firstBoot" , false);
                editor.apply();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.food_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Creates the list with the headers.
    private void onListCreate() {

    }
}
