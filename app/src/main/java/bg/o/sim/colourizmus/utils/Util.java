package bg.o.sim.colourizmus.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Utility class for storing non-UI Strings, regexprs, numeric constants and providing non-class-specific objects and utility methods.
 */
public abstract class Util {

    public static final String LOG_TAG_DEV = "DEV_LOG";
    public static final String LOG_TAG_ERR = "ERR_LOG";
    public static final String DB_NAME = "bg.o.sim.colourizmus.db";
    public static final String EXTRA_COLOUR = "EXTRA_COLOUR";
    public static final String EXTRA_PICTURE_URI = "EXTRA_PICTURE_URI";
    public static final String EXTRA_PICTURE_THUMB = "EXTRA_PICTURE_THUMB";

    public static final int REQUEST_IMAGE_CAPTURE = 0b0000_0000;
    public static final int REQUEST_IMAGE_PERMISSION = 0b0000_1000;

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

    public static File getImageFile(Activity activity, boolean deleteOnExit) throws IOException {
        isStoragePermissionGranted(activity);

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
