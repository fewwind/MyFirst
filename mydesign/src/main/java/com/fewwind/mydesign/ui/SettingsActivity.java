package com.fewwind.mydesign.ui;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.utils.StatusBarCompat;

public class SettingsActivity extends AppCompatActivity {

    private SettingFragment setFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set);


        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));



        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_set);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("个性化设置");
        //设置文字必须在这之前才会生效
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            setFrag = new SettingFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.id_layout_set_container, setFrag);
            fragmentTransaction.commit();


        }

    }


    public static void startSettingActivity(Context context) {
        Intent intent
                = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_set);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

}
