package bg.o.sim.colourizmus.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
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
        val contentView = activity.layoutInflater.inflate(R.layout.block_fab_dialogue, null)
        val dialog = AlertDialog.Builder(activity).setView(contentView).create()

        val nameInput = contentView.findViewById<TextInputEditText>(R.id.fab_dialogue_input_layout)
        val cancel = contentView.findViewById<FloatingActionButton>(R.id.fab_dialogue_cancel)
        val save = contentView.findViewById<FloatingActionButton>(R.id.fab_dialogue_save)

        cancel.setOnClickListener { dialog.cancel() }
        save.setOnClickListener {
            if (nameInput.text!!.length < 4) {
                nameInput.error = getString(R.string.err_invalid_name)
                nameInput.requestFocus()
            } else {
                val col = CustomColour(LIVE_COLOUR.value!!, nameInput.text.toString())

                saveColour(col)

                activity.toastShort(getString(R.string.msg_saved, col.name))

                dialog.dismiss()
            }
        }

        return dialog
    }

}