package bg.o.sim.colourizmus.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.cardview.widget.CardView
import android.widget.LinearLayout
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.databinding.ActivityColourDetailsBinding
import bg.o.sim.colourizmus.model.CustomColour
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.utils.*

class ColourDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColourDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColourDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dominantCol: CustomColour  = when {
            intent.hasExtra(EXTRA_COLOUR) -> intent.getSerializableExtra(EXTRA_COLOUR) as CustomColour
            intent.hasExtra(EXTRA_PICTURE_URI) -> loadPassedPhoto(intent)
            else -> CustomColour(LIVE_COLOUR.value!!, "")
        }

        // show the 'dominant' colour at top
        bind(binding.colourPreview.root, dominantCol)
        // complimentary second
        bind(binding.complimentaryPreview.root, getComplimentaryColour(dominantCol))
        // and the rest ->
        bind(binding.paletteA.root, *getSaturationSwatch(dominantCol))
        bind(binding.paletteB.root, *getValueSwatch(dominantCol))
        bind(binding.paletteC.root, *getColourTriade(dominantCol))
        bind(binding.paletteD.root, *getHueSwatch(dominantCol))
    }
    
    private fun loadPassedPhoto(intent: Intent): CustomColour {
        val bitmap: Bitmap = intent.getParcelableExtra(EXTRA_PICTURE_THUMB)!!
        val palette = Palette.Builder(bitmap).generate()
        binding.photoPreview.setImageBitmap(bitmap)

        val default = ContextCompat.getColor(this, R.color.error_red)
        bind(binding.photoSwatch.root, CustomColour(palette.getMutedColor(default), ""),
                CustomColour(palette.getDominantColor(default), ""),
                CustomColour(palette.getVibrantColor(default), "")
        )

        return CustomColour(palette.getDominantColor(default), "prime")
    }

    private fun bind(view: CardView, vararg colours: CustomColour) {
        view.findViewById<LinearLayout>(R.id.palette_row).bindColourList(*colours)
    }

}