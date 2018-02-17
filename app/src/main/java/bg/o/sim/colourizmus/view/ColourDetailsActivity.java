package bg.o.sim.colourizmus.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.UtilKt;
import bg.o.sim.colourizmus.utils.ViewBindAdapterKt;

public class ColourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_details);

        CustomColour colourInPlay;

        if (getIntent().hasExtra(UtilKt.EXTRA_COLOUR))
            colourInPlay = (CustomColour) getIntent().getSerializableExtra(UtilKt.EXTRA_COLOUR);
        else if (getIntent().hasExtra(UtilKt.EXTRA_PICTURE_URI))
            colourInPlay = loadPassedPhoto(getIntent());
        else
            colourInPlay = new CustomColour(CR.LIVE_COLOUR.getValue(), "temp");

        float[] colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        bind(findViewById(R.id.colour_preview), colourInPlay);

        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        colInHSV[0] = (colInHSV[0] + 180) % 360;
        int cmplColourInt = Color.HSVToColor(colInHSV);
        CustomColour complimentary = new CustomColour(cmplColourInt, "Complimentary");
        bind(findViewById(R.id.complimentary_preview), complimentary);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        colInHSV[1] = 0;

        CustomColour[] coloursInPlay = new CustomColour[5];
        for (byte i = 0; i < 5; i++) {
            colInHSV[1] += 0.2;
            coloursInPlay[i] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");
        }
        bind(findViewById(R.id.paletteA), coloursInPlay);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);

        while (colInHSV[2] >= 0.18)
            colInHSV[2] -= 0.16;

        coloursInPlay = new CustomColour[5];
        for (byte i = 0; i < 5; i++) {
            colInHSV[2] += 0.16;
            coloursInPlay[i] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");
        }
        bind(findViewById(R.id.paletteB), coloursInPlay);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        coloursInPlay = new CustomColour[3];
        for (byte i = 0; i < 3; i++) {
            coloursInPlay[i] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");
            colInHSV[0] = (colInHSV[0] + 120) % 360;
        }
        bind(findViewById(R.id.paletteC), coloursInPlay);


        coloursInPlay = new CustomColour[6];

        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[0] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");

        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[1] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        colInHSV[0] = (colInHSV[0] + 60) % 360;


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[2] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");
        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[3] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        colInHSV[0] = (colInHSV[0] + 60) % 360;


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[4] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");
        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[5] = new CustomColour(Color.HSVToColor(colInHSV), "does NOT matter");

        bind(findViewById(R.id.paletteD), coloursInPlay);
    }

    private CustomColour loadPassedPhoto(Intent intent) {
        Uri uri = intent.getParcelableExtra(UtilKt.EXTRA_PICTURE_URI);
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            Toast.makeText(this, "aaaaah, fml....", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

        Palette palette =  new Palette.Builder(bitmap).generate();

        @ColorInt int defCol = ContextCompat.getColor(this, R.color.error_red);

        CustomColour colourToBeInPlay = new CustomColour(palette.getDominantColor(defCol), "temp");

        CustomColour[] photoColours = new CustomColour[3];
        photoColours[0] = new CustomColour(palette.getMutedColor(defCol), "nope1");
        photoColours[1] = new CustomColour(palette.getDominantColor(defCol), "nope1");
        photoColours[2] = new CustomColour(palette.getVibrantColor(defCol), "nope1");

        ImageView photo = findViewById(R.id.photo_preview);

        if (intent.hasExtra(UtilKt.EXTRA_PICTURE_THUMB))
            bitmap = intent.getParcelableExtra(UtilKt.EXTRA_PICTURE_THUMB);
        else
            Toast.makeText(this, "no fucking thumb m7!", Toast.LENGTH_SHORT).show();

        photo.setImageBitmap(bitmap);

        bind(findViewById(R.id.photo_swatch), photoColours);

        return colourToBeInPlay;
    }

    private void bind(CardView view, CustomColour... colours) {
        ViewBindAdapterKt.bindColourList(view.findViewById(R.id.palette_row), colours);
    }
}
