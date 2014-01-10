package com.vedantu.android.reader;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Properties;

import android.app.Application;
import android.util.Log;

import com.google.analytics.tracking.android.ExceptionReporter;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.Tracker;

public class VApp extends Application {

    private static final String    TAG                          = "VApp";
    private static GoogleAnalytics mGa;
    private static Tracker         mTracker;

    /*
     * Google Analytics configuration values.
     */
    // Placeholder property ID.
    private static String          GA_PROPERTY_ID               = "UA-45060486-6";

    // Dispatch period in seconds.
    private static final int       GA_DISPATCH_PERIOD           = 20;

    // Prevent hits from being sent to reports, i.e. during testing.
    private static boolean         GA_IS_DRY_RUN                = true;

    // GA Logger verbosity.
    private static final LogLevel  GA_LOG_VERBOSITY             = LogLevel.INFO;
    private static Properties      properties                   = new Properties();

    /*
     * Method to handle basic Google Analytics initialization. This call will not block as all
     * Google Analytics work occurs off the main thread.
     */
    private static final boolean   GA_CATCH_UNCAUGHT_EXCEPTIONS = true;

    private void initializeGa() {

        mGa = GoogleAnalytics.getInstance(this);

        GA_PROPERTY_ID = properties.getProperty("ga.property.id");
        GA_IS_DRY_RUN = Boolean.parseBoolean(properties.getProperty("ga.is.dry.run"));
        Log.d(TAG, "Property id for google analytics is  " + GA_PROPERTY_ID);
        mTracker = mGa.getTracker(GA_PROPERTY_ID);

        // Set dispatch period.
        GAServiceManager.getInstance().setLocalDispatchPeriod(GA_DISPATCH_PERIOD);

        // Set dryRun flag.
        mGa.setDryRun(GA_IS_DRY_RUN);

        // Set Logger verbosity.
        mGa.getLogger().setLogLevel(GA_LOG_VERBOSITY);

        if (GA_CATCH_UNCAUGHT_EXCEPTIONS) {
            UncaughtExceptionHandler myHandler = new ExceptionReporter(mTracker,
                    GAServiceManager.getInstance(), Thread.getDefaultUncaughtExceptionHandler(),
                    this);
            // Make myHandler the new default uncaught exception handler.
            Thread.setDefaultUncaughtExceptionHandler(myHandler);
        }
    }

    @Override
    public void onCreate() {

        super.onCreate();
        try {
            properties.load(getApplicationContext().getAssets().open("conf/app.conf"));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        initializeGa();
    }

    public static Tracker getGaTracker() {

        return mTracker;
    }

    public static GoogleAnalytics getGaInstance() {

        return mGa;
    }

}
