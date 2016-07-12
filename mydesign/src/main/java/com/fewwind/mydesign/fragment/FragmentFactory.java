package com.fewwind.mydesign.fragment;


import android.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fewwind on 2015/10/15.
 */
public class FragmentFactory {

    private static Map<Integer, Fragment> mFragments = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fm = null;

        fm = mFragments.get(position);
        if (fm == null) {
            if (position == 1) {
                fm = new FragmentMain();
            } else if (position == 2) {
                fm = new FragmentSysChan();
            } else if (position == 3) {
                fm = new FragmentCustom();
            } else if (position == 4) {

            }
        }


        if (fm != null) {
            mFragments.put(position, fm);
        }
        return fm;
    }

}
