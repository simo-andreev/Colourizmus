package bg.o.sim.colourizmus.utils

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import bg.o.sim.colourizmus.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility file for storing non-UI Strings, regexprs, numeric constants and
 * providing non-class-specific objects and utility methods.
 */

// String consts
const val LOG_TAG_DEV: String = "DEV_LOG"
const val LOG_TAG_ERR: String = "ERR_LOG"
const val DB_NAME: String = "bg.o.sim.colourizmus.db"
const val EXTRA_COLOUR: String = "EXTRA_COLOUR"
const val EXTRA_PICTURE_URI: String = "EXTRA_PICTURE_URI"
const val EXTRA_PICTURE_THUMB: String = "EXTRA_PICTURE_THUMB"

// Numeric consts
const val REQUEST_IMAGE_CAPTURE: Int = 0b0000_0000
const val REQUEST_PERMISSION_IMAGE_CAPTURE: Int = 0b0000_0010
const val REQUEST_IMAGE_PERMISSION: Int = 0b0000_1000

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/** When you just *have* to call a [Toast] from a background [Thread]*/
fun Activity.toastOnUiThreadExplicit(message: String) {
    this.runOnUiThread({ toastLong(message) })
}

@Throws(IOException::class)
fun Activity.getImageFile(): File {
    requestStoragePermission(this)    // rand locale for consistency
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(Date())
    val imageFileName = "COLOURIZMUS_$timestamp"
    val storageDir: File = this.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)

    return File.createTempFile(imageFileName, ".jpg", storageDir)
}

fun Activity.havePermission(permission: String) = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, permission)

fun requestStoragePermission(activity: Activity) {
    if (Build.VERSION.SDK_INT < 23)
        return //permission is automatically granted on sdk<23 upon installation

    if (ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(activity, arrayOf(WRITE_EXTERNAL_STORAGE), 1)
}

