package com.example.fewwind.myfirst.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.fewwind.myfirst.R;

/**
 * Created by fewwind on 2016/1/14.
 */
public class AppWidget extends AppWidgetProvider {

    public static final String BTN_RADIO_ACTION = "com.incarmedia.BTN_RADIO_ACTION";
    private static final String TAG ="tag" ;

    public AppWidget() {
        super();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(BTN_RADIO_ACTION)){
            Log.e(TAG, "onReceive: " );
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.e(TAG, "onUpdate: ");

        for (int appWidgetid:appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            Log.e(TAG, "appWidgetid: " );
            Intent intentClick = new Intent();
            intentClick.setAction(BTN_RADIO_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
            remoteViews.setOnClickPendingIntent(R.id.logo,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetid,remoteViews);

        }


    }
}
