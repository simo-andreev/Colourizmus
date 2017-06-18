package com.colourizmus.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.colourizmus.model.LiveColour;

/**
 * Utility class for storing non-UI Strings, regexprs, numeric constants and providing non-class-specific utility methods.
 */
public abstract class Util {

    public static final LiveColour LIVE_COLOR = new LiveColour();

    public static final String LOG_TAG_DEV = "DEV_LOG";
    public static final String LOG_TAG_ERR = "ERR_LOG";


    public static void makeLongToast(Context c, String message){ toast(c, message, Toast.LENGTH_LONG); }
    public static void makeShortToast(Context c, String message){ toast(c, message, Toast.LENGTH_LONG); }
    private static void toast(Context c, String s, int length){
        if (c == null || s == null ) Log.e(LOG_TAG_ERR, "Util.toast: Context or message was null!!!");
        else Toast.makeText(c, s, length);
    }

}
