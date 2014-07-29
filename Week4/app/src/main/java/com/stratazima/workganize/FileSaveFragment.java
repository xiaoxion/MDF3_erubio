package com.stratazima.workganize;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by esaurubio on 7/29/14.
 */
public class FileSaveFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FileSaveFragment newInstance(int sectionNumber) {
        FileSaveFragment fragment = new FileSaveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FileSaveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        return rootView;
    }
}
