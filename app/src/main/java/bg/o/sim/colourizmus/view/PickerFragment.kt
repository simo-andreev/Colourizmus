package bg.o.sim.colourizmus.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.*
import kotlinx.android.synthetic.main.fragment_picker.*


class PickerFragment : Fragment() {

    companion object {
        fun newInstance() = PickerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LIVE_COLOUR.observe(this, Observer{
            red_picker.value = LIVE_COLOUR.getRed()
            green_picker.value = LIVE_COLOUR.getGreen()
            blue_picker.value = LIVE_COLOUR.getBlue()
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_picker, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        red_picker.maxValue = 255
        green_picker.maxValue = 255
        blue_picker.maxValue = 255

        red_picker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setRed(newVal) }
        green_picker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setGreen(newVal) }
        blue_picker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setBlue(newVal) }
    }
}
