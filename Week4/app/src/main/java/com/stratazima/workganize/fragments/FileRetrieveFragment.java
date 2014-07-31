package com.stratazima.workganize.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stratazima.workganize.R;
import com.stratazima.workganize.processes.CustomList;
import com.stratazima.workganize.processes.DataStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by esaurubio on 7/29/14.
 */
public class FileRetrieveFragment extends ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    DataStorage dataStorage;
    TextView textView;

    public static FileRetrieveFragment newInstance(int sectionNumber) {
        FileRetrieveFragment fragment = new FileRetrieveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FileRetrieveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataStorage = DataStorage.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        textView = (TextView) rootView.findViewById(R.id.enter_jobs);
        ImageButton button = (ImageButton) rootView.findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadData();
            }
        });

        if (dataStorage.onCheckFile()) {
            onListCreate();
            textView.setVisibility(TextView.INVISIBLE);
        } else {
            textView.setVisibility(TextView.VISIBLE);
        }

        return rootView;
    }

    public void onReloadData() {
        if (dataStorage.onCheckFile()) {
            onListCreate();
            textView.setVisibility(TextView.INVISIBLE);
        } else {
            textView.setVisibility(TextView.VISIBLE);
        }
    }

    public void onListCreate() {
        dataStorage = DataStorage.getInstance(getActivity().getApplicationContext());
        JSONArray daJSONArray = dataStorage.onReadFile();
        ArrayList<HashMap<String,String>> myList = new ArrayList<HashMap<String, String>>();

        if(daJSONArray != null) {
            String name = null;
            String location = null;
            String type = null;
            String date = null;

            for (int i = 0; i < daJSONArray.length(); i++) {
                JSONObject tempObj = null;
                try {
                    tempObj =  daJSONArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (tempObj != null) {
                    try {
                        name = tempObj.getString("name");
                        location = tempObj.getString("location");
                        type = tempObj.getString("type");
                        date = tempObj.getString("date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                HashMap<String,String> displayMap = new HashMap<String, String>();
                displayMap.put("name", name);
                displayMap.put("location", location);
                displayMap.put("type", type);
                displayMap.put("date", date);

                myList.add(displayMap);
            }
        }

        String[] strings = new String[myList.size()];
        CustomList adapter = new CustomList(getActivity(), strings, myList);

        setListAdapter(adapter);
    }
}
