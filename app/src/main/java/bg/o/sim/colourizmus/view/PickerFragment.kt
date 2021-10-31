package bg.o.sim.colourizmus.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bg.o.sim.colourizmus.databinding.FragmentPickerBinding
import bg.o.sim.colourizmus.model.LIVE_COLOUR


class PickerFragment : Fragment() {

    private lateinit var binding: FragmentPickerBinding

    companion object {
        fun newInstance() = PickerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LIVE_COLOUR.observe(viewLifecycleOwner) {
            binding.redPicker.value = LIVE_COLOUR.getRed()
            binding.greenPicker.value = LIVE_COLOUR.getGreen()
            binding.bluePicker.value = LIVE_COLOUR.getBlue()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPickerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.redPicker.maxValue = 255
        binding.greenPicker.maxValue = 255
        binding.bluePicker.maxValue = 255

        binding.redPicker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setRed(newVal) }
        binding.greenPicker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setGreen(newVal) }
        binding.bluePicker.setOnValueChangedListener { _, _, newVal -> LIVE_COLOUR.setBlue(newVal) }
    }
}
