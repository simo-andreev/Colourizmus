package com.colourizmus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;


public class PickerFragment extends Fragment implements ColourComunicee {

    //TODO link all related fragments colour selection - both selectors themselvs and colour display to maintain same colour.

    NumberPicker red, green, blue;

    public static PickerFragment newInstance() {
        PickerFragment fragment = new PickerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        observe((ColourComunicator) getActivity());

        red = (NumberPicker) view.findViewById(R.id.red_picker);
        green = (NumberPicker) view.findViewById(R.id.green_picker);
        blue = (NumberPicker) view.findViewById(R.id.blue_picker);

        red.setMaxValue(255);
        green.setMaxValue(255);
        blue.setMaxValue(255);

        NumberPicker.OnValueChangeListener chanelListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int r = red.getValue();
                int g = green.getValue();
                int b = blue.getValue();

                ((MainActivity) getActivity()).setColour(r, g, b);
            }
        };

        red.setOnValueChangedListener(chanelListener);
        green.setOnValueChangedListener(chanelListener);
        blue.setOnValueChangedListener(chanelListener);

        return view;
    }


    @Override
    public void observe(ColourComunicator c) {
        c.addObserver(this);
    }

    @Override
    public void react(int r, int g, int b) {
        red.setValue(r);
        green.setValue(g);
        blue.setValue(b);
    }
}
