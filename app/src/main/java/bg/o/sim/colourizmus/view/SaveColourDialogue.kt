package bg.o.sim.colourizmus.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.CustomColour
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.model.saveColour
import bg.o.sim.colourizmus.utils.toastShort


class SaveColourDialogue : DialogFragment() {

    companion object {
        const val TAG = "SaveColourDialogue"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = activity.layoutInflater.inflate(R.layout.dialogue_save_colour, null)
        val dialog = AlertDialog.Builder(activity).setView(contentView).create()

        val preview = contentView.findViewById<View>(R.id.colour_preview)
        val nameInput = contentView.findViewById<EditText>(R.id.colour_name)
        val isFavCheck = contentView.findViewById<CheckBox>(R.id.colour_favourite)
        val cancel = contentView.findViewById<Button>(R.id.colour_cancel)
        val save = contentView.findViewById<Button>(R.id.colour_save)

        preview.setBackgroundColor(LIVE_COLOUR.value!!)

        cancel.setOnClickListener { dialog.cancel() }
        save.setOnClickListener {
            if (nameInput.text.length < 4) {
                nameInput.error = getString(R.string.err_invalid_name)
                nameInput.requestFocus()
            } else {
                val col = CustomColour(LIVE_COLOUR.value!!, nameInput.text.toString())
                col.isFavourite = isFavCheck.isChecked

                saveColour(col)

                activity.toastShort(getString(R.string.msg_saved, col.name))

                dialog.dismiss()
            }
        }

        return dialog
    }

}