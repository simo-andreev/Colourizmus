package bg.o.sim.colourizmus.view.widgets

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat.OPAQUE
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.text.TextPaint
import android.util.Log
import android.databinding.adapters.TextViewBindingAdapter.setTextSize
import kotlin.math.min


class CharDrawable(
        val char: Char,
        @ColorInt private val backgroundColour: Int,
        @ColorInt private val textColour: Int,
        private val textPaint: TextPaint = TextPaint()
) : Drawable() {
    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
    }


    override fun draw(canvas: Canvas?) {
        Log.wtf("TAAAG", "On Draw ======================================================================")
        canvas!! // assert that canvas is non-null

        canvas.drawColor(backgroundColour) // set background
        textPaint.color = textColour


        textPaint.textSize = min(bounds.width(), bounds.height()).toFloat()

        canvas.drawText(char.toString(), 0.toFloat(), bounds.height().toFloat() - textPaint.descent(), textPaint)
    }

}