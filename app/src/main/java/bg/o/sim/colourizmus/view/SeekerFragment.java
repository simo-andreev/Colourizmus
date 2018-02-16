package bg.o.sim.colourizmus.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;

public class SeekerFragment extends Fragment {

    private SeekBar mRedSeeker, mGreenSeeker, mBlueSeeker;

    public static SeekerFragment newInstance() {
        return new SeekerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CR.LIVE_COLOUR.observe(this, onChange -> {
            mRedSeeker.setProgress(CR.LIVE_COLOUR.getRed());
            mGreenSeeker.setProgress(CR.LIVE_COLOUR.getGreen());
            mBlueSeeker.setProgress(CR.LIVE_COLOUR.getBlue());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_seeker, container, false);

        mRedSeeker = view.findViewById(R.id.seeker_red);
        mGreenSeeker = view.findViewById(R.id.seeker_green);
        mBlueSeeker = view.findViewById(R.id.seeker_blue);

        mRedSeeker.setMax(255);
        mGreenSeeker.setMax(255);
        mBlueSeeker.setMax(255);

        mRedSeeker.setOnSeekBarChangeListener((ChannelListener) (seekBar, progress, fromUser) -> {
            if (fromUser) CR.LIVE_COLOUR.setRed(progress);
        });

        mGreenSeeker.setOnSeekBarChangeListener((ChannelListener) (seekBar, progress, fromUser) -> {
            if (fromUser) CR.LIVE_COLOUR.setGreen(progress);
        });

        mBlueSeeker.setOnSeekBarChangeListener((ChannelListener) (seekBar, progress, fromUser) -> {
            if (fromUser) CR.LIVE_COLOUR.setBlue(progress);
        });

        return view;
    }

    /** I know this seems pointless *BUT* it allows me to lambdize the listeners abouve! ( ͡° ͜ʖ ͡°) **/
    private interface ChannelListener extends SeekBar.OnSeekBarChangeListener {
        @Override
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
        @Override
        default void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        default void onStopTrackingTouch(SeekBar seekBar) {}
    }
}
