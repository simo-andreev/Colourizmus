package bg.o.sim.colourizmus.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import bg.o.sim.colourizmus.R

typealias DialogCallback = (dialogue: EditTextDialogue, input: String) -> Unit

class EditTextDialogue: DialogFragment() {

    var onCancel: DialogCallback? = null
    var onSave: DialogCallback? = null

    companion object {
        const val TAG = "EditTextDialogue"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = activity.layoutInflater.inflate(R.layout.dialogue_edit_text, null)

        val inputView = contentView.findViewById<EditText>(R.id.input)

        contentView.findViewById<Button>(R.id.colour_cancel).setOnClickListener {
            if (onCancel != null)
                onCancel!!.invoke(this, inputView.text.toString())
            else
                this.dismiss()
        }

        contentView.findViewById<Button>(R.id.colour_save).setOnClickListener {
            if (onSave != null)
                onSave!!.invoke(this, inputView.text.toString())
            else
                this.dismiss()
        }

        return AlertDialog.Builder(activity).setView(contentView).create()
    }
}