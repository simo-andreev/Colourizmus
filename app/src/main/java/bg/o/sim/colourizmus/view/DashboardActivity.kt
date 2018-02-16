package bg.o.sim.colourizmus.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import bg.o.sim.colourizmus.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : Activity() {

    private val mClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.dashboard_button_colour_creation -> ColourCreationActivity.start(this)
            R.id.dashboard_button_gallery -> ColourListActivity.start(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        dashboard_button_colour_creation.setOnClickListener(mClickListener)
        dashboard_button_gallery.setOnClickListener(mClickListener)
    }
}