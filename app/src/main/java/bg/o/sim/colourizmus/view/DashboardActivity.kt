package bg.o.sim.colourizmus.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.databinding.ActivityDashboardBinding

class DashboardActivity : Activity() {

    private lateinit var binding: ActivityDashboardBinding

    private val mClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.dashboard_button_colour_creation -> startActivity(Intent(this, ColourCreationActivity::class.java))
            R.id.dashboard_button_gallery -> startActivity(Intent(this, ColourListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.dashboardButtonColourCreation.setOnClickListener(mClickListener)
        binding.dashboardButtonGallery.setOnClickListener(mClickListener)

//        dashboard_button_gallery.background = CharDrawable('c', Color.rgb(180, 10, 10), Color.rgb(10, 10, 180))
//        dashboard_button_colour_creation.background = CharDrawable('T', Color.rgb(10, 180, 10), Color.rgb(180, 10, 180))
    }
}