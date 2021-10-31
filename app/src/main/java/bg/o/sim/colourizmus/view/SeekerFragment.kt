package bg.o.sim.colourizmus.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import bg.o.sim.colourizmus.databinding.FragmentSeekerBinding
import bg.o.sim.colourizmus.model.LIVE_COLOUR

class SeekerFragment : Fragment() {

    companion object {
        fun newInstance() = SeekerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LIVE_COLOUR.observe(this, Observer {
            binding.seekerRed.progress = LIVE_COLOUR.getRed()
            binding.seekerGreen.progress = LIVE_COLOUR.getGreen()
            binding.seekerBlue.progress = LIVE_COLOUR.getBlue()
        })
    }


    private lateinit var binding: FragmentSeekerBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSeekerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.seekerRed.max = 255
        binding.seekerGreen.max = 255
        binding.seekerBlue.max = 255

        binding.seekerRed.setOnSeekBarChangeListener(ChannelListener { progress, fromUser -> if (fromUser) LIVE_COLOUR.setRed(progress) })
        binding.seekerGreen.setOnSeekBarChangeListener(ChannelListener { progress, fromUser -> if (fromUser) LIVE_COLOUR.setGreen(progress) })
        binding.seekerBlue.setOnSeekBarChangeListener(ChannelListener { progress, fromUser -> if (fromUser) LIVE_COLOUR.setBlue(progress) })
    }

    /** I know this seems pointless *BUT* it allows me to lambdize the listeners above! ( ͡° ͜ʖ ͡°)  */
    class ChannelListener(private val onProgressChanged: (Int, Boolean) -> Unit) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) = onProgressChanged(progress, fromUser)
        override fun onStartTrackingTouch(seekBar: SeekBar) { /*do nothing at-all*/
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) { /*do nothing at-all*/
        }
    }
}
