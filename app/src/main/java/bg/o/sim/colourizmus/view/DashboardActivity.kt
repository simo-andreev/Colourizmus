package bg.o.sim.colourizmus.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import bg.o.sim.colourizmus.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : Activity() {

    private val mClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.dashboard_button_colour_creation -> startActivity(Intent(this, ColourCreationActivity::class.java))
            R.id.dashboard_button_gallery -> startActivity(Intent(this, ColourListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Kotlin Android Extensions creates extension funcs/vals for the Views, named by their ids
        dashboard_button_colour_creation.setOnClickListener(mClickListener)
        dashboard_button_gallery.setOnClickListener(mClickListener)
    }
}