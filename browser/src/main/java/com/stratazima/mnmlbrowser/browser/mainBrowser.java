package com.stratazima.mnmlbrowser.browser;

import com.stratazima.mnmlbrowser.browser.util.SystemUiHider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/*
Author:  Esau Rubio
Project: MNML Browser
Package: MainBrowser
Date:    7/8/2014
 */

public class MainBrowser extends Activity implements GestureDetector.OnGestureListener {
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;
    private EditText URLEditText;
    private WebView webView;
    private View controlsView;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;

    // Sets the views and receives intents
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_browser);

        final View contentView = findViewById(R.id.fullscreen_content);
        final GestureDetectorCompat mDetector = new GestureDetectorCompat(this,this);
        final Button goButton = (Button) findViewById(R.id.dummy_button);
        controlsView = findViewById(R.id.fullscreen_content_controls);
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        URLEditText = (EditText) findViewById(R.id.urlEditText);
        webView = (WebView) contentView;

        mSystemUiHider.setup();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mDetector.onTouchEvent(motionEvent);
            }
        });

        Intent webIntent = getIntent();

        if (webIntent.getAction().equals("android.intent.action.MAIN")) {
            webView.loadUrl("http://www.google.com");
        } else if (webIntent.getAction().equals("android.intent.action.VIEW")){
            if (!webIntent.getData().toString().equals("")) {
                webView.loadUrl(webIntent.getData().toString());
            } else {
                webView.loadUrl("http://www.google.com");
            }
        }

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGo();
            }
        });

        if (!isNetworkOnline()) {
            onNoNetworkDialog("Please Connect to Internet");
        }
    }

    // Inflates the menu and sets it
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Handles which button is clicked in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                if (webView.canGoBack()){
                    webView.goBack();
                }
                return true;
            case R.id.action_forward:
                if (webView.canGoForward()){
                    webView.goForward();
                }
                return true;
            case R.id.action_reload:
                webView.reload();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    // Handles the fling action and captures gestures on the app
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        try {
            if (Math.abs(motionEvent.getX() - motionEvent2.getX()) > SWIPE_MAX_OFF_PATH) return false;
            if (getActionBar() != null) {
                if(motionEvent.getY() - motionEvent2.getY() > SWIPE_MIN_DISTANCE) {
                    // Scroll Down -- Disappear
                    getActionBar().hide();
                    controlsView.setVisibility(View.INVISIBLE);
                    mSystemUiHider.hide();
                }  else if (motionEvent2.getY() - motionEvent.getY() > SWIPE_MIN_DISTANCE && Math.abs(v2) > 7500) {
                    // Scroll Up -- Reappear
                    getActionBar().show();
                    controlsView.setVisibility(View.VISIBLE);
                    mSystemUiHider.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Go to the web page or sets error if it's invalid
    private void onGo(){
        if (!isNetworkOnline()) {
            onNoNetworkDialog("Need Internet to Load Page");
            return;
        }

        if (Patterns.WEB_URL.matcher(URLEditText.getText().toString()).matches()){
            String url = URLEditText.getText().toString();
            if (!url.substring(0,6).equals("http://")){
                webView.loadUrl("http://"+url);
            } else {
                webView.loadUrl(url);
            }
            webView.requestFocus();
        } else {
            URLEditText.setError("Invalid URL");
        }
    }

    // Checks if there is a valid network connection
    public boolean isNetworkOnline() {
        boolean status = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnected()) {
                status= true;
            }
        }

        return status;
    }

    // Creates a dialog for user feedback
    private void onNoNetworkDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertBuilder =  builder.create();
        alertBuilder.show();
    }
}