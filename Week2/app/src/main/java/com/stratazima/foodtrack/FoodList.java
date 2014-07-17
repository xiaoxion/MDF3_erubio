package com.stratazima.foodtrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class FoodList extends Activity {
    public static final String PREFS_NAME = "BootPreferences";
    private File[] files;
    private int CAPTURE_IMAGE_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Restore preferences
        SharedPreferences bootSetting = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        File healthy = new File(getFilesDir() + "/healthy");
        File neutral = new File(getFilesDir() + "/neutral");
        File unhealthy = new File(getFilesDir() + "/unhealthy");

        files = new File[] {healthy, neutral, unhealthy};

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
        if (id == R.id.action_add) {
            onCheckType();
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
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Image not Captured", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Failed! Check Device Camera!", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Creates the list with the headers.
    private void onListCreate() {

    }

    // starts the intent for the photo
    private void onCameraStart(int healthRating) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Long tsLong = System.currentTimeMillis()/1000;

        File tempFile = new File(files[healthRating] + "/" + tsLong.toString() + ".png");
        Uri uriSavedImage = Uri.fromFile(tempFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    // Checks which type of food is selected
    private void onCheckType() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Health Rating")
                .setItems(R.array.healthRating, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onCameraStart(which);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
