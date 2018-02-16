package bg.o.sim.colourizmus.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Collection;

import bg.o.sim.colourizmus.model.CustomColour;

public abstract class ViewBindAdapter {

    private static final String APP_SPACE = "chain:";

    @BindingAdapter(APP_SPACE + "colourList")
    public static void bindColourList(ViewGroup viewGroup, Collection<CustomColour> colours) {
        Context context = viewGroup.getContext();
        viewGroup.removeAllViews();

        if (colours == null)
            return;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        for (CustomColour col : colours) {
            View v = new View(context);
            v.setLayoutParams(params);
            v.setBackgroundColor(col.getValue());

            viewGroup.addView(v);
        }
    }
}
