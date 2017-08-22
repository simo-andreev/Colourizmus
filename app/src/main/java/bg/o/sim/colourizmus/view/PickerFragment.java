package bg.o.sim.colourizmus.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.ColourRepository;
import bg.o.sim.colourizmus.utils.Util;


public class PickerFragment extends LifecycleFragment {

    private NumberPicker pickerRed, pickerGreen, pickerBlue;

    @Override
    public void onStart() {
        super.onStart();
        Log.w(Util.LOG_TAG_DEV, "PickerFragment#onStart: =====================================================");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(Util.LOG_TAG_DEV, "PickerFragment#onStop : =====================================================");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ColourRepository.LIVE_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                pickerRed.setValue(Color.red(integer));
                pickerGreen.setValue(Color.green(integer));
                pickerBlue.setValue(Color.blue(integer));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        pickerRed = (NumberPicker) view.findViewById(R.id.red_picker);
        pickerGreen = (NumberPicker) view.findViewById(R.id.green_picker);
        pickerBlue = (NumberPicker) view.findViewById(R.id.blue_picker);

        pickerRed.setMaxValue(255);
        pickerGreen.setMaxValue(255);
        pickerBlue.setMaxValue(255);

        NumberPicker.OnValueChangeListener chanelListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ColourRepository.LIVE_COLOR.setColour(Color.rgb(pickerRed.getValue(), pickerGreen.getValue(), pickerBlue.getValue()));
            }
        };

        pickerRed.setOnValueChangedListener(chanelListener);
        pickerGreen.setOnValueChangedListener(chanelListener);
        pickerBlue.setOnValueChangedListener(chanelListener);

        return view;
    }
}
