package bg.o.sim.colourizmus.utils

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.LinearLayout
import bg.o.sim.colourizmus.model.CustomColour

const val APP_SPACE = "chain:"

@BindingAdapter("${APP_SPACE}colourList")
fun LinearLayout.bindColourList(vararg colours: CustomColour) {
    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
    val context = this.context

    for (col in colours.filter { it.value != -1 }) {
        val colView = View(context)
        colView.setBackgroundColor(col.value)

        this.addView(colView, layoutParams)
    }
}