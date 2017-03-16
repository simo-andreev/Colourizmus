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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeekerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeekerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeekerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    SeekBar red, green, blue;
    View colorBox;
    TextView hexadec;

    int r;
    int g;
    int b;


    private OnFragmentInteractionListener mListener;

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

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        // Inflate the layout for this fragment
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
