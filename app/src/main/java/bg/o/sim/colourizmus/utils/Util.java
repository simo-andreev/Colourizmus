package bg.o.sim.colourizmus.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Utility class for storing non-UI Strings, regexprs, numeric constants and providing non-class-specific objects and utility methods.
 */
public final class Util {


    private Util(){}

    public static final String DB_NAME = "bg.o.sim.colourizmus.db";
    public static final String LOG_TAG_DEV = "DEV_LOG";
    public static final String LOG_TAG_ERR = "ERR_LOG";
    public static final String EXTRA_COLOUR = "EXTRA_COLOUR";
    private static final String DIR_IMAGE_STORE = "ColourizmusPics";


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

    /**
     * Acquire the default Image directory on the device's external storage.
     * @return {@link File} corresponding to public app-specific image directory on external storage
     * @throws IOException if path corresponds to a non-dir file or couldn't create dir for some other reason
     */
    public static File getPublicImageDir() throws IOException {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_IMAGE_STORE);

        if (!dir.isDirectory()){
            if (!dir.mkdirs()){
                throw new IOException("Couldn't create directory " + dir.getAbsolutePath());
            }
        }

        return dir;
    }

    /**
     * Wraps {@link File#createNewFile()}.
     * @return true if file is successfully recreated.
     * @throws IOException if path corresponds to a non-file file or couldn't create file for some other reason
     */
    public static boolean makeFile(File file, boolean overrideIfExists) throws IOException {
        if (file.exists()){
            if (!overrideIfExists)
                return false;
            else
                file.delete();
        }

        if (!file.isFile()){
            if (!file.createNewFile()){
                throw new IOException("Couldn't create file " + file.getAbsolutePath());
            }
        }

        // TODO: 26.10.17 - revise in a more sober state of being. This method just feels unsecure.
        return true;
    }
}
