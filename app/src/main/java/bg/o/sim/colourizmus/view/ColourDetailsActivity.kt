package bg.o.sim.colourizmus.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.CardView
import android.widget.LinearLayout
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.CustomColour
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.utils.EXTRA_COLOUR
import bg.o.sim.colourizmus.utils.EXTRA_PICTURE_THUMB
import bg.o.sim.colourizmus.utils.EXTRA_PICTURE_URI
import bg.o.sim.colourizmus.utils.bindColourList
import kotlinx.android.synthetic.main.activity_colour_details.*

class ColourDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colour_details)

        val dominantCol: CustomColour  = when {
            intent.hasExtra(EXTRA_COLOUR) -> intent.getSerializableExtra(EXTRA_COLOUR) as CustomColour
            intent.hasExtra(EXTRA_PICTURE_URI) -> loadPassedPhoto(intent)
            else -> CustomColour(LIVE_COLOUR.value!!, "")
        }

        // show the 'dominant' colour at top
        bind(colour_preview as CardView, dominantCol)
        // complimentary second
        bind(complimentary_preview as CardView, getComplimentaryColour(dominantCol))
        // and the rest ->
        bind(paletteA as CardView, *getSaturationSwatch(dominantCol))
        bind(paletteB as CardView, *getValueSwatch(dominantCol))
        bind(paletteC as CardView, *getColourTriade(dominantCol))
        bind(paletteD as CardView, *getHueSwatch(dominantCol))
    }

    private fun getComplimentaryColour(colourInPlay: CustomColour): CustomColour {
        val colInHSV = FloatArray(3)
        Color.colorToHSV(colourInPlay.value, colInHSV)
        colInHSV[0] = (colInHSV[0] + 180) % 360 // invert the Hue

        return CustomColour(Color.HSVToColor(colInHSV), "Complimentary")
    }

    private fun getSaturationSwatch(dominantCol: CustomColour): Array<CustomColour> {
        val colInHSV = FloatArray(3)
        Color.colorToHSV(dominantCol.value, colInHSV)
        colInHSV[1] = 0.2f // set the Saturation to 0 for start of swatch

        return Array(5) {
            colInHSV[1] += 0.2f // increment saturation from 0.2 to 1 (thus start != white, but ends is [dominantColour])
            CustomColour(Color.HSVToColor(colInHSV), "")
        }
    }

    private fun getValueSwatch(dominantCol: CustomColour): Array<CustomColour> {
        val colInHSV = FloatArray(3)
        Color.colorToHSV(dominantCol.value, colInHSV)

        return Array(5){
            colInHSV[2] +=0.2f
            CustomColour(Color.HSVToColor(colInHSV), "")
        }
    }

    private fun getColourTriade(dominantCol: CustomColour): Array<CustomColour> {
        val colInHSV = FloatArray(3)
        Color.colorToHSV(dominantCol.value, colInHSV)
        colInHSV[2] = 0.2f // Value element of HSV to 0.2, to allow for progression, but avoid starting at black

        return Array(3){
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
    private fun getHueSwatch(dominantCol: CustomColour): Array<CustomColour> {
        val colInHSV = FloatArray(3)
        Color.colorToHSV(dominantCol.value, colInHSV)
        val hueSwatch = Array(6, { CustomColour(-1, "") }) // default unless objects, so the GC has something to do... (-_-)

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
    
    private fun loadPassedPhoto(intent: Intent): CustomColour {
        val bitmap: Bitmap = intent.getParcelableExtra(EXTRA_PICTURE_THUMB)
        val palette = Palette.Builder(bitmap).generate()
        photo_preview.setImageBitmap(bitmap)

        val default = ContextCompat.getColor(this, R.color.error_red)
        bind(photo_swatch as CardView, CustomColour(palette.getMutedColor(default), ""),
                CustomColour(palette.getDominantColor(default), ""),
                CustomColour(palette.getVibrantColor(default), "")
        )

        return CustomColour(palette.getDominantColor(default), "prime")
    }

    private fun bind(view: CardView, vararg colours: CustomColour) {
        view.findViewById<LinearLayout>(R.id.palette_row).bindColourList(*colours)
    }

}