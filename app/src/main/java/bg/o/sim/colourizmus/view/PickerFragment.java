package bg.o.sim.colourizmus.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;


public class PickerFragment extends Fragment {

    private NumberPicker pickerRed, pickerGreen, pickerBlue;

    public static Fragment newInstance() {
        return new PickerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CR.LIVE_COLOR.observe(this, integer -> {
            pickerRed.setValue(Color.red(integer));
            pickerGreen.setValue(Color.green(integer));
            pickerBlue.setValue(Color.blue(integer));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        pickerRed = view.findViewById(R.id.red_picker);
        pickerGreen = view.findViewById(R.id.green_picker);
        pickerBlue = view.findViewById(R.id.blue_picker);

        pickerRed.setMaxValue(255);
        pickerGreen.setMaxValue(255);
        pickerBlue.setMaxValue(255);

        pickerRed.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOR.setRed(newVal));
        pickerGreen.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOR.setRed(newVal));
        pickerBlue.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOR.setRed(newVal));

        return view;
    }

}
