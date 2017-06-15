package com.colourizmus.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.colourizmus.R;

public class SeekerFragment extends Fragment implements ColourComunicee {

    private SeekBar red, green, blue;


    public SeekerFragment() {
    }


    public static SeekerFragment newInstance() {
        SeekerFragment fragment = new SeekerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seeker, container, false);

        observe((ColourComunicator) getActivity());

        red = (SeekBar) view.findViewById(R.id.seeker_red);
        green = (SeekBar) view.findViewById(R.id.seeker_green);
        blue = (SeekBar) view.findViewById(R.id.seeker_blue);

        red.setMax(255);
        green.setMax(255);
        blue.setMax(255);


        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int r = red.getProgress();
                int g = green.getProgress();
                int b = blue.getProgress();
                ((MainActivity) getActivity()).setColour(r, g, b);
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

    @Override
    public void observe(ColourComunicator c) {
        c.addObserver(this);
    }

    @Override
    public void react(int r, int g, int b) {
        red.setProgress(r);
        green.setProgress(g);
        blue.setProgress(b);
    }
}
