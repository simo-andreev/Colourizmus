package com.colourizmus;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class PickerFragment extends Fragment {

    //TODO link all related fragments colour selection - both selectors themselvs and colour display to maintain same colour.

    NumberPicker red, green, blue;
    View colorBox;
    TextView hexadec;

    int r;
    int g;
    int b;

    public static PickerFragment newInstance() {
        PickerFragment fragment = new PickerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        colorBox = view.findViewById(R.id.color_box);

        red = (NumberPicker) view.findViewById(R.id.red_picker);
        green = (NumberPicker) view.findViewById(R.id.green_picker);
        blue = (NumberPicker) view.findViewById(R.id.blue_picker);

        red.setMaxValue(25);
        green.setMaxValue(25);
        blue.setMaxValue(25);

        r = red.getValue();
        g = green.getValue();
        b = blue.getValue();

        colorBox.setBackgroundColor(Color.rgb(r*10,g*10,b*10));

        NumberPicker.OnValueChangeListener chanelListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                r = red.getValue();
                g = green.getValue();
                b = blue.getValue();

                colorBox.setBackgroundColor(Color.rgb(r*10,g*10,b*10));
            }
        };

        red.setOnValueChangedListener(chanelListener);
        green.setOnValueChangedListener(chanelListener);
        blue.setOnValueChangedListener(chanelListener);

        return view;
    }
}
