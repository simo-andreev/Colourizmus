package com.colourizmus;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekerFragment extends Fragment {

    SeekBar red, green, blue;
    View colorBox;
    TextView hexadec;

    int r;
    int g;
    int b;

    public SeekerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeekerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeekerFragment newInstance(String param1, String param2) {
        SeekerFragment fragment = new SeekerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seeker, container, false);


        colorBox = view.findViewById(R.id.colorBox);
        hexadec = (TextView) view.findViewById(R.id.hexadecValue);

        red = (SeekBar) view.findViewById(R.id.redSlider);
        green = (SeekBar) view.findViewById(R.id.greenSlider);
        blue = (SeekBar) view.findViewById(R.id.blueSlider);

        red.setMax(255);
        green.setMax(255);
        blue.setMax(255);


        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                r = red.getProgress();
                g = green.getProgress();
                b = blue.getProgress();

                colorBox.setBackgroundColor(Color.rgb(r, g, b));

                StringBuilder sb = new StringBuilder("#");

                sb.append(Integer.toHexString(r));
                sb.append(Integer.toHexString(g));
                sb.append(Integer.toHexString(b));

                sb.append(" | ");

                sb.append(" r:"+r);
                sb.append(" g:"+g);
                sb.append(" b:"+b);


                hexadec.setText(sb);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        red.setOnSeekBarChangeListener(chanelListener);
        green.setOnSeekBarChangeListener(chanelListener);
        blue.setOnSeekBarChangeListener(chanelListener);

        return view;
    }

}
