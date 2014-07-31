package com.stratazima.workganize.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.stratazima.workganize.R;
import com.stratazima.workganize.processes.DataStorage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by esaurubio on 7/29/14.
 */
public class WebViewFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static WebViewFragment newInstance(int sectionNumber) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WebViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.main_web_view);
        WebSettings webSettings = webView.getSettings();
        webView.loadUrl("file:///android_asset/www/index.html");

        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new onSaveData(getActivity()), "Android");

        return rootView;
    }

    public class onSaveData {
        Context mContext;


        onSaveData(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void saveData(String jsonObject) {
            DataStorage dataStorage = DataStorage.getInstance(mContext);
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = new JSONObject(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dataStorage.onWriteFile(jsonObject1);
        }
    }

}
