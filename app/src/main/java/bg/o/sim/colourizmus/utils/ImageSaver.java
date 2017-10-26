package bg.o.sim.colourizmus.utils;

import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Saves a JPEG {@link Image} into the specified {@link File}.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ImageSaver implements Runnable {

    private final Image mImage;
    private final File mFile;

    public ImageSaver(Image image, File file) {
        this.mImage = image;
        this.mFile = file;
    }

    // TODO: 26.10.17 I'm not 100% sure what on Alla's good, flat, earth is going on 'ere!
    @Override
    public void run() {
        if (Util.makeFile(mFile, false)){
            return;
            // TODO: 26.10.17 - notify somehow.
        }

        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        FileOutputStream output = null;

        try {
            output = new FileOutputStream(mFile);
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mImage.close();
            try {
                output.close();
            } catch (NullPointerException e){
                // ignore, this replaces a null-check
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
