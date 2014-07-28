package com.stratazima.instaviewer.processes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
    public void onWriteFile(JSONArray jsonObject) {
        try {
            FileOutputStream fos = mContext.openFileOutput("data.JSON", Context.MODE_PRIVATE);
            fos.write(jsonObject.toString().getBytes());
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

    /**
     * Gets the JSON Data from instagram and writes it.
     */
    public void onGetJSON() {
        new getInstWithOptions().execute("https://api.instagram.com/v1/media/popular?client_id=7b6f5f09559a46459bb1e3a6c339e8a2");
    }

    /**
     * Gets data from URL and sends it to a jsonArray to be written.
     */
    private JSONArray getJSONFromUrl(String url) {
        InputStream is = null;
        JSONObject jsonObject;
        JSONArray jsonArray = null;
        String jObj = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            jObj = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
            jsonObject = new JSONObject(jObj);
            jsonArray = jsonObject.getJSONArray("data");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jsonArray;
    }

    /**
     * Private AsyncTask to run the thread.
     */
    private class getInstWithOptions extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            return getJSONFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            onWriteFile(jsonArray);
        }
    }
}
