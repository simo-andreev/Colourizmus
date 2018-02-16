package bg.o.sim.colourizmus.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.UtilKt;

public class SaveColourDialogue extends DialogFragment {

    public static final String TAG = SaveColourDialogue.class.getName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialogue_save_colour, null);

        Dialog dialog = new AlertDialog.Builder(getActivity()).setView(contentView).create();

        View preview = contentView.findViewById(R.id.colour_preview);
        EditText nameInput = contentView.findViewById(R.id.colour_name);
        CheckBox isFavCheck = contentView.findViewById(R.id.colour_favourite);
        Button cancel = contentView.findViewById(R.id.colour_cancel);
        Button save = contentView.findViewById(R.id.colour_save);

        preview.setBackgroundColor(CR.LIVE_COLOUR.getValue());

        cancel.setOnClickListener((view) -> dialog.cancel());
        save.setOnClickListener(view -> {
            if (nameInput.getText().length() < 4) {
                nameInput.setError(getString(R.string.err_invalid_name));
                nameInput.requestFocus();
                return;
            }

            String name = nameInput.getText().toString();
            @ColorInt int value = CR.LIVE_COLOUR.getValue();
            boolean isFavourite = isFavCheck.isChecked();

            CustomColour col = new CustomColour(value, name);
            col.setFavourite(isFavourite);
            CR.saveColour(col);

            UtilKt.toastShort(getActivity(), getString(R.string.msg_saved, name));

            dialog.dismiss();
        });

        return dialog;
    }
}