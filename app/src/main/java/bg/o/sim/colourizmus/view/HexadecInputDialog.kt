package bg.o.sim.colourizmus.view

import android.app.DialogFragment
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.model.LiveColour

abstract class HexadecInputDialog : DialogFragment() {


    private val colour = LiveColour()

    // TODO - 26.07.2018 - add a light-weight colour preview View consisting of a simple paint on canvas
    // TODO - 26.07.2018 - implement a custom keyboard-view for hexadec input

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val contentView = inflater.inflate(R.layout.dialogue_hexadec_input, container, false)

        colour.set(LIVE_COLOUR)

        val nameInput = contentView.findViewById<EditText>(R.id.input)
        val cancel = contentView.findViewById<Button>(R.id.colour_cancel)
        val save = contentView.findViewById<Button>(R.id.colour_save)
        val preview = contentView.findViewById<ImageView>(R.id.hexadec_input_preview)

        nameInput.setText(colourToHexString().toUpperCase())

        cancel.setOnClickListener { dialog.cancel() }

        nameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                parseColour(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        colour.observeForever {
            preview.setBackgroundColor(it!!)
        }

        save.setOnClickListener {
            if (parseColour(nameInput.text)) {
                onSave(this, colour)
            }
        }

        return contentView
    }

    private fun colourToHexString(): String {
        return Integer.toHexString(colour.getRed()) + Integer.toHexString(colour.getGreen()) + Integer.toHexString(colour.getBlue())
    }

    private fun parseColour(colourText: CharSequence): Boolean {
        return try {
            val inputColor = Color.parseColor('#' + colourText.trim(' ', '\n', '#').toString())
            colour.set(r(inputColor), g(inputColor), b(inputColor))
            true
        } catch (e: IllegalArgumentException) {
            colour.set(0, 0, 0)
            false
        }
    }

    private fun r(@ColorInt colour: Int) = Color.red(colour)
    private fun g(@ColorInt colour: Int) = Color.green(colour)
    private fun b(@ColorInt colour: Int) = Color.blue(colour)


    abstract fun onSave(dialogue: HexadecInputDialog, colour: LiveColour)
}
