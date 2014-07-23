package com.stratazima.instaviewer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Esau on 7/23/2014.
 */

public class DataStorage implements Cloneable{
    private static DataStorage mInstance;
    private static Context mContext;
    JSONArray daObject = null;

    private DataStorage() {}

    public static DataStorage getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new DataStorage();
        }
        mContext = context.getApplicationContext();
        return mInstance;
    }

    /**
     * Check file so that the application can check if there
     * is an existing file.
     */
    public boolean onCheckFile() {
        Boolean fileExist = false;

        File file = new File(mContext.getFilesDir().getPath() + "/data.JSON");
        if (file.exists()) fileExist = true;

        return fileExist;
    }

    /**
     * Writes JSON data to application files directory.
     */
    public void onWriteFile(String jsonObject) {
        try {
            FileOutputStream fos = mContext.openFileOutput("data.JSON", Context.MODE_PRIVATE);
            fos.write(jsonObject.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads file and creates a JSONArray which is sent to the main activity
     */
    public JSONArray onReadFile() {
        String content;
        try {
            InputStream inputStream = mContext.openFileInput("data.JSON");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder contentBuffer = new StringBuilder();

            while ((content = bufferedReader.readLine()) != null) {
                contentBuffer.append(content);
            }

            content = contentBuffer.toString();
            daObject = new JSONArray(content);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return daObject;
    }
}
