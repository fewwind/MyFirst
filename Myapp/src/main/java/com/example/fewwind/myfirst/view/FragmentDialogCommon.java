package com.example.fewwind.myfirst.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fewwind on 2016/1/22.
 */
public class FragmentDialogCommon extends DialogFragment {


    int layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        int styleNum = getArguments().getInt("style", -1);
        View view = inflater.inflate(styleNum, container);
        return view;
    }

    public static FragmentDialogCommon newInstance(int style) {
        FragmentDialogCommon dialogFragment = new FragmentDialogCommon();

        Bundle bundle = new Bundle();
        bundle.putInt("style", style);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }
}
