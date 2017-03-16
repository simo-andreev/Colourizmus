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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerFragment extends Fragment {

    NumberPicker red, green, blue;
    View colorBox;
    TextView hexadec;

    int r;
    int g;
    int b;

    private OnFragmentInteractionListener mListener;

    public PickerFragment() {
    }


    public static PickerFragment newInstance() {
        PickerFragment fragment = new PickerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
