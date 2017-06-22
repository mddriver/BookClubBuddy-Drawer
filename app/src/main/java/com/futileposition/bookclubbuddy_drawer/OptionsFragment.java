package com.futileposition.bookclubbuddy_drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MD on 6/21/2017.
 */

public class OptionsFragment extends Fragment {
    public static final String ARG_OPTION_NUMBER = "option_number";

    public OptionsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int i = getArguments().getInt(ARG_OPTION_NUMBER);
        View rootView = null;
        if(i == 0) {
            //TO FIX: SET THIS TO SHOW CURRENT BOOKS
            rootView = inflater.inflate(R.layout.temp_layout, container, false);
            getActivity().setTitle("Book Club Buddy");
        } else if (i == 1) {
            //TO FIX: Set this to show book add interface.
            rootView = inflater.inflate(R.layout.content_main, container, false);
            getActivity().setTitle("Add A New Book");
        }
        return rootView;
    }

}