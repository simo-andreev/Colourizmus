package bg.o.sim.colourizmus.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * Utility class for storing non-UI Strings, regexprs, numeric constants and providing non-class-specific objects and utility methods.
 */
public abstract class Util {

    public static final String LOG_TAG_DEV = "DEV_LOG";
    public static final String LOG_TAG_ERR = "ERR_LOG";
    public static final String DB_NAME = "bg.o.sim.colourizmus.db";
    public static final String EXTRA_COLOUR = "EXTRA_COLOUR";

    public static void toastLong(Context c, String message) {
        toast(c, message, Toast.LENGTH_LONG);
    }

    public static void toastShort(Context c, String message) {
        toast(c, message, Toast.LENGTH_LONG);
    }

    private static void toast(Context c, String s, int length) {
        Toast.makeText(c, s, length).show();
    }
}
