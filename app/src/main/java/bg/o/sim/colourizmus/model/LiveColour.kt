package bg.o.sim.colourizmus.model

import androidx.lifecycle.LiveData
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

    val r = getRed()
    val g = getGreen()
    val b = getBlue()

    val rgb = arrayOf(r, g, b)

    fun set(red: Int = getRed(), green: Int = getGreen(), blue: Int = getBlue()) {
        value = Color.rgb(red, green, blue)
    }

    fun set(rgb: Array<Int>) {

        if (rgb.size != 3) throw IllegalArgumentException("The rgb array MUST contain EXACTLY 3 items " +
                "for red, green and blue respectively. Passed array was : ${rgb.fold("") { acc, i -> "$acc, $i" }}.")

        value = Color.rgb(rgb[0],rgb[1],rgb[2])
    }

    fun set(liveColour: LiveColour){
        this.value = liveColour.value
    }
}