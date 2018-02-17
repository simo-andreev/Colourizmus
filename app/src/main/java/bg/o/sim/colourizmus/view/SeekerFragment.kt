package bg.o.sim.colourizmus.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import kotlinx.android.synthetic.main.fragment_seeker.*

class SeekerFragment : Fragment() {

    companion object {
        fun newInstance() = SeekerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LIVE_COLOUR.observe(this, Observer {
            seeker_red.progress = LIVE_COLOUR.getRed()
            seeker_green.progress = LIVE_COLOUR.getGreen()
            seeker_blue.progress = LIVE_COLOUR.getBlue()
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_seeker, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        seeker_red.max = 255
        seeker_green.max = 255
        seeker_blue.max = 255

        seeker_red.setOnSeekBarChangeListener(ChannelListener{ progress, fromUser -> if (fromUser) LIVE_COLOUR.setRed(progress) })
        seeker_green.setOnSeekBarChangeListener(ChannelListener{ progress, fromUser -> if (fromUser) LIVE_COLOUR.setGreen(progress) })
        seeker_blue.setOnSeekBarChangeListener(ChannelListener{ progress, fromUser -> if (fromUser) LIVE_COLOUR.setBlue(progress) })
    }

    /** I know this seems pointless *BUT* it allows me to lambdize the listeners abouve! ( ͡° ͜ʖ ͡°)  */
    class ChannelListener(private val onProgressChanged: (Int, Boolean) -> Unit) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) = onProgressChanged(progress, fromUser)
        override fun onStartTrackingTouch(seekBar: SeekBar) { /*do nothing at-all*/ }
        override fun onStopTrackingTouch(seekBar: SeekBar) { /*do nothing at-all*/ }
    }
}
