package bg.o.sim.colourizmus.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import bg.o.sim.colourizmus.model.CustomColour

const val APP_SPACE = "chain:"

@BindingAdapter("${APP_SPACE}colourList")
fun LinearLayout.bindColourList(vararg colours: CustomColour) {
    val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1f)
    val context = this.context

    for(col in colours){
        val colView = View(context)
        colView.setBackgroundColor(col.value)

        this.addView(colView, layoutParams)
    }
}