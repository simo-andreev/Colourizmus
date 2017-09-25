package bg.o.sim.colourizmus.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.Util;

public class ColourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_details);

        CustomColour c;
        if (getIntent().hasExtra(Util.EXTRA_COLOUR)) {
            c = (CustomColour) getIntent().getSerializableExtra(Util.EXTRA_COLOUR);
        } else {
            c = new CustomColour("temp", CR.LIVE_COLOR.getValue());
        }

        float[] colInHSV = new float[3];

        View mainColourPreview = findViewById(R.id.colour_preview);
        View cmplColourPreview = findViewById(R.id.complimentary_preview);
//        TextView mainColourName = findViewById(R.id.colour_name);
//        TextView cmplColourName = findViewById(R.id.complimentary_name);

        Color.colorToHSV(c.getValue(), colInHSV);
        mainColourPreview.setBackgroundColor(c.getValue());
//        mainColourName.setText(c.getName() + " " + +colInHSV[0] + " : " + colInHSV[1] + " : " + colInHSV[2]);

        colInHSV = new float[3];

        Color.colorToHSV(c.getValue(), colInHSV);
        colInHSV[0] = (colInHSV[0] + 180) % 360;

        int cmplColourInt = Color.HSVToColor(colInHSV);
        CustomColour complimentary = new CustomColour("Complimentary", cmplColourInt);


        cmplColourPreview.setBackgroundColor(complimentary.getValue());
//        cmplColourName.setText("Complimentary colour [hue reversed]  " + colInHSV[0] + " : " + colInHSV[1] + " : " + colInHSV[2]);

        View[] palleteA = {
                findViewById(R.id.palleteA_0),
                findViewById(R.id.palleteA_1),
                findViewById(R.id.palleteA_2),
                findViewById(R.id.palleteA_3),
                findViewById(R.id.palleteA_4),
        };

        colInHSV = new float[3];
        Color.colorToHSV(c.getValue(), colInHSV);
        colInHSV[1] = 0;

        for (byte i = 0; i < 5; i++) {
            colInHSV[1] += 0.2;
            palleteA[i].setBackgroundColor(Color.HSVToColor(colInHSV));
        }

        View[] palleteB = {
                findViewById(R.id.palleteB_0),
                findViewById(R.id.palleteB_1),
                findViewById(R.id.palleteB_2),
                findViewById(R.id.palleteB_3),
                findViewById(R.id.palleteB_4),
        };

        colInHSV = new float[3];
        Color.colorToHSV(c.getValue(), colInHSV);
        colInHSV[2] = 0;

        for (byte i = 0; i < 5; i++) {
            colInHSV[2] += 0.2;
            palleteB[i].setBackgroundColor(Color.HSVToColor(colInHSV));
        }
    }
}
