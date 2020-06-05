package com.fmahieu.timetracker.logger;

import android.util.Log;

public class Logger {

    private boolean showDebug = false;

    /**
     * Logs for Test module
     * @param tag
     * @param message
     */
    public void logTest(String tag, String message){
        tag  = tag + "__TESTING";
        Log.i(tag, message);
    }

    /**
     * For debugging purposes
     */
    public void logDebug(String tag, String message){
        if(showDebug) {
            tag = tag + "__DEBUG";
            Log.i(tag, message);
        }
    }

    public void logError(String tag, String message){
        tag += "__ERROR";
        Log.e(tag, message);
    }

    public void logException(String tag, String message, Exception e){
        tag += "__EXCEPTION";
        Log.e(tag, message, e);
    }

    public void logMessage(String tag, String message){
        Log.i(tag, message);
    }
}
