package com.vedantu.android.reader.utils.ga;

import android.util.Log;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.vedantu.android.reader.VApp;

public class GoogleAnalyticsUtils {

    public static void sendPageViewDataToGA() {

        Log.d("Google analytics", "Sending page view");
        VApp.getGaTracker().send(MapBuilder.createAppView().build());
    }

    public static void setScreenName(String screenName) {

        VApp.getGaTracker().set(Fields.SCREEN_NAME, screenName);
    }
}
