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
import bg.o.sim.colourizmus.utils.*
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