package bg.o.sim.colourizmus.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


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

    /**
     * Shows a {@link Toast} on the UI thread.
     */
    public static void toastOnUiThread(Activity activity, final String text) {
        if (activity != null) {
            activity.runOnUiThread(() -> Toast.makeText(activity, text, Toast.LENGTH_SHORT).show());
        }
    }

    // TODO: 19.10.17 document
    public static boolean havePermissions(Context context, String... permissions) {
        for (String permission : permissions)
            if (!(ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED))
                return false;

        return true;
    }
}
