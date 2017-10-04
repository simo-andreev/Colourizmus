package bg.o.sim.colourizmus.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Collection;

import bg.o.sim.colourizmus.databinding.PreviewSimpleColourPreviewBinding;
import bg.o.sim.colourizmus.model.CustomColour;

public abstract class ViewBindAdapter {

    private static final String APP_SPACE = "chain:";

    @BindingAdapter(APP_SPACE + "colourList")
    public static void bindColourList(ViewGroup viewGroup, Collection<CustomColour> colours) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup.removeAllViews();

        if (colours == null)
            return;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        for (CustomColour col : colours) {
            PreviewSimpleColourPreviewBinding binding = PreviewSimpleColourPreviewBinding.inflate(inflater, viewGroup, true);
            binding.getRoot().setLayoutParams(params);
            binding.setColour(col);
        }
    }
}
