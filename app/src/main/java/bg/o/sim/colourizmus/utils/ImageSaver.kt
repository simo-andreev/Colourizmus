package bg.o.sim.colourizmus.utils

import android.media.Image

import java.io.File
import java.io.FileOutputStream

/** Saves a JPEG [Image] into the specified [File]. */
class ImageSaver(private val mImage: Image, private val mFile: File) : Runnable {

    override fun run() {
        val buffer = mImage.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())

        buffer.get(bytes)

        val output = FileOutputStream(mFile)

        try {
            output.write(bytes)
        } finally {
            mImage.close()
            output.close()
        }
    }
}
