package bg.o.sim.colourizmus.view;

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

        CR.LIVE_COLOUR.observe(this, integer -> {
            pickerRed.setValue(CR.LIVE_COLOUR.getRed());
            pickerGreen.setValue(CR.LIVE_COLOUR.getGreen());
            pickerBlue.setValue(CR.LIVE_COLOUR.getBlue());
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

        pickerRed.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOUR.setRed(newVal));
        pickerGreen.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOUR.setGreen(newVal));
        pickerBlue.setOnValueChangedListener((picker, oldVal, newVal) -> CR.LIVE_COLOUR.setBlue(newVal));

        return view;
    }

}
