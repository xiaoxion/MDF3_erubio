package com.stratazima.workganize.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stratazima.workganize.R;

/**
 * Created by esaurubio on 7/29/14.
 */
public class FileRetrieveFragment extends ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        return rootView;
    }
}
