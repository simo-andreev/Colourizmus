package bg.o.sim.colourizmus.model

import android.arch.lifecycle.LiveData
import android.graphics.Color
import java.util.*

/**
 * An implementation of the [LiveData] class, made for storing a single colour.
 * Intended for the ColourCreation fragments of the [bg.o.sim.colourizmus.view.ColourCreationActivity].
 * It should allow a more efficient synchronisation of the current value, than the old Communicator system,
 * due to the Lifecycle awareness of the LiveData type.
 */
class LiveColour : LiveData<Int>() {
    init {
        val r = Random()
        value = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
    }

    fun getRed() = Color.red(value!!)
    fun getGreen() = Color.green(value!!)
    fun getBlue() = Color.blue(value!!)

    // necessary for Java inter-op. Can't use @JvmOverloads because all params are Ints with default val
    fun setRed(red: Int) { value = Color.rgb(red, getGreen(), getBlue()) }
    fun setGreen(green: Int) { value = Color.rgb(getRed(), green, getBlue()) }
    fun setBlue(blue: Int) { value = Color.rgb(getRed(), getGreen(), blue) }

    fun set(red: Int = getRed(), green: Int = getGreen(), blue: Int = getBlue()) {
        value = Color.rgb(red, green, blue)
    }
}