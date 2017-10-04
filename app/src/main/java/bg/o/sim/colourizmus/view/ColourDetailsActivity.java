package bg.o.sim.colourizmus.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;

import java.util.Arrays;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.databinding.CardColourListPreviewBinding;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.Util;

public class ColourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_details);

        CustomColour colourInPlay;

        if (getIntent().hasExtra(Util.EXTRA_COLOUR))
            colourInPlay = (CustomColour) getIntent().getSerializableExtra(Util.EXTRA_COLOUR);
        else
            colourInPlay = new CustomColour("temp", CR.LIVE_COLOR.getValue());


        float[] colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        bind(findViewById(R.id.colour_preview), colourInPlay);

        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        colInHSV[0] = (colInHSV[0] + 180) % 360;
        int cmplColourInt = Color.HSVToColor(colInHSV);
        CustomColour complimentary = new CustomColour("Complimentary", cmplColourInt);
        bind(findViewById(R.id.complimentary_preview), complimentary);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        colInHSV[1] = 0;

        CustomColour[] coloursInPlay = new CustomColour[5];
        for (byte i = 0; i < 5; i++) {
            colInHSV[1] += 0.2;
            coloursInPlay[i] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));
        }
        bind(findViewById(R.id.paletteA), coloursInPlay);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);

        while (colInHSV[2] >= 0.18)
            colInHSV[2] -= 0.16;

        coloursInPlay = new CustomColour[5];
        for (byte i = 0; i < 5; i++) {
            colInHSV[2] += 0.16;
            coloursInPlay[i] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));
        }
        bind(findViewById(R.id.paletteB), coloursInPlay);


        colInHSV = new float[3];
        Color.colorToHSV(colourInPlay.getValue(), colInHSV);
        coloursInPlay = new CustomColour[3];
        for (byte i = 0; i < 3; i++) {
            coloursInPlay[i] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));
            colInHSV[0] = (colInHSV[0] + 120) % 360;
        }
        bind(findViewById(R.id.paletteC), coloursInPlay);


        coloursInPlay = new CustomColour[6];

        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[0] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));

        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[1] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        colInHSV[0] = (colInHSV[0] + 60) % 360;


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[2] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));
        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[3] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        colInHSV[0] = (colInHSV[0] + 60) % 360;


        colInHSV[0] = (colInHSV[0] - 10) % 360;
        coloursInPlay[4] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));
        colInHSV[0] = (colInHSV[0] + 20) % 360;
        coloursInPlay[5] = new CustomColour("does NOT matter", Color.HSVToColor(colInHSV));

        bind(findViewById(R.id.paletteD), coloursInPlay);

//        Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
//        Canvas canvas = new Canvas(bmp);
//        canvas.drawColor(colourInPlay.getValue());
//
//        coloursInPlay = new CustomColour[6];
//        Palette.Builder paletteBuilder = new Palette.Builder(bmp);
//        paletteBuilder.maximumColorCount(6);
//        Palette palette = paletteBuilder.generate();
//
//        coloursInPlay[0] = new CustomColour("does NOT matter", palette.getDominantColor(colourInPlay.getValue()));
//        coloursInPlay[1] = new CustomColour("does NOT matter", palette.getMutedColor(colourInPlay.getValue()));
//        coloursInPlay[2] = new CustomColour("does NOT matter", palette.getVibrantColor(colourInPlay.getValue()));
//        coloursInPlay[3] = new CustomColour("does NOT matter", palette.getDarkMutedColor(colourInPlay.getValue()));
//        coloursInPlay[4] = new CustomColour("does NOT matter", palette.getDominantColor(colourInPlay.getValue()));
//        coloursInPlay[5] = new CustomColour("does NOT matter", palette.getDominantColor(colourInPlay.getValue()));
//
//        bind(findViewById(R.id.paletteE), coloursInPlay);

    }

    private void bind(CardView view, CustomColour ... colours) {
        CardColourListPreviewBinding.bind(view).setColourList(Arrays.asList(colours));
    }
}
