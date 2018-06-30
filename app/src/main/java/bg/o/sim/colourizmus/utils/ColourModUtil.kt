package bg.o.sim.colourizmus.utils

import android.graphics.Color
import bg.o.sim.colourizmus.model.CustomColour

fun getComplimentaryColour(colourInPlay: CustomColour): CustomColour {
    val colInHSV = FloatArray(3)
    Color.colorToHSV(colourInPlay.value, colInHSV)
    colInHSV[0] = (colInHSV[0] + 180) % 360 // invert the Hue

    return CustomColour(Color.HSVToColor(colInHSV), "Complimentary")
}

fun getSaturationSwatch(dominantCol: CustomColour): Array<CustomColour> {
    val colInHSV = FloatArray(3)
    Color.colorToHSV(dominantCol.value, colInHSV)
    colInHSV[1] = 0.2f // set the Saturation to 0 for start of swatch

    return Array(5) {
        colInHSV[1] += 0.2f // increment saturation from 0.2 to 1 (thus start != white, but ends is [dominantColour])
        CustomColour(Color.HSVToColor(colInHSV), "")
    }
}

fun getValueSwatch(dominantCol: CustomColour): Array<CustomColour> {
    val colInHSV = FloatArray(3)
    Color.colorToHSV(dominantCol.value, colInHSV)

    return Array(5) {
        colInHSV[2] += 0.2f
        CustomColour(Color.HSVToColor(colInHSV), "")
    }
}

fun getColourTriade(dominantCol: CustomColour): Array<CustomColour> {
    val colInHSV = FloatArray(3)
    Color.colorToHSV(dominantCol.value, colInHSV)
    colInHSV[2] = 0.2f // Value element of HSV to 0.2, to allow for progression, but avoid starting at black

    return Array(3) {
        colInHSV[0] = (colInHSV[0] + 120) % 360
        CustomColour(Color.HSVToColor(colInHSV), "")
    }
}

/**
 * I have no idea exactly what happens in this method, or exactly what I was drinking when I wrote it.
 * It *does* however do *something*.
 * And I'm *pretty sure* I wasn't sober when I wrote most of this class (its original Java implementation anyway)
 * ...
 * Welp.
 * It works (I think) so...
 */
fun getHueSwatch(dominantCol: CustomColour): Array<CustomColour> {
    val colInHSV = FloatArray(3)
    Color.colorToHSV(dominantCol.value, colInHSV)
    val hueSwatch = Array(6, { CustomColour(-1, "") }) // default useless objects, so the GC has something to do... (-_-)

    colInHSV[0] = (colInHSV[0] - 10) % 360
    hueSwatch[0] = CustomColour(Color.HSVToColor(colInHSV), "")
    colInHSV[0] = (colInHSV[0] + 20) % 360
    hueSwatch[1] = CustomColour(Color.HSVToColor(colInHSV), "")


    colInHSV[0] = (colInHSV[0] - 10) % 360
    colInHSV[0] = (colInHSV[0] + 60) % 360


    colInHSV[0] = (colInHSV[0] - 10) % 360
    hueSwatch[2] = CustomColour(Color.HSVToColor(colInHSV), "")
    colInHSV[0] = (colInHSV[0] + 20) % 360
    hueSwatch[3] = CustomColour(Color.HSVToColor(colInHSV), "")


    colInHSV[0] = (colInHSV[0] - 10) % 360
    colInHSV[0] = (colInHSV[0] + 60) % 360


    colInHSV[0] = (colInHSV[0] - 10) % 360
    hueSwatch[4] = CustomColour(Color.HSVToColor(colInHSV), "")
    colInHSV[0] = (colInHSV[0] + 20) % 360
    hueSwatch[5] = CustomColour(Color.HSVToColor(colInHSV), "")

    return hueSwatch
}
