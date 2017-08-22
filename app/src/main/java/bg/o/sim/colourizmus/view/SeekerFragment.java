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
import android.widget.SeekBar;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.ColourRepository;
import bg.o.sim.colourizmus.utils.Util;

public class SeekerFragment extends LifecycleFragment {

    private SeekBar redSeeker, greenSeeker, blueSeeker;

    @Override
    public void onStart() {
        super.onStart();
        Log.w(Util.LOG_TAG_DEV, "SeekerFragment#onStart: =====================================================");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(Util.LOG_TAG_DEV, "SeekerFragment#onStop : =====================================================");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ColourRepository.LIVE_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
            redSeeker.setProgress(ColourRepository.LIVE_COLOR.getRed());
            greenSeeker.setProgress(ColourRepository.LIVE_COLOR.getGreen());
            blueSeeker.setProgress(ColourRepository.LIVE_COLOR.getBlue());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_seeker, container, false);

        redSeeker = (SeekBar) view.findViewById(R.id.seeker_red);
        greenSeeker = (SeekBar) view.findViewById(R.id.seeker_green);
        blueSeeker = (SeekBar) view.findViewById(R.id.seeker_blue);

        redSeeker.setMax(255);
        greenSeeker.setMax(255);
        blueSeeker.setMax(255);

        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) return;
                ColourRepository.LIVE_COLOR.setColour(Color.rgb(
                        redSeeker.getProgress(),
                        greenSeeker.getProgress(),
                        blueSeeker.getProgress())
                );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        redSeeker.setOnSeekBarChangeListener(chanelListener);
        greenSeeker.setOnSeekBarChangeListener(chanelListener);
        blueSeeker.setOnSeekBarChangeListener(chanelListener);

        return view;
    }
}
